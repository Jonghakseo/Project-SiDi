package com.myapp.sidi.sketch;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.myapp.sidi.DTO.SimilarImageRcyData;
import com.myapp.sidi.DTO.SimilarImageResult;
import com.myapp.sidi.Interface.ServerInterface;
import com.myapp.sidi.MemoDrawView;
import com.myapp.sidi.R;
import com.myapp.sidi.SketchSearch;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import yuku.ambilwarna.AmbilWarnaDialog;

public class IdeaSketch extends AppCompatActivity {
    MemoDrawView view;
    int tColor, n = 0;
    File newSketch;

    String imageUrl, registrationNum, country;
    ImageView iv_backgroundDesign;
    FrameLayout idea_captureArea;

    String fName;//파일명
    String pName;//경로명

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idea_sketch);
        //일단 addMemo레이아웃을 불러온다
        view = new MemoDrawView(this, true);
        //생성자에 true 붙이면 아이디어 스케치 전용으로 생성
        //그림을 그릴 수 있는 MemoDrawView를 View로 가져온다.

        idea_captureArea = findViewById(R.id.idea_captureArea);
        iv_backgroundDesign = findViewById(R.id.iv_idea_backgroundDesign);

        imageUrl = getIntent().getStringExtra("nowImage");//현재 배경으로 둘 도면
        registrationNum = getIntent().getStringExtra("registrationNum");//등록번호 혹은 출원번호
        country = getIntent().getStringExtra("country");//국가

        Glide.with(this).load(imageUrl).into(iv_backgroundDesign);

        LinearLayout container;
        //컨테이너로 사용할 수 있는 선형 레이아웃 선언
        container = findViewById(R.id.idea_drawingLayout);
        //addMemo에 있던 아랫부분으로 선언한다.
        Resources res = getResources();


        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        //레이아웃 안의 설정값을 변경한다. 부모에게 자식 뷰를 배치할 방법을 알려주는 데에 사용된다.
        // *파라미터를 사용하는 이유 : addView메소드로 전달 가능한 인수의 갯수가 제한되므로,
        //                              파라미터 객체를 생성해서 정보를 담은 후 전달해주는 것이다.


        container.addView(view, params);

        RadioButton thick1 = findViewById(R.id.idea_radioButton);
        RadioButton thick2 = findViewById(R.id.idea_radioButton2);
        RadioButton thick3 = findViewById(R.id.idea_radioButton3);
        RadioButton eraserButton = findViewById(R.id.idea_eraserButton);

        //각 굵기에 대응하는 라디오 버튼들

        RadioGroup radioGroup = findViewById(R.id.idea_radioGroup);
        // 라디오 버튼을 모아서 그룹으로 만든다

        thick1.setChecked(true);
        //굵기 1을 기본으로 설정

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.idea_radioButton:
                        view.setStrokeWidth(10);
                        view.loadColor();
                        break;
                    case R.id.idea_radioButton2:
                        view.setStrokeWidth(20);
                        view.loadColor();
                        break;
                    case R.id.idea_radioButton3:
                        view.setStrokeWidth(50);
                        view.loadColor();
                        break;
                    case R.id.idea_eraserButton:
                        view.setStrokeWidth(250);
                        view.setColor(-1);
                        //두껍게. 흰 색으로 설정해서 지우개 구현.
                }
            }
        });
        //그룹에 대한 변화를 체크하는 리스너. 각 체크 항목마다 다른 굵기를 설정해준다.

        Button colorPickerButton = findViewById(R.id.idea_colorPickerButton);
        Button saveMemoButton = findViewById(R.id.idea_saveMemoButton);
        ImageButton backButton = findViewById(R.id.idea_memoBackButton);
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
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);
        try {
//            View v1 = getWindow().getDecorView().getRootView();//전체화면
            View v1 = idea_captureArea;//캡쳐영역 지정
//
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);

            File dir = new File(getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), "ideaSketch");
//         폴더가 있는지 확인 후 없으면 새로 만들어준다.

            if (!dir.exists()) {
                dir.mkdirs();
            }
            fName = registrationNum+"_is_" + now + ".jpg";//파일명
            pName = getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/ideaSketch";//storage 경로명
            newSketch = new File(pName, fName);
            System.out.println(getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES));

            FileOutputStream outputStream = new FileOutputStream(newSketch);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream);
            outputStream.flush();
            outputStream.close();
            Toast.makeText(this, "저장 성공", Toast.LENGTH_SHORT).show();

            /**
             * 저장과 동시에 서버에 파일 업로드
             *
             * **/
            UploadAsync uploadAsync = new UploadAsync();
            uploadAsync.execute(newSketch);

        } catch (Throwable e) {
            // Several error may come out with file handling or OOM
            e.printStackTrace();
        }

    }

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



    class UploadAsync extends AsyncTask<File, Void, Void> {
        File targetFile;
        int serverResponseCode;
        boolean sf = false;
        @Override
        protected Void doInBackground(File... files) {

            targetFile = files[0];
            HttpURLConnection conn = null;
            DataOutputStream dos = null;
            String lineEnd = "\r\n";
            String twoHyphens = "--";
            String boundary = "*****";
            int bytesRead, bytesAvailable, bufferSize;
            byte[] buffer;
            int maxBufferSize = 1 * 1024 * 1024;
            File sourceFile = targetFile;
            if (!sourceFile.isFile()) {
                Log.e("uploadFile", "Source File not exist :"
                        +pName + "" + fName);
            } else {
                try {
                    // open a URL connection to the Servlet
                    FileInputStream fileInputStream = new FileInputStream(sourceFile);
                    URL url = new URL("http://ec2-13-125-249-181.ap-northeast-2.compute.amazonaws.com/sidi/uploadIdeaSketch.php");
                    // Open a HTTP  connection to  the URL

                    conn = (HttpURLConnection) url.openConnection();

                    conn.setDoInput(true); // Allow Inputs
                    conn.setDoOutput(true); // Allow Outputs
                    conn.setUseCaches(false); // Don't use a Cached Copy
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Connection", "Keep-Alive");
                    conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                    conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                    conn.setRequestProperty("uploaded_file", fName);

                    dos = new DataOutputStream(conn.getOutputStream());
                    dos.writeBytes(twoHyphens + boundary + lineEnd);
                    dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                            + fName + "\"" + lineEnd);


                    dos.writeBytes(lineEnd);
                    // crete a buffer of  maximum size

                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    buffer = new byte[bufferSize];

                    // read file and write it into form...

                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                    while (bytesRead > 0) {
                        dos.write(buffer, 0, bufferSize);
                        bytesAvailable = fileInputStream.available();
                        bufferSize = Math.min(bytesAvailable, maxBufferSize);
                        bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                    }

                    // send multipart form data necesssary after file data...
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                    // Responses from the server (code and message)
                    serverResponseCode = conn.getResponseCode();
                    String serverResponseMessage = conn.getResponseMessage();

                    Log.i("uploadFile", "HTTP Response is : "

                            + serverResponseMessage + ": " + serverResponseCode);

                    if (serverResponseCode == 200) {
                        sf = true;
                        runOnUiThread(new Runnable() {

                            public void run() {
                                Toast.makeText(IdeaSketch.this, "File Upload Complete.",
                                        Toast.LENGTH_SHORT).show();

                            }

                        });

                    }


                    //close the streams //

                    fileInputStream.close();

                    dos.flush();

                    dos.close();


                } catch (MalformedURLException ex) {



                    ex.printStackTrace();


                    runOnUiThread(new Runnable() {

                        public void run() {
                            Toast.makeText(IdeaSketch.this, "MalformedURLException",

                                    Toast.LENGTH_SHORT).show();

                        }

                    });


                    Log.e("Upload file to server", "error: " + ex.getMessage(), ex);

                } catch (Exception e) {



                    e.printStackTrace();


                    runOnUiThread(new Runnable() {

                        public void run() {
                            Toast.makeText(IdeaSketch.this, "Got Exception : see logcat ",
                                    Toast.LENGTH_SHORT).show();

                        }

                    });

                    Log.e("Upload file to server Exception", "Exception : "

                            + e.getMessage(), e);

                }


            } // End else block

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (sf){

            }

            super.onPostExecute(aVoid);
        }


    }

    private HttpLoggingInterceptor httpLoggingInterceptor(){

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                android.util.Log.e("MyGitHubData :", message + "");
            }
        });

        return interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    }
}