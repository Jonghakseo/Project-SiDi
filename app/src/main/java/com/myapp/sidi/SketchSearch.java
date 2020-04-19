package com.myapp.sidi;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import yuku.ambilwarna.AmbilWarnaDialog;

public class SketchSearch extends AppCompatActivity {
    MemoDrawView view;
    int tColor, n = 0;
    File newMemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sketch_search);
        //일단 addMemo레이아웃을 불러온다
        view = new MemoDrawView(this);
        //그림을 그릴 수 있는 MemoDrawView를 View로 가져온다.

//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
//            //쓰기 권한 없음
//            System.out.println("쓰기 권한 없음");
//
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//                // 권한이 필요한 이유를 설명해야하는가?
//                ActivityCompat.requestPermissions(this,
//                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                        SAVE_MEMO_PERMISSION);
//            } else {
//                // 그런거 필요 없으면 바로 권한요청
//                ActivityCompat.requestPermissions(this,
//                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                        SAVE_MEMO_PERMISSION);
//
//            }
//            if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
//                //읽기 권한 없음\
//                System.out.println("읽기 권한 없음");
//                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
//                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
//                } else {
//                    ActivityCompat.requestPermissions(this,
//                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
//                            SAVE_MEMO_PERMISSION);
//
//                }
//            }
//        }


        LinearLayout container;
        //컨테이너로 사용할 수 있는 선형 레이아웃 선언
        container = findViewById(R.id.drawingLayout);
        //addMemo에 있던 아랫부분으로 선언한다.
        Resources res = getResources();


        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        //레이아웃 안의 설정값을 변경한다. 부모에게 자식 뷰를 배치할 방법을 알려주는 데에 사용된다.
        // *파라미터를 사용하는 이유 : addView메소드로 전달 가능한 인수의 갯수가 제한되므로,
        //                              파라미터 객체를 생성해서 정보를 담은 후 전달해주는 것이다.


        container.addView(view, params);

        RadioButton thick1 = findViewById(R.id.radioButton);
        RadioButton thick2 = findViewById(R.id.radioButton2);
        RadioButton thick3 = findViewById(R.id.radioButton3);
        RadioButton eraserButton = findViewById(R.id.eraserButton);

        //각 굵기에 대응하는 라디오 버튼들

        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        // 라디오 버튼을 모아서 그룹으로 만든다

        thick1.setChecked(true);
        //굵기 1을 기본으로 설정

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButton:
                        view.setStrokeWidth(10);
                        view.loadColor();
                        break;
                    case R.id.radioButton2:
                        view.setStrokeWidth(20);
                        view.loadColor();
                        break;
                    case R.id.radioButton3:
                        view.setStrokeWidth(50);
                        view.loadColor();
                        break;
                    case R.id.eraserButton:
                        view.setStrokeWidth(250);
                        view.setColor(-1);
                        //두껍게. 흰 색으로 설정해서 지우개 구현.
                }
            }
        });
        //그룹에 대한 변화를 체크하는 리스너. 각 체크 항목마다 다른 굵기를 설정해준다.

        Button colorPickerButton = findViewById(R.id.colorPickerButton);
        Button saveMemoButton = findViewById(R.id.saveMemoButton);
        ImageButton backButton = findViewById(R.id.memoBackButton);
        // 색상, 저장, 뒤로가기 버튼도 찾아준다.

        colorPickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openColorPicker();
            }
        });
        //색상 선택 버튼

        saveMemoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
        //저장 버튼

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
//                loadPicture();
            }
        });
        //뒤로가기 버튼
    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
//        switch (requestCode) {
//            case SAVE_MEMO_PERMISSION: {
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    Toast.makeText(this,"승인이 허가되어 있습니다.",Toast.LENGTH_LONG).show();
//                } else {
//                    Toast.makeText(this,"아직 승인받지 않았습니다.",Toast.LENGTH_LONG).show();
//                }
//                return;
//            }
//        }
//    }


    private void save() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("저장 확인");
        builder.setMessage("저장하시겠습니까?");
        builder.setPositiveButton("저장",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //저장버튼 누르면 작동하는 부분 구현
                        savePicture();
                    }
                });
        builder.setNegativeButton("취소",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //취소시 작동부분. 없음.
                    }
                });
        builder.show();

    }

    private void savePicture() {
        // 1. 캐쉬(Cache)를 허용시킨다.
        // 2. 그림을 Bitmap 으로 저장.
        // 3. 캐쉬를 막는다.

        view.setDrawingCacheEnabled(true);    // 캐쉬허용
        // 캐쉬에서 가져온 비트맵을 복사해서 새로운 비트맵(스크린샷) 생성
        Bitmap screenshot = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);   // 캐쉬닫기
//

        // SDCard(ExternalStorage) : 외부저장공간

        // 접근하려면 반드시 AndroidManifest.xml에 권한 설정을 한다.

        File dir = new File(getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), "memo");
//         폴더가 있는지 확인 후 없으면 새로 만들어준다.

        if (!dir.exists()) {
            dir.mkdirs();
        }

        // 현재시간을 msec 으로 구한다.
        long now = System.currentTimeMillis();
        // 현재시간을 date 변수에 저장한다.
        Date date = new Date(now);
        // 시간을 나타냇 포맷을 정한다 ( yyyy/MM/dd 같은 형태로 변형 가능 )
        SimpleDateFormat sdfNow = new SimpleDateFormat("yyyyMMdd_HHmmss");
        // nowDate 변수에 값을 저장한다.
        String formatDate = sdfNow.format(date);

        FileOutputStream fos;
        try {
            newMemo = new File(getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/memo", "memo" + formatDate + ".png");
//            System.out.println(getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES));
            //앱의 내부 저장소에 접근해서 저장함으로써 권한 문제 회피  https://underground2.tistory.com/39
            fos = new FileOutputStream(newMemo);
            screenshot.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
            Toast.makeText(this, "저장 성공", Toast.LENGTH_SHORT).show();
//            memoLists.add(newMemo);
            finish();

        } catch (Exception e) {
            Log.e("phoro", "그림저장오류", e);
            Toast.makeText(this, "저장 실패", Toast.LENGTH_SHORT).show();

        }

    }

//    private void loadPicture() {
//
//        if (newMemo != null) {
//            System.out.println("그렸던 그림 불러오기");
//        }
////        Drawable drawable = new VectorDrawable()
////        Drawable drawable = ContextCompat.getDrawable(AddMemo.this, );
////        Bitmap icon = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
//
//        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
////        Bitmap bm = BitmapFactory.decodeFile(dir+"/memo.png");
//        Bitmap bm = null;
//        try {
//            bm = BitmapFactory.decodeStream(getContentResolver().openInputStream(Uri.fromFile(newMemo))).copy(Bitmap.Config.ARGB_8888, true);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        //벡터 - 비트맵 변환되면서 에러 발생.
//
//        view.draw(new Canvas(bm));
//
//
//    }


    private void openColorPicker() {
        AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(this, tColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }
            //취소시

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
//                Toast.makeText(getApplicationContext(), "" + color, Toast.LENGTH_LONG).show();
                //컬러 선택시 팝업되는 토스트 메시지. 선택된 색의 int값 알 수 있음
                view.setColor(color);
            }
            //확인시
        });
        colorPicker.show();
    }
//오픈소스로 가져온 컬러 선택기를 팝업한다.
}