package com.myapp.sidi;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

public class MemoDrawView extends View {

    public boolean changed = false;

    //그림을 그리려면 픽셀을 고정하는 비트맵, 그릴 수 있는 캔버스(비트맵에 쓰기), 도면의 색과 스타일을 나타내는 페인트,

    //Canvas 무엇을 그릴 것인지? 선을 그릴 수 있는 메서드 제공
    Canvas mCanvas;
    //Bitmap 작은 사각형 모양의 픽셀이 모여 이미지를 구성하는 방식
    Bitmap mBitmap;
    //Paint 어떻게 그릴 것인지? 도형의 색상, 스타일 ,글꼴등을 지정
    Paint mPaint;

    float lastX;
    float lastY;

    //Path 클래스는 그림을 그리는 경로를 캡슐화(연관 있는 변수와 함수를 클래스로 묶는 작업)한다.
    Path mPath = new Path();

    float mCurveEndX;
    float mCurveEndY;

    int mInvalidateExtraBorder = 10;

    int currentColor = Color.BLACK;

    static final float TOUCH_TOLERANCE = 8;

    public MemoDrawView(Context context) {
        super(context);
        init(context);
    }

    public MemoDrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //Paint.Cap은 선과 경로의 시작과 끝에 대한 처리를 명시
        mPaint.setStrokeCap(Paint.Cap.BUTT);
        init(context);
    }

    private void init(Context context) {
        mPaint = new Paint();
        //setAntiAlias : 선과 모서리를 매끄럽게 나타내주는 효과
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        //join set
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        //cap set
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        //기본 굵기.
        mPaint.setStrokeWidth(10);


        this.lastX = -1;
        this.lastY = -1;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {

        //Bitmap.Config 환경 설정
        Bitmap img = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);


        //TODO 추후에 여기서 한번 배경화면 넣어보기
        Canvas canvas = new Canvas();
        canvas.setBitmap(img);
        canvas.drawColor(Color.WHITE);

        mBitmap = img;
        mCanvas = canvas;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mBitmap != null) {
            canvas.drawBitmap(mBitmap, 0, 0, null);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //MotionEvent 이동이벤트 감지하는 객체
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_UP:
                changed = true;
                Rect rect = touchUp(event, false);
                if (rect != null) {
                    invalidate(rect);
                }
                mPath.rewind();

                return true;
            case MotionEvent.ACTION_DOWN:
                rect = touchDown(event);
                if (rect != null) {
                    invalidate(rect);
                }

                return true;
            case MotionEvent.ACTION_MOVE:
                rect = touchMove(event);
                if (rect != null) {
                    invalidate(rect);
                }
                return true;

        }
        return false;
    }

    private Rect touchMove(MotionEvent event) {
        Rect rect=processMove(event);
        return rect;
    }

    private Rect processMove(MotionEvent event) {
        final float x=event.getX();
        final float y=event.getY();

        final float dx=Math.abs(x-lastX);
        final float dy=Math.abs(y-lastY);

        Rect mInvalidateRect=new Rect();

        if(dx>=TOUCH_TOLERANCE || dy>=TOUCH_TOLERANCE){
            final int border=mInvalidateExtraBorder;

            mInvalidateRect.set((int)mCurveEndX-border,(int)mCurveEndY-border,(int)mCurveEndX+border,(int)mCurveEndY+border);

            float cx=mCurveEndX=(x+lastX)/2;
            float cy=mCurveEndY=(y+lastY)/2;

            mPath.quadTo(lastX,lastY,cx,cy);

            mInvalidateRect.union((int)lastX-border,(int)lastY-border,(int)lastX+border,(int)lastY+border);
            mInvalidateRect.union((int)cx-border,(int)cy-border,(int)cx,(int)cy+border);

            lastX=x;
            lastY=y;

            mCanvas.drawPath(mPath,mPaint);

        }

        return mInvalidateRect;
    }

    private Rect touchDown(MotionEvent event) {
        float x=event.getX();
        float y=event.getY();

        lastX=x;
        lastY=y;

        Rect mInvalidateRect=new Rect();
        mPath.moveTo(x,y);

        final int border=mInvalidateExtraBorder;
        mInvalidateRect.set((int)x-border,(int)y-border,(int)x+border,(int)y+border);
        mCurveEndX=x;
        mCurveEndY=y;

        mCanvas.drawPath(mPath,mPaint);
        return mInvalidateRect;
    }
    public void setStrokeWidth(int width){
        mPaint.setStrokeWidth(width);
    }

    private Rect touchUp(MotionEvent event, boolean b) {
        Rect rect=processMove(event);
        return rect;
    }

    public void setColor(int color){
        mPaint.setColor(color);
        if (color!=-1){
            currentColor = color;
        }
        //흰색으로 변경하는게 아니라면, 현재 색 저장.
    }

    public void loadColor(){
        setColor(currentColor);
    }
//    public void setCap(int cap){
//        switch(cap){
//            case 0:
//                mPaint.setStrokeCap(Paint.Cap.BUTT);
//                break;
//            case 1:
//                mPaint.setStrokeCap(Paint.Cap.ROUND);
//                break;
//            case 2:
//                mPaint.setStrokeCap(Paint.Cap.SQUARE);
//                break;
//        }
//    }
}