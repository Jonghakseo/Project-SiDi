package com.myapp.sidi.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.myapp.sidi.Adapter.SearchDetail_Adapter;
import com.myapp.sidi.DTO.SearchDetailData;
import com.myapp.sidi.R;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class ViewDetail extends AppCompatActivity {
    //    private int searchMode; // 1. 국내 디자인 API, 2. 일본 디자인 API 3. 자체 서버 요청

    URL apiServerEndpoint;//api 요청 url
    private Intent intent;
    private String country; //해당 디자인 출원 국가
    private String registrationNum; //검색 결과로 받은 출원번호
    private String depth1,depth2,depth3,depth4,depth5;
    Button btn_fullText, btn_scrap, btn_sketch, btn_moreDescription;
    ImageView main_design;
    TextView text_designNum, text_basicInfo, text_description, tagBox1, tagBox2, tagBox3, tagBox4, tagBox5;
    RecyclerView rv_otherDesign, rv_sameDepth, rv_othersSketch;
    boolean descript = true;//자세한 설명 접혀있는지


    String articleName = "";//디자인명
    String applicantName = "";//출원인
    String applicationDate = "";//출원일
    //    String registrationNumber = registrationNum;//출원번호
    String applicantCountry = "";//출원국가
    String lastDispositionDescription = ""; // 현재 상태
    String designSummary = ""; // 디자인 요약
    String designDescription = ""; // 디자인 설명
    String fullTextFilePath = ""; // 원문 경로
    ArrayList<SearchDetailData> ImagePaths = new ArrayList<>(); // 다른 도면 이미지들
    ArrayList<SearchDetailData> sameDepthDesigns = new ArrayList<>(); // 같은 형태분류 이미지들
    ArrayList<SearchDetailData> otherSketches = new ArrayList<>(); // 다른 사람의 스케치 이미지들
    private SearchDetail_Adapter otherDesignAdapter;
    private SearchDetail_Adapter sameDepthDesignAdapter;
    private SearchDetail_Adapter sketchDesignAdapter;
    private LinearLayoutManager linearLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_detail);

        btn_fullText = findViewById(R.id.detail_btn_fullText);
        btn_scrap = findViewById(R.id.detail_btn_scrap);
        btn_sketch = findViewById(R.id.detail_btn_sketch);
        btn_moreDescription = findViewById(R.id.detail_btn_moreDescription);

        main_design = findViewById(R.id.detail_main_designView);

        text_basicInfo = findViewById(R.id.detail_text_basicInfo);
        text_description = findViewById(R.id.detail_text_description);
        text_designNum = findViewById(R.id.detail_text_designNum);

        rv_otherDesign = findViewById(R.id.detail_RV_otherDesigns);
        rv_sameDepth = findViewById(R.id.detail_RV_sameDepth);
        rv_othersSketch = findViewById(R.id.detail_RV_othersSketch);

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
            country = intent.getExtras().getString("country");
            registrationNum = intent.getExtras().getString("registrationNum");
            depth1 = intent.getExtras().getString("depth1",null);
            depth2 = intent.getExtras().getString("depth2",null);
            depth3 = intent.getExtras().getString("depth3",null);
            depth4 = intent.getExtras().getString("depth4",null);
            depth5 = intent.getExtras().getString("depth5",null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        linearLayoutManager = new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false);
        rv_otherDesign.setLayoutManager(linearLayoutManager);
        rv_sameDepth.setLayoutManager(linearLayoutManager);
        rv_othersSketch.setLayoutManager(linearLayoutManager);
        otherDesignAdapter = new SearchDetail_Adapter(ImagePaths,this);
        sameDepthDesignAdapter = new SearchDetail_Adapter(sameDepthDesigns,this);
        sketchDesignAdapter = new SearchDetail_Adapter(otherSketches,this);

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
        depths.add(depth1);
//        System.out.println(depth1);
        depths.add(depth2);
        depths.add(depth3);
        depths.add(depth4);
        depths.add(depth5);

        int id = 0;
        for (String depth : depths) {
            if (depth!=null){
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
                if (descript){
                    descript = !descript;
                    text_description.setVisibility(View.VISIBLE);
                    btn_moreDescription.setText("접기");
                }else{
                    descript = !descript;
                    text_description.setVisibility(View.GONE);
                    btn_moreDescription.setText("펼치기");
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    class SearchAsync extends AsyncTask<String, Void, Void> {
        final String SEARCH_MODE_KOR = "kor"; //한국은 국내 api요청
        final String SEARCH_MODE_JAP = "jap"; //일본은 일본 api요청
        final String SEARCH_MODE_ETC = "etc"; //그 외는 자체 서버로 요청
        int status = 0;

        @Override
        protected Void doInBackground(String... strings) {

            switch (strings[0]) {
                case SEARCH_MODE_KOR:
                    try {
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
                                            SearchDetailData sdd = new SearchDetailData(parser.getText(),imageIndex);
                                            ImagePaths.add(sdd);
                                            break;
                                        case "designSummary":
                                            parser.next();
                                            designSummary = parser.getText();
                                            break;
                                        case "designDescription":
                                            parser.next();
                                            designDescription = parser.getText();
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
                    break;
                case SEARCH_MODE_ETC://or default
                    break;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (status != 0){
                text_designNum.setText(""+ImagePaths.get(0).getDesignId()+"번");
                Glide.with(ViewDetail.this).load(ImagePaths.get(0).getDesign()).into(main_design);
                StringBuffer sb = new StringBuffer();
                sb.append("디자인명 : "+articleName+"  /  ");
                sb.append("출원인 : "+applicantName+"\n");
                sb.append("출원국가 : "+applicantCountry+"  /  ");
                sb.append("상태 : "+lastDispositionDescription+"\n");
                sb.append("디자인 요약 : "+designSummary);
                text_basicInfo.setText(sb);
                text_description.setText(designDescription);
                //pdf 보기
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
                        }catch (ActivityNotFoundException e){
                            e.printStackTrace();
                        }
                    }
                });
                //pdf 보기

                btn_sketch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        //TODO 인텐트 내용 채워줘야함 ( 현재 이미지 선택 후 함께 전송 )
                        startActivity(intent);
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

    private void changeMainDesign(int position){
        Glide.with(ViewDetail.this).load(ImagePaths.get(position).getDesign()).into(main_design);
        text_designNum.setText("도면 "+ImagePaths.get(position).getDesignId()+"");
    }

}
