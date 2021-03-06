package com.myapp.sidi.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.myapp.sidi.Adapter.Detail_SimilarDesignList_Adapter;
import com.myapp.sidi.Adapter.SearchDetail_Adapter;
import com.myapp.sidi.Adapter.SearchResult_Adapter;
import com.myapp.sidi.Adapter.SketchListAdapter;
import com.myapp.sidi.Category.CountryInfo;
import com.myapp.sidi.Category.DeskInfo;
import com.myapp.sidi.DTO.Detail_SimilarDesignRcy_Data;
import com.myapp.sidi.DTO.Detail_SimilarDesign_Server_Result;
import com.myapp.sidi.DTO.SimilarImageRcyData;
import com.myapp.sidi.DTO.SearchResultData;
import com.myapp.sidi.DTO.SketchListData;
import com.myapp.sidi.DTO.SketchListResult;
import com.myapp.sidi.Interface.ServerInterface;
import com.myapp.sidi.R;
import com.myapp.sidi.sketch.IdeaSketch;
import com.myapp.sidi.sketch.SketchList;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ViewDetail extends AppCompatActivity {
    //    private int searchMode; // 1. 국내 디자인 API, 2. 일본 디자인 API 3. 자체 서버 요청

    URL apiServerEndpoint;//api 요청 url
    private Intent intent;
    private String cate;
    private String country; //해당 디자인 출원 국가
    private String registrationNum; //검색 결과로 받은 출원번호
    private String registrationNum_jap;//일본 검색에 필요한 등록번호
    private int depth1;
    private int depth2;
    private int depth3;
    private int depth4;
    private int depth5;
    private int imagePos;
    Button btn_fullText, btn_scrap, btn_sketch, btn_moreDescription, btn_basicInfo,detail_btn_sketchList;
    ImageView main_design;
    TextView text_designNum, text_basicInfo, text_description, tagBox1, tagBox2, tagBox3, tagBox4, tagBox5;
    RecyclerView rv_otherDesign, rv_sameDepth, rv_othersSketch;
    boolean descript = true;//자세한 설명 접혀있는지
    boolean basicInfo = true;//기본 정보 접혀있는지
    private ServerInterface serverInterface;
    private OkHttpClient client;
    private Retrofit retrofit;
    private Gson gson;
    //다른 사람이 한 스케치 보는 리사이클러뷰 arr, 어뎁터
    private ArrayList<SketchListData> sketchListArr;
    private SketchListAdapter sketchListAdapter;
    private LinearLayoutManager linearLayoutManager_otherSketch;
    private RecyclerView detail_RV_othersSketch;


    //유사한 형태 보여주는 리사이클러뷰
    private ArrayList<Detail_SimilarDesignRcy_Data> detail_similarDesignRcy_Arr;
    private Detail_SimilarDesignList_Adapter detail_similarDesignList_adapter;
    private LinearLayoutManager linearLayoutManager_similarDesign;
    private RecyclerView detail_RV_sameDepth;
    private String SIMILAR_DESIGN_RESUME_CODE = "OFF";

    String articleName = "";//디자인명
    String applicantName = "";//출원인
    String applicationDate = "";//출원일
    //    String registrationNumber = registrationNum;//출원번호
    String applicantCountry = "";//출원국가
    String lastDispositionDescription = ""; // 현재 상태
    String designSummary = ""; // 디자인 요약
    String designDescription = ""; // 디자인 설명
    String fullTextFilePath = ""; // 원문 경로

    ArrayList<SimilarImageRcyData> ImagePaths = new ArrayList<>(); // 다른 도면 이미지들
    ArrayList<SimilarImageRcyData> sameDepthDesigns = new ArrayList<>(); // 같은 형태분류 이미지들
    ArrayList<SimilarImageRcyData> otherSketches = new ArrayList<>(); // 다른 사람의 스케치 이미지들

    ArrayList<SearchResultData> searchResultData = new ArrayList<>();

    private SearchDetail_Adapter otherDesignAdapter;
    private SearchDetail_Adapter sameDepthDesignAdapter;
    private SearchDetail_Adapter sketchDesignAdapter;
    private SearchResult_Adapter searchResultAdapter;
    private LinearLayoutManager linearLayoutManager1, linearLayoutManager2, linearLayoutManager3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_detail);

        btn_fullText = findViewById(R.id.detail_btn_fullText);
        btn_scrap = findViewById(R.id.detail_btn_scrap);
        btn_sketch = findViewById(R.id.detail_btn_sketch);
        btn_moreDescription = findViewById(R.id.detail_btn_moreDescription);
        btn_basicInfo = findViewById(R.id.detail_btn_basicInfo);
        detail_btn_sketchList=findViewById(R.id.detail_btn_sketchList);

        main_design = findViewById(R.id.detail_main_designView);

        text_basicInfo = findViewById(R.id.detail_text_basicInfo);
        text_description = findViewById(R.id.detail_text_description);
        text_designNum = findViewById(R.id.detail_text_designNum);

        rv_otherDesign = findViewById(R.id.detail_RV_otherDesigns);
        rv_sameDepth = findViewById(R.id.detail_RV_sameDepth);
        rv_othersSketch = findViewById(R.id.detail_RV_othersSketch);
        detail_RV_othersSketch = findViewById(R.id.detail_RV_othersSketch);
        detail_RV_sameDepth = findViewById(R.id.detail_RV_sameDepth);

        tagBox1 = findViewById(R.id.detail_tag1);
        tagBox2 = findViewById(R.id.detail_tag2);
        tagBox3 = findViewById(R.id.detail_tag3);
        tagBox4 = findViewById(R.id.detail_tag4);
        tagBox5 = findViewById(R.id.detail_tag5);

        tagBox1.setVisibility(View.GONE);
        tagBox2.setVisibility(View.GONE);
        tagBox3.setVisibility(View.GONE);
        tagBox4.setVisibility(View.GONE);
        tagBox5.setVisibility(View.GONE);

        ArrayList<TextView> tagBoxes = new ArrayList<>();
        tagBoxes.add(tagBox1);
        tagBoxes.add(tagBox2);
        tagBoxes.add(tagBox3);
        tagBoxes.add(tagBox4);
        tagBoxes.add(tagBox5);


        intent = getIntent();

        try {
            cate = intent.getExtras().getString("cate");
            country = intent.getExtras().getString("country");
            registrationNum = intent.getExtras().getString("registrationNum");
//            registrationNum_jap = intent.getExtras().getString("registrationNum_jap");
            depth1 = Integer.parseInt(intent.getExtras().getString("depth1"));
            depth2 = Integer.parseInt(intent.getExtras().getString("depth2"));
            depth3 = Integer.parseInt(intent.getExtras().getString("depth3"));
            depth4 = Integer.parseInt(intent.getExtras().getString("depth4"));
            depth5 = Integer.parseInt(intent.getExtras().getString("depth5"));


        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.e("cate", cate);
        Log.e("country", country);
        Log.e("registrationNum", registrationNum);
        Log.e("depth1", "" + depth1);
        Log.e("depth2", "" + depth2);
        Log.e("depth3", "" + depth3);
        Log.e("depth4", "" + depth4);
        Log.e("depth5", "" + depth5);

        //TODO 레트로핏 생성
        gson = new GsonBuilder()
                .setLenient()
                .create();
        client = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor()).build();
        retrofit = new Retrofit.Builder()
                .baseUrl(ServerInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();
        serverInterface = retrofit.create(ServerInterface.class);


        linearLayoutManager1 = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        linearLayoutManager2 = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        linearLayoutManager3 = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);

        rv_otherDesign.setLayoutManager(linearLayoutManager1);
        rv_sameDepth.setLayoutManager(linearLayoutManager2);
        rv_othersSketch.setLayoutManager(linearLayoutManager3);

        otherDesignAdapter = new SearchDetail_Adapter(ImagePaths, this);
        sameDepthDesignAdapter = new SearchDetail_Adapter(sameDepthDesigns, this);
        sketchDesignAdapter = new SearchDetail_Adapter(otherSketches, this);

        otherDesignAdapter.setOnItemClickListener(new SearchDetail_Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                System.out.println(position);
                changeMainDesign(position);
            }
        });
        sameDepthDesignAdapter.setOnItemClickListener(new SearchDetail_Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                System.out.println(position);
                //TODO 동일 디자인 검색 가능한 인텐트
            }
        });
        sketchDesignAdapter.setOnItemClickListener(new SearchDetail_Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                System.out.println(position);
                //TODO 스케치 조회 화면으로 넘기는 인텐트
            }
        });
        rv_otherDesign.setAdapter(otherDesignAdapter);
        rv_sameDepth.setAdapter(sameDepthDesignAdapter);
        rv_othersSketch.setAdapter(sketchDesignAdapter);


        //인텐트에서 국가와 출원번호 추출

        ArrayList<String> depths = new ArrayList<>();
        DeskInfo deskInfo = new DeskInfo();
        String[] desk_dep1_searchForm = deskInfo.desk_dep1_searchForm.split(",");
        String[] desk_dep2_searchForm = deskInfo.desk_dep2_searchForm.split(",");
        String[] desk_dep3_searchForm = deskInfo.desk_dep3_searchForm.split(",");
        String[] desk_dep4_searchForm = deskInfo.desk_dep4_searchForm.split(",");
        String[] desk_dep5_searchForm = deskInfo.desk_dep5_searchForm.split(",");
        if (depth1 != 0) {
            depths.add(" # " + desk_dep1_searchForm[depth1 - 1]);
        }
        if (depth2 != 0) {
            depths.add(" # " + desk_dep2_searchForm[depth2 - 1]);
        }
        if (depth3 != 0) {
            depths.add(" # " + desk_dep3_searchForm[depth3 - 1]);
        }
        if (depth4 != 0) {
            depths.add(" # " + desk_dep4_searchForm[depth4 - 1]);
        }
        if (depth5 != 0) {
            depths.add(" # " + desk_dep5_searchForm[depth5 - 1]);
        }
//        System.out.println(depth1);


        int id = 0;
        for (String depth : depths) {
            if (depth != null) {
                tagBoxes.get(id).setText(depth);
                tagBoxes.get(id).setVisibility(View.VISIBLE);
                id++;
            }
        }

//        search(searchMode);
        SearchAsync searchAsync = new SearchAsync();
        searchAsync.execute(country);


        btn_moreDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (descript) {
                    descript = !descript;
                    text_description.setVisibility(View.VISIBLE);
                    btn_moreDescription.setText("접기");
                } else {
                    descript = !descript;
                    text_description.setVisibility(View.GONE);
                    btn_moreDescription.setText("펼치기");
                }
            }
        });
        btn_basicInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (basicInfo) {
                    basicInfo = !basicInfo;
                    text_basicInfo.setVisibility(View.GONE);
                    btn_basicInfo.setText("펼치기");
                } else {
                    basicInfo = !basicInfo;
                    text_basicInfo.setVisibility(View.VISIBLE);
                    btn_basicInfo.setText("접기");
                }
            }
        });

        detail_btn_sketchList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewDetail.this, SketchList.class);
                intent.putExtra("registrationNum",registrationNum);
                startActivity(intent);
            }
        });
        //TODO 유사한 형태 디자인 리사이클러뷰
        serverInterface.detail_similarList(cate,
                String.valueOf(depth1),
                String.valueOf(depth2),
                String.valueOf(depth3),
                String.valueOf(depth4),
                String.valueOf(depth5)).enqueue(new Callback<Detail_SimilarDesign_Server_Result>() {
            @Override
            public void onResponse(Call<Detail_SimilarDesign_Server_Result> call, Response<Detail_SimilarDesign_Server_Result> response) {
                Detail_SimilarDesign_Server_Result result = response.body();
                List<Detail_SimilarDesign_Server_Result.Result> list = result.getResult();
                for(Detail_SimilarDesign_Server_Result.Result value : list){
                    Detail_SimilarDesignRcy_Data data = new Detail_SimilarDesignRcy_Data("http://"+value.getUrl(),
                            value.getServerIndex(),
                            value.getDesignNum(),
                            value.getRegistrationNum(),
                            value.getCountry(),
                            value.getDesignCode(),
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
                    detail_similarDesignRcy_Arr.add(data);
                    Log.e("now",detail_similarDesignRcy_Arr.get(0).getCountry());

                    detail_similarDesignList_adapter.notifyDataSetChanged();


                }


            }

            @Override
            public void onFailure(Call<Detail_SimilarDesign_Server_Result> call, Throwable t) {

            }
        });

        linearLayoutManager_similarDesign = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        detail_RV_sameDepth.setLayoutManager(linearLayoutManager_similarDesign);
        detail_similarDesignRcy_Arr = new ArrayList<>();
        detail_similarDesignList_adapter = new Detail_SimilarDesignList_Adapter(detail_similarDesignRcy_Arr,this);
        detail_RV_sameDepth.setAdapter(detail_similarDesignList_adapter);
        detail_similarDesignList_adapter.setOnItemClickListener(new Detail_SimilarDesignList_Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                //TODO 추후 카테고리 값도 가져와서 변경시켜줘야함// 지금은 책상 뿐이라서 괜츈
                Intent intent = new Intent(ViewDetail.this, ViewDetail.class);

                String tmp_country = detail_similarDesignRcy_Arr.get(position).getCountry();
                Log.e("tmp_country",tmp_country);

                CountryInfo countryInfo = new CountryInfo();
                String tmp_registrationNum="";

                if (country.equals(countryInfo.kor)){
                   tmp_registrationNum =  detail_similarDesignRcy_Arr.get(position).getDesignNum();
                }else if (country.equals(countryInfo.jap)){
                   tmp_registrationNum = detail_similarDesignRcy_Arr.get(position).getRegistrationNum();
                }

                Log.e("tmp_registrationNum",tmp_registrationNum);
                String tmp_depth1 = detail_similarDesignRcy_Arr.get(position).getDep_1();
                String tmp_depth2 = detail_similarDesignRcy_Arr.get(position).getDep_2();
                String tmp_depth3 = detail_similarDesignRcy_Arr.get(position).getDep_3();
                String tmp_depth4 = detail_similarDesignRcy_Arr.get(position).getDep_4();
                String tmp_depth5 = detail_similarDesignRcy_Arr.get(position).getDep_5();

                intent.putExtra("cate","desk");
                intent.putExtra("country",tmp_country);
                intent.putExtra("registrationNum",tmp_registrationNum);
                intent.putExtra("depth1",tmp_depth1);
                intent.putExtra("depth2",tmp_depth2);
                intent.putExtra("depth3",tmp_depth3);
                intent.putExtra("depth4",tmp_depth4);
                intent.putExtra("depth5",tmp_depth5);
                startActivity(intent);
                finish();

            }
        });




        //TODO 다른 사람이 한 스케치 보는 리사이클러뷰




        serverInterface.sketchList(registrationNum)
                .enqueue(new Callback<SketchListResult>() {
                    @Override
                    public void onResponse(Call<SketchListResult> call, Response<SketchListResult> response) {
                        SketchListResult result = response.body();
                        List<SketchListResult.Result> list = result.getResult();

                        for (SketchListResult.Result value : list){
                            Log.e("getUploadUser",value.getUploadUser());
                            Log.e("getUrl",value.getUrl());
                            SketchListData sketchListData = new SketchListData(
                                    value.getUploadUser(),"http://"+value.getUrl());
                            sketchListArr.add(sketchListData);
                            sketchListAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<SketchListResult> call, Throwable t) {

                        Log.e("networkError",t.toString());
                    }
                });

        linearLayoutManager_otherSketch = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        detail_RV_othersSketch.setLayoutManager(linearLayoutManager_otherSketch);
        sketchListArr = new ArrayList<>();
        sketchListAdapter = new SketchListAdapter(sketchListArr,this);
        detail_RV_othersSketch.setAdapter(sketchListAdapter);
        sketchListAdapter.setOnItemClickListener(new SketchListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Log.e("click","ok");
            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    class SearchAsync extends AsyncTask<String, Void, Void> {
        final String SEARCH_MODE_KOR = "대한민국 특허청"; //한국은 국내 api요청
        final String SEARCH_MODE_JAP = "일본 특허청"; //일본은 일본 api요청
        final String SEARCH_MODE_ETC = "etc"; //그 외는 자체 서버로 요청
        int status = 0;

        @Override
        protected Void doInBackground(String... strings) {
//            Log.e("country_tag", strings[0].substring(0, 2));
            switch (strings[0]) {
                case SEARCH_MODE_KOR:
                    try {
                        System.out.println(strings[0]);
                        String result;
                        apiServerEndpoint = new URL("http://plus.kipris.or.kr/kipo-api/kipi/designInfoSearchService/getBibliographyDetailInfoSearch?applicationNumber=" + registrationNum + "&ServiceKey=MycjEOwwAfMDHrTT1DIYF=Z4/8MIZY7ofDy4IzoWF14=");
                        // 서버 엔드포인트
                        HttpURLConnection myConnection = (HttpURLConnection) apiServerEndpoint.openConnection();
                        // 커넥션을 통해 요청 헤더를 추가하거나 응답을 읽는 작업 수행
                        if (myConnection.getResponseCode() == 200) {
                            status = 1;
                            // Success
                            myConnection.setRequestMethod("GET");
                            //get
                            InputStream is = myConnection.getInputStream();
//                                xml로 추출
                            XmlPullParser parser = Xml.newPullParser();
                            // create xml parser
                            parser.setInput(new InputStreamReader(is, "UTF-8"));
                            int eventType = parser.getEventType();
                            int imageIndex = 0;
                            while (eventType != XmlPullParser.END_DOCUMENT) {
                                if (eventType == XmlPullParser.START_DOCUMENT) {
                                    // XML 데이터 시작
                                } else if (eventType == XmlPullParser.START_TAG) {
                                    String startTag = parser.getName();
                                    // 시작 태그가 파싱. "<TAG>"
                                    switch (startTag) {
                                        case "articleName":
                                            parser.next();
//                                                System.out.println("text "+parser.getText());
                                            articleName = parser.getText();
                                            break;
                                        case "applicantName":
                                            parser.next();
//                                                System.out.println("text "+parser.getText());
                                            applicantName = parser.getText();
                                            break;
                                        case "applicationDate":
                                            parser.next();
//                                                System.out.println("text "+parser.getText());
                                            applicationDate = parser.getText();
                                            break;
                                        case "applicantCountry":
                                            parser.next();
//                                                System.out.println("text "+parser.getText());
                                            applicantCountry = parser.getText();
                                            break;
                                        case "lastDispositionDescription":
                                            parser.next();
                                            lastDispositionDescription = parser.getText();
                                            break;
                                        case "largePath":
                                            parser.next();
                                            imageIndex++;
                                            //********************수정해야함
                                            //imageIndex ?? 이미지 인지 아니면 유사도 인지 알아봐야함

                                            SimilarImageRcyData sdd = new SimilarImageRcyData(parser.getText(), imageIndex);
                                            ImagePaths.add(sdd);
                                            break;
                                        case "designSummary":
                                            parser.next();
                                            designSummary = parser.getText();
                                            break;
                                        case "designDescription":
                                            parser.next();
                                            designDescription = ""+parser.getText();
                                            break;
                                        case "fullTextFilePath":
                                            parser.next();
                                            fullTextFilePath = parser.getText();
                                            break;
                                    }
//                                        System.out.println("Start tag "+parser.getName());
                                } else if (eventType == XmlPullParser.END_TAG) {
                                    // 종료 태그가 파싱. "</TAG>"
//                                        System.out.println("End tag "+parser.getName());
                                } else if (eventType == XmlPullParser.TEXT) {
                                    // 시작 태그와 종료 태그 사이의 텍스트. "<TAG>TEXT</TAG>"
//                                        System.out.println("Text "+parser.getText());
                                }
                                eventType = parser.next();
                            }

                        } else {
                            status = 0;
                            // Error handling code goes here
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case SEARCH_MODE_JAP:
                    System.out.println("일본특허청 검색");
                    try {
                        String result;
                        apiServerEndpoint = new URL("http://plus.kipris.or.kr/openapi/rest/ForeignDesignBibliographicService/searchAllInfo?literatureNumber=" + registrationNum + "&CountryCode=JP&accessKey=MycjEOwwAfMDHrTT1DIYF=Z4/8MIZY7ofDy4IzoWF14=");
                        // 서버 엔드포인트
                        HttpURLConnection myConnection = (HttpURLConnection) apiServerEndpoint.openConnection();
                        // 커넥션을 통해 요청 헤더를 추가하거나 응답을 읽는 작업 수행
                        if (myConnection.getResponseCode() == 200) {
                            status = 1;
                            // Success
                            myConnection.setRequestMethod("GET");
                            //get
                            InputStream is = myConnection.getInputStream();
//                                xml로 추출
                            XmlPullParser parser = Xml.newPullParser();
                            // create xml parser
                            parser.setInput(new InputStreamReader(is, "UTF-8"));
                            int eventType = parser.getEventType();
                            applicantCountry = "일본 특허청";
                            while (eventType != XmlPullParser.END_DOCUMENT) {
                                if (eventType == XmlPullParser.START_DOCUMENT) {
                                    // XML 데이터 시작
                                } else if (eventType == XmlPullParser.START_TAG) {
                                    String startTag = parser.getName();
                                    // 시작 태그가 파싱. "<TAG>"
                                    switch (startTag) {
                                        case "articleNameKR":
                                            parser.next();
                                            articleName = parser.getText();
                                            break;
                                        case "creator":
                                            parser.next();
                                            applicantName = parser.getText();
                                            break;
                                        case "applicationDate":
                                            parser.next();
                                            applicationDate = parser.getText();
                                            break;
                                        case "creatContent":
                                            parser.next();
                                            designSummary = parser.getText();
                                            break;
                                        case "creatFunction":
                                            parser.next();
                                            designDescription = parser.getText();
                                            break;
                                    }
//                                        System.out.println("Start tag "+parser.getName());
                                } else if (eventType == XmlPullParser.END_TAG) {
                                    // 종료 태그가 파싱. "</TAG>"
//                                        System.out.println("End tag "+parser.getName());
                                } else if (eventType == XmlPullParser.TEXT) {
                                    // 시작 태그와 종료 태그 사이의 텍스트. "<TAG>TEXT</TAG>"
//                                        System.out.println("Text "+parser.getText());
                                }
                                eventType = parser.next();
                            }

                        } else {
                            status = 0;
                            // Error handling code goes here
                        }

                        apiServerEndpoint = new URL("http://plus.kipris.or.kr/openapi/rest/ForeignDesignImageAndFullTextService/designImageInfo?literatureNumber=" + registrationNum + "&CountryCode=JP&accessKey=MycjEOwwAfMDHrTT1DIYF=Z4/8MIZY7ofDy4IzoWF14=");
                        // 서버 엔드포인트
                        HttpURLConnection imgConnection = (HttpURLConnection) apiServerEndpoint.openConnection();
                        // 커넥션을 통해 요청 헤더를 추가하거나 응답을 읽는 작업 수행
                        if (imgConnection.getResponseCode() == 200) {
                            status = 1;
                            // Success
                            imgConnection.setRequestMethod("GET");
                            //get
                            InputStream is = imgConnection.getInputStream();
//                                xml로 추출
                            XmlPullParser parser = Xml.newPullParser();
                            // create xml parser
                            parser.setInput(new InputStreamReader(is, "UTF-8"));
                            int eventType = parser.getEventType();
                            int imageIndex = 0;
                            while (eventType != XmlPullParser.END_DOCUMENT) {
                                if (eventType == XmlPullParser.START_DOCUMENT) {
                                    // XML 데이터 시작
                                } else if (eventType == XmlPullParser.START_TAG) {
                                    String startTag = parser.getName();
                                    // 시작 태그가 파싱. "<TAG>"
                                    switch (startTag) {
                                        case "imagePath":
                                            parser.next();
                                            imageIndex++;
                                            SimilarImageRcyData sdd = new SimilarImageRcyData(parser.getText(), imageIndex);
                                            ImagePaths.add(sdd);
                                            break;
                                    }
                                    System.out.println("Start tag " + parser.getName());
                                } else if (eventType == XmlPullParser.END_TAG) {
                                    // 종료 태그가 파싱. "</TAG>"
//                                        System.out.println("End tag "+parser.getName());
                                } else if (eventType == XmlPullParser.TEXT) {
                                    // 시작 태그와 종료 태그 사이의 텍스트. "<TAG>TEXT</TAG>"
//                                        System.out.println("Text "+parser.getText());
                                }
                                eventType = parser.next();
                            }

                        } else {
                            status = 0;
                            // Error handling code goes here
                        }
                        fullTextFilePath = "http://abdg.kipris.or.kr/abdg/remoteFile.do?method=fullText&publ_key=JP," + registrationNum_jap + ",J01&cntry=JP&dsImgTpcd=jpn";
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                default://case SEARCH_MODE_ETC://or default
//                case SEARCH_MODE_ETC://or default
                    //TODO 레트로핏으로 세부정보 요청
//                    String fileName = registrationNum.replaceAll("/", "");
//                    String imagePath = "http://" + "ec2-13-125-249-181.ap-northeast-2.compute.amazonaws.com/sidi/deskimg/" + fileName + ".jpg";
//                    ImagePaths.add(new SimilarImageRcyData(imagePath, 1));
//                    status = 2;
                    break;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (status != 0) {
                System.out.println(country);
                //pdf 보기
                if (country.equals(SEARCH_MODE_KOR)) {
                    if (ImagePaths.size() > 0) {
                        text_designNum.setText("" + ImagePaths.get(0).getDesignId() + "번");
                        Glide.with(ViewDetail.this).load(ImagePaths.get(0).getDesign()).into(main_design);
                    }
                    StringBuffer sb = new StringBuffer();
                    sb.append("디자인명 : " + articleName + "\n");
                    sb.append("출원인 : " + applicantName + "\n");
                    sb.append("출원일 : " + applicationDate + "\n");
                    sb.append("출원국가 : " + applicantCountry + "\n");
                    sb.append("상태 : " + lastDispositionDescription + "\n");
                    sb.append("디자인 요약 : " + designSummary);
                    text_basicInfo.setText(sb);
                    text_description.setText(designDescription);

                    btn_fullText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent();
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.setAction(Intent.ACTION_VIEW);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.setDataAndType(Uri.parse(fullTextFilePath), "application/pdf");
                            try {
                                startActivity(intent);
                            } catch (ActivityNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    //pdf 보기
                } else if (country.equals(SEARCH_MODE_JAP)) {
                    if (ImagePaths.size() > 0) {
                        text_designNum.setText("" + ImagePaths.get(0).getDesignId() + "번");
                        Glide.with(ViewDetail.this).load(ImagePaths.get(0).getDesign()).into(main_design);
                    }
                    StringBuffer sb = new StringBuffer();
                    sb.append("디자인명 : " + articleName + "\n");
                    sb.append("출원인 : " + applicantName + "\n");
                    sb.append("출원일 : " + applicationDate + "\n");
                    sb.append("출원국가 : " + applicantCountry + "\n");
                    sb.append("디자인 요약 : " + designSummary);
                    text_basicInfo.setText(sb);
                    text_description.setText(designDescription);

                    btn_fullText.setOnClickListener(new View.OnClickListener() {
                        @ Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(fullTextFilePath));
                            startActivity(intent);
                        }
                    });
                } else {//etc
                    if (ImagePaths.size() > 0) {
                        text_designNum.setText("대표 이미지");
                        Glide.with(ViewDetail.this).load(ImagePaths.get(0).getDesign()).into(main_design);
                        StringBuffer sb = new StringBuffer();
//                        sb.append("디자인명 : " + articleName + "\n");
//                        sb.append("출원인 : " + applicantName + "\n");
//                        sb.append("출원일 : " + applicationDate + "\n");
                        sb.append("출원국가 : " + country + "\n");
//                        sb.append("디자인 요약 : " + designSummary);
                        text_basicInfo.setText(sb);
//                        text_description.setText(designDescription);
                        btn_fullText.setVisibility(View.GONE);
                        rv_otherDesign.setVisibility(View.GONE);
                    }
                }
                //레트로핏으로 서버에 스케치, 동일 디자인 요청
                retrofit = new Retrofit.Builder()
                        .baseUrl(ServerInterface.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                serverInterface = retrofit.create(ServerInterface.class);
//                serverInterface.signUp("desk",sendYear)
//                        .enqueue(new Callback<MainPageDesignResult>() {
//                            @Override
//                            public void onResponse(Call<MainPageDesignResult> call, Response<MainPageDesignResult> response) {
//                                MainPageDesignResult result = response.body();
//
//                                String design = result.getDesign1();
//                                String url1 = result.getUrl1();
//                                String tag_1_1 = result.getTag1_1();
//                                String tag_1_2 = result.getTag1_2();
//                                String tag_1_3 = result.getTag1_3();
//                                Log.e("tag",tag_1_1);
//                                Log.e("tag",tag_1_2);
//                                Log.e("tag",tag_1_3);
//
//                                Design_Data design_data = new Design_Data(design,url1,tag_1_1,tag_1_2,tag_1_3);
//                                re_arrayList.add(design_data);
//
//                                String design2 = result.getDesign2();
//                                String url2 = result.getUrl2();
//                                String tag_2_1 = result.getTag2_1();
//                                String tag_2_2 = result.getTag2_2();
//                                String tag_2_3 = result.getTag2_3();
//
//                                Design_Data design_data2 = new Design_Data(design2,url2,tag_2_1,tag_2_2,tag_2_3);
//                                re_arrayList.add(design_data2);
//
//                                String design3 = result.getDesign3();
//                                String url3 = result.getUrl3();
//                                String tag_3_1 = result.getTag3_1();
//                                String tag_3_2 = result.getTag3_2();
//                                String tag_3_3 = result.getTag3_3();
//
//                                Design_Data design_data3 = new Design_Data(design3,url3,tag_3_1,tag_3_2,tag_3_3);
//                                re_arrayList.add(design_data3);
//
//                                String design4 = result.getDesign4();
//                                String url4 = result.getUrl4();
//                                String tag_4_1 = result.getTag4_1();
//                                String tag_4_2 = result.getTag4_2();
//                                String tag_4_3 = result.getTag4_3();
//
//                                Design_Data design_data4 = new Design_Data(design4,url4,tag_4_1,tag_4_2,tag_4_3);
//                                re_arrayList.add(design_data4);
//
//                                String design5 = result.getDesign5();
//                                String url5 = result.getUrl5();
//                                String tag_5_1 = result.getTag5_1();
//                                String tag_5_2 = result.getTag5_2();
//                                String tag_5_3 = result.getTag5_3();
//
//                                Design_Data design_data5 = new Design_Data(design5,url5,tag_5_1,tag_5_2,tag_5_3);
//                                re_arrayList.add(design_data5);
//
//                                designAdapter.notifyDataSetChanged();
//
//
//                            }
//
//                            @Override
//                            public void onFailure(Call<MainPageDesignResult> call, Throwable t) {
//                                Log.e("networkError",t.toString());
//                            }
//                        });


                btn_sketch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ImagePaths.size() > 0){
                            Intent intent = new Intent(ViewDetail.this, IdeaSketch.class);
                            //TODO 인텐트 내용 채워줘야함 ( 현재 이미지 선택 후 스케치 페이지로 함께 전송 )
                            intent.putExtra("nowImage",ImagePaths.get(imagePos).getDesign());//현재 선택 이미지 url 보냄
                            intent.putExtra("registrationNum",registrationNum);//번호
                            intent.putExtra("country",country);//국가

                            startActivity(intent);
                        }else {
                            Toast.makeText(ViewDetail.this,"스케치 가능한 도면이 없습니다.",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                btn_scrap.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        //TODO 인텐트 내용 채워줘야함 ( 현재 출원번호 데이터 정보 스크랩해서 마이페이지로 보냄 )
                        startActivity(intent);
                    }
                });

                otherDesignAdapter.notifyDataSetChanged();//변경 반영
            }

            super.onPostExecute(aVoid);
        }
    }

    private void changeMainDesign(int position) {
        Glide.with(ViewDetail.this).load(ImagePaths.get(position).getDesign()).into(main_design);
        text_designNum.setText("도면 " + ImagePaths.get(position).getDesignId() + "");
        imagePos = position;
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