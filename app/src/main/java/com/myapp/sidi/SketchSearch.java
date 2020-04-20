package com.myapp.sidi;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.icu.text.SimpleDateFormat;
import android.os.AsyncTask;
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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.myapp.sidi.Adapter.SearchSketch_Adapter;
import com.myapp.sidi.DTO.SearchResultData;
import com.myapp.sidi.DTO.SearchingTabDesignResult;
import com.myapp.sidi.DTO.SimilarImageDetailData;
import com.myapp.sidi.DTO.SimilarImageRcyData;
import com.myapp.sidi.DTO.SimilarImageResult;
import com.myapp.sidi.Interface.ServerInterface;
import com.myapp.sidi.search.ViewDetail;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
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

public class SketchSearch extends AppCompatActivity {
    MemoDrawView view;
    int tColor, n = 0;
    String userID;
    File newMemo;
    Button btn_sketchSearch;
    RecyclerView rv_similarDesign;
    GridLayoutManager gridLayoutManager;
    SearchSketch_Adapter searchSketchAdapter;
    ArrayList<SimilarImageRcyData> similarImages  = new ArrayList<>();
    ArrayList<SimilarImageDetailData> similarImageDetailArr = new ArrayList<>();
    ArrayList appNumberArr = new ArrayList();

    String uploadFilePath = "";//경로를 모르겠으면, 갤러리 어플리케이션 가서 메뉴->상세 정보
    String uploadFileName = ""; //전송하고자하는 파일 이름


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sketch_search);
        //일단 addMemo레이아웃을 불러온다
        view = new MemoDrawView(this);
        //그림을 그릴 수 있는 MemoDrawView를 View로 가져온다.

        userID = getIntent().getStringExtra("user");
        if (userID == null){
            userID = "tempUser";
        }



        btn_sketchSearch = findViewById(R.id.btn_sketchSearch);
        rv_similarDesign = findViewById(R.id.rv_similarDesign);

        gridLayoutManager = new GridLayoutManager(this,2,GridLayoutManager.HORIZONTAL,false);
        rv_similarDesign.setLayoutManager(gridLayoutManager);
        searchSketchAdapter = new SearchSketch_Adapter(similarImages,SketchSearch.this);
        searchSketchAdapter.setOnItemClickListener(new SearchSketch_Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, final int position) {
                //받아온 모든 데이터들 넘겨주기
                //일단 데이터를 아이템에 저장시킬 수 있도록 하기
                String appNumber = appNumberArr.get(position).toString();
                String cate = "desk";


                OkHttpClient client = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor()).build();
                //1. 서버에 카테고리 1번을 보내기
                //2. 기본 연도 1960으로 해서 보내기
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(ServerInterface.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(client)
                        .build();
                ServerInterface serverInterface = retrofit.create(ServerInterface.class);
                serverInterface.similarDetail(cate,appNumber).enqueue(new Callback<SimilarImageDetailData>() {
                    @Override
                    public void onResponse(Call<SimilarImageDetailData> call, Response<SimilarImageDetailData> response) {
                        SimilarImageDetailData result = response.body();
                        Log.e("result",result.toString());
                        List<SimilarImageDetailData.Result> list = result.getResult();

                        Log.e("test", String.valueOf(response.body()));

                        for (SimilarImageDetailData.Result value : list) {
                            Intent intent = new Intent(SketchSearch.this, ViewDetail.class);
                            intent.putExtra("country",value.getCountry());
                            intent.putExtra("registrationNum",value.getDesignNum());
                            intent.putExtra("depth1",value.getDep1());
                            intent.putExtra("depth2",value.getDep2());
                            intent.putExtra("depth3",value.getDep3());
                            intent.putExtra("depth4",value.getDep4());
                            intent.putExtra("depth5",value.getDep5());
                            startActivity(intent);



                            SimilarImageDetailData searchResultData = new SimilarImageDetailData(value.getUrl(),
                                    value.getServerIndex(),
                                    value.getDesignNum(),
                                    value.getRegistrationNum(),
                                    value.getDesignCode(),
                                    value.getCountry(),
                                    value.getDesignName(),
                                    value.getRegisterPerson(),
                                    value.getDateApplication(),
                                    value.getDateRegistration(),
                                    value.getDatePublication(),
                                    value.getDep1(),
                                    value.getDep2(),
                                    value.getDep3(),
                                    value.getDep4(),
                                    value.getDep5()
                            );
//                            similarImageDetailArr.add(searchResultData);
                        }

//                        Log.e("arr[0]",similarImageDetailArr.get(0).toString());



                    }

                    @Override
                    public void onFailure(Call<SimilarImageDetailData> call, Throwable t) {

                    }
                });



                Log.e("num",appNumberArr.get(position).toString());



            }
        });
        rv_similarDesign.setAdapter(searchSketchAdapter);
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

        btn_sketchSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });

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

    private void search() {
        savePicture();
//        if (savePicture() != null){
        UploadAsync uploadAsync = new UploadAsync();
        uploadAsync.execute(newMemo);
//        }
    }

    private String savePicture() {
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

        File dir = new File(getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), "searchSketch");
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
            newMemo = new File(getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/searchSketch", "sketch" + userID + formatDate + ".jpg");
//            System.out.println(getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES));
            //앱의 내부 저장소에 접근해서 저장함으로써 권한 문제 회피  https://underground2.tistory.com/39
            fos = new FileOutputStream(newMemo);
            screenshot.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();
//            Toast.makeText(this, "저장 성공", Toast.LENGTH_SHORT).show();
            uploadFilePath = getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/searchSketch";
            uploadFileName = "sketch" + userID + formatDate + ".jpg";
//            memoLists.add(newMemo);
//            finish();
            return getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/searchSketch/sketch" + formatDate + ".jpg";
        } catch (Exception e) {
            Log.e("photo", "그림저장오류", e);
            Toast.makeText(this, "저장 실패", Toast.LENGTH_SHORT).show();
            return null;
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


    //    private void HttpMultiPart(final File file) {
//
//        new AsyncTask<Void, Void, JSONObject>() {
//
//            @Override
//            protected JSONObject doInBackground(Void... voids) {
//
//                String boundary = "^-----^";
//                String LINE_FEED = "\r\n";
//                String charset = "UTF-8";
//                OutputStream outputStream;
//                PrintWriter writer;
//
//                JSONObject result = null;
//                try {
//
//                    URL url = new URL("http://ec2-13-125-249-181.ap-northeast-2.compute.amazonaws.com/findSimilarImages.php");
//                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//
//                    connection.setRequestProperty("Content-Type", "multipart/form-data;charset=utf-8;boundary=" + boundary);
//                    connection.setRequestMethod("POST");
//                    connection.setDoInput(true);
//                    connection.setDoOutput(true);
//                    connection.setUseCaches(false);
//                    connection.setConnectTimeout(15000);
//
//                    outputStream = connection.getOutputStream();
//                    writer = new PrintWriter(new OutputStreamWriter(outputStream, charset), true);
//
//                    /** Body에 데이터를 넣어줘야 할경우 없으면 Pass **/
////                    writer.append("--" + boundary).append(LINE_FEED);
////                    writer.append("Content-Disposition: form-data; name=\"데이터 키값\"").append(LINE_FEED);
////                    writer.append("Content-Type: text/plain; charset=" + charset).append(LINE_FEED);
////                    writer.append(LINE_FEED);
////                    writer.append("데이터값").append(LINE_FEED);
////                    writer.flush();
//
//                    /** 파일 데이터를 넣는 부분**/
//                    writer.append("--" + boundary).append(LINE_FEED);
//                    writer.append("Content-Disposition: form-data; name=\"file\"; filename=\"" + file.getName() + "\"").append(LINE_FEED);
//                    writer.append("Content-Type: " + URLConnection.guessContentTypeFromName(file.getName())).append(LINE_FEED);
//                    writer.append("Content-Transfer-Encoding: binary").append(LINE_FEED);
//                    writer.append(LINE_FEED);
//                    writer.flush();
//
//                    FileInputStream inputStream = new FileInputStream(file);
//                    byte[] buffer = new byte[(int) file.length()];
//                    int bytesRead = -1;
//                    while ((bytesRead = inputStream.read(buffer)) != -1) {
//                        outputStream.write(buffer, 0, bytesRead);
//                    }
//                    outputStream.flush();
//                    inputStream.close();
//                    writer.append(LINE_FEED);
//                    writer.flush();
//
//                    writer.append("--" + boundary + "--").append(LINE_FEED);
//                    writer.close();
//
//                    int responseCode = connection.getResponseCode();
//                    if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
//                        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//                        String inputLine;
//                        StringBuffer response = new StringBuffer();
//                        while ((inputLine = in.readLine()) != null) {
//                            response.append(inputLine);
//                        }
//                        in.close();
//
//                        try {
//                            result = new JSONObject(response.toString());
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    } else {
//                        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
//                        String inputLine;
//                        StringBuffer response = new StringBuffer();
//                        while ((inputLine = in.readLine()) != null) {
//                            response.append(inputLine);
//                        }
//                        in.close();
//                        result = new JSONObject(response.toString());
//                    }
//
//                } catch (ConnectException e) {
//                    Log.e("tag", "ConnectException");
//                    e.printStackTrace();
//
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//                return result;
//            }
//
//            @Override
//            protected void onPostExecute(JSONObject jsonObject) {
//                super.onPostExecute(jsonObject);
//            }
//
//        }.execute();
//    }

    int serverResponseCode = 0;
    String upLoadServerUri = null;



    class UploadAsync extends AsyncTask<File, Void, Void> {
        File targetFile;
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
                        +uploadFilePath + "" + uploadFileName);

            } else {

                try {
                    // open a URL connection to the Servlet
                    FileInputStream fileInputStream = new FileInputStream(sourceFile);
                    URL url = new URL("http://ec2-13-125-249-181.ap-northeast-2.compute.amazonaws.com/sidi/uploadSerchSketch.php");
                    // Open a HTTP  connection to  the URL

                    conn = (HttpURLConnection) url.openConnection();

                    conn.setDoInput(true); // Allow Inputs
                    conn.setDoOutput(true); // Allow Outputs
                    conn.setUseCaches(false); // Don't use a Cached Copy
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Connection", "Keep-Alive");
                    conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                    conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                    conn.setRequestProperty("uploaded_file", uploadFileName);

                    dos = new DataOutputStream(conn.getOutputStream());
                    dos.writeBytes(twoHyphens + boundary + lineEnd);
                    dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                            + uploadFileName + "\"" + lineEnd);


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
//                                Toast.makeText(SketchSearch.this, "File Upload Complete.",
//                                        Toast.LENGTH_SHORT).show();

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
                            Toast.makeText(SketchSearch.this, "MalformedURLException",

                                    Toast.LENGTH_SHORT).show();

                        }

                    });


                    Log.e("Upload file to server", "error: " + ex.getMessage(), ex);

                } catch (Exception e) {



                    e.printStackTrace();


                    runOnUiThread(new Runnable() {

                        public void run() {
                            Toast.makeText(SketchSearch.this, "Got Exception : see logcat ",
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

                OkHttpClient client = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor()).build();
                //1. 서버에 카테고리 1번을 보내기
                //2. 기본 연도 1960으로 해서 보내기
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(ServerInterface.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(client)
                        .build();
                ServerInterface serverInterface = retrofit.create(ServerInterface.class);
//        Log.e("year",sendYear);
                serverInterface.similar(uploadFileName)
                        .enqueue(new Callback<SimilarImageResult>() {
                            @Override
                            public void onResponse(Call<SimilarImageResult> call, Response<SimilarImageResult> response) {


                                SimilarImageResult result = response.body();
                                Log.e("result", result.toString());

                                List<SimilarImageResult.Result> list = result.getResult();

                                Log.e("test", String.valueOf(response.body()));

                                for (SimilarImageResult.Result value : list) {
                                    String url = "http://ec2-13-125-249-181.ap-northeast-2.compute.amazonaws.com/sidi/deskimg/"+value.getDesign();
                                    Log.e("url",url);
                                    SimilarImageRcyData similarImageResult = new SimilarImageRcyData(
                                            url,
                                            Integer.parseInt(String.valueOf(Float.parseFloat(value.getSimilarity())*100).substring(0,2)));

                                    similarImages.add(similarImageResult);
                                    searchSketchAdapter.notifyDataSetChanged();
                                    rv_similarDesign.setFocusedByDefault(true);
                                    appNumberArr.add(value.getDesign());
                                }

                            }
                            @Override
                            public void onFailure(Call<SimilarImageResult> call, Throwable t) {
                                Log.e("networkError",t.toString());
                            }
                        });


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