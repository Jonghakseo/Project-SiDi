package com.myapp.sidi.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.myapp.sidi.Adapter.Design_Adapter;
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
    Button btn_fullText, btn_scrap, btn_sketch, btn_moreDescription;
    ImageView main_design;
    TextView text_designNum, text_basicInfo, text_description, tagBox1, tagBox2, tagBox3, tagBox4, tagBox5;
    RecyclerView rv_others;
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
    ArrayList<SearchDetailData> ImagePaths = new ArrayList<>(); //이미지들
    private SearchDetail_Adapter designAdapter;
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
        rv_others = findViewById(R.id.detail_RV_otherDesigns);
        tagBox1 = findViewById(R.id.detail_tag1);
        tagBox2 = findViewById(R.id.detail_tag2);
        tagBox3 = findViewById(R.id.detail_tag3);
        tagBox4 = findViewById(R.id.detail_tag4);
        tagBox5 = findViewById(R.id.detail_tag5);

        linearLayoutManager = new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false);
        rv_others.setLayoutManager(linearLayoutManager);
        designAdapter = new SearchDetail_Adapter(ImagePaths,this);
        designAdapter.setOnItemClickListener(new SearchDetail_Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                System.out.println(position);
                changeMainDesign(position);
            }
        });
        rv_others.setAdapter(designAdapter);

        intent = getIntent();
        try {
            country = intent.getExtras().getString("country");
            registrationNum = intent.getExtras().getString("registrationNum");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //인텐트에서 국가와 출원번호 추출

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

                designAdapter.notifyDataSetChanged();//변경 반영
            }

            super.onPostExecute(aVoid);
        }
    }

    private void changeMainDesign(int position){
        Glide.with(ViewDetail.this).load(ImagePaths.get(position).getDesign()).into(main_design);
        text_designNum.setText(""+ImagePaths.get(position).getDesignId()+"번");
    }

}
