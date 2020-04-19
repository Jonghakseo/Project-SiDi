package com.myapp.sidi.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.myapp.sidi.Category.DepToImgConverter;
import com.myapp.sidi.Category.DeskInfo;
import com.myapp.sidi.ContractAndHelper.SelectFurnitureContract;
import com.myapp.sidi.ContractAndHelper.SelectFurnitureHelper;
import com.myapp.sidi.Adapter.Design_Adapter;
import com.myapp.sidi.DTO.Design_Data;
import com.myapp.sidi.DTO.MainPageDesignResult;
import com.myapp.sidi.InitSelectPage;
import com.myapp.sidi.Interface.ServerInterface;
import com.myapp.sidi.R;
import com.myapp.sidi.search.ViewDetail;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainPageTab extends AppCompatActivity {
    private Button btn_choice,btn_choiceRevise,btn_searchPage;
    private Button btn_rankTag_1,btn_rankTag_2,btn_rankTag_3,btn_rankTag_4,btn_rankTag_5;
    private TextView tv_year,tv_category;
    private TextView rankTagDep_1,rankTagDep_2,rankTagDep_3,rankTagDep_4,rankTagDep_5;
    private ImageView iv_design_1,iv_design_2,iv_design_3,iv_design_4,iv_design_5;
    private LinearLayout linearLayout_1,linear_designBtnList;
    private String choice_1,choice_2,choice_3,choice_4,choice_5;
    private String sendCategory="";
    private ArrayList<Design_Data> re_arrayList;
    private Design_Adapter designAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ServerInterface serverInterface;
    private int sendYear=0;
    private List choiceArr;
    private Spinner spinner_year;
    private DepToImgConverter depToImgConverter;




    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page_tab);
//        btn_choice= findViewById(R.id.btn_choice);

        btn_choiceRevise = findViewById(R.id.btn_choiceRevise);
        recyclerView = findViewById(R.id.recyclerView);
        btn_searchPage = findViewById(R.id.btn_searchPage);
        tv_category = findViewById(R.id.tv_category);

        iv_design_1 = findViewById(R.id.iv_design_1);
        iv_design_2 = findViewById(R.id.iv_design_2);
        iv_design_3 = findViewById(R.id.iv_design_3);
        iv_design_4 = findViewById(R.id.iv_design_4);
        iv_design_5 = findViewById(R.id.iv_design_5);

        btn_rankTag_1 = findViewById(R.id.btn_rankTag_1);
        btn_rankTag_2 = findViewById(R.id.btn_rankTag_2);
        btn_rankTag_3 = findViewById(R.id.btn_rankTag_3);
        btn_rankTag_4 = findViewById(R.id.btn_rankTag_4);
        btn_rankTag_5 = findViewById(R.id.btn_rankTag_5);

        rankTagDep_1 = findViewById(R.id.rankTagDep_1);
        rankTagDep_2 = findViewById(R.id.rankTagDep_2);
        rankTagDep_3 = findViewById(R.id.rankTagDep_3);
        rankTagDep_4 = findViewById(R.id.rankTagDep_4);
        rankTagDep_5 = findViewById(R.id.rankTagDep_5);


        spinner_year = findViewById(R.id.spinner_year);
        String[] str= getResources().getStringArray(R.array.year_item);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainPageTab.this,R.layout.spinner_item,str);
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner_year.setAdapter(arrayAdapter);

        spinner_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if(position==0){
                    sendYear=1970;
                    onResume();
                }else if (position==1){
                    sendYear=1980;
                    onResume();
                }else if (position==2){
                    sendYear=1990;
                    onResume();
                }else if (position==3){
                    sendYear=2000;
                    onResume();
                }else if (position==4){
                    sendYear=2010;
                    onResume();
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        if (sendYear==0){
            sendYear=1970;
        }

        linear_designBtnList = findViewById(R.id.linear_designBtnList);


        SelectFurnitureHelper helper = new SelectFurnitureHelper(getApplicationContext());
        SQLiteDatabase sqLiteDatabase = helper.getReadableDatabase();
        String[] readData = {
                BaseColumns._ID,
                SelectFurnitureContract.TableEntry.DELETE_KEY,
                SelectFurnitureContract.TableEntry.COLUMN_CHOICE_1,
                SelectFurnitureContract.TableEntry.COLUMN_CHOICE_2,
                SelectFurnitureContract.TableEntry.COLUMN_CHOICE_3,
                SelectFurnitureContract.TableEntry.COLUMN_CHOICE_4,
                SelectFurnitureContract.TableEntry.COLUMN_CHOICE_5
        };
        String selection = SelectFurnitureContract.TableEntry._ID + " = ?";
        String[] selectionArgs = { "1" };
        String sortOrder = SelectFurnitureContract.TableEntry._ID + " DESC";


        Cursor cursor = sqLiteDatabase.query(
                SelectFurnitureContract.TableEntry.TABLE_NAME,
                readData,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );

        choiceArr = new ArrayList<>();
        while(cursor.moveToNext()) {
            choice_1 = cursor.getString(cursor.getColumnIndexOrThrow(SelectFurnitureContract.TableEntry.COLUMN_CHOICE_1));
            choice_2 = cursor.getString(cursor.getColumnIndexOrThrow(SelectFurnitureContract.TableEntry.COLUMN_CHOICE_2));
            choice_3 = cursor.getString(cursor.getColumnIndexOrThrow(SelectFurnitureContract.TableEntry.COLUMN_CHOICE_3));
            choice_4 = cursor.getString(cursor.getColumnIndexOrThrow(SelectFurnitureContract.TableEntry.COLUMN_CHOICE_4));
            choice_5 = cursor.getString(cursor.getColumnIndexOrThrow(SelectFurnitureContract.TableEntry.COLUMN_CHOICE_5));

            if (!choice_1.isEmpty()){
                choiceArr.add(choice_1);
            }
            if (!choice_2.isEmpty()){
                choiceArr.add(choice_2);
            }
            if (!choice_3.isEmpty()){
                choiceArr.add(choice_3);
            }
            if (!choice_4.isEmpty()){
                choiceArr.add(choice_4);
            }
            if (!choice_5.isEmpty()){
                choiceArr.add(choice_5);
            }
        }
        cursor.close();
        //1. 카테고리 수만큼 버튼 생성하는 코드
        linearLayout_1 = findViewById(R.id.linearLayout_1);
        for(int i=0; i<choiceArr.size(); i++) {
            final Button button = new Button(this);
            button.setText(choiceArr.get(i).toString());
            button.setTextColor(Color.parseColor("#ffffff"));
            button.setBackgroundResource(Color.parseColor("#00000000"));
            button.setGravity(Gravity.CENTER);
            linearLayout_1.addView(button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String tmp= button.getText().toString();
                    sendCategory = categoryToEngConverter(tmp);
                    onResume();
                }
            });
        }





        depToImgConverter = new DepToImgConverter();

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (sendCategory.equals("")){
            sendCategory = categoryToEngConverter(choiceArr.get(0).toString());
        }
        Log.e("cate",sendCategory);


        //1.시대의 디자인 보여줄 리사이클러뷰

        linearLayoutManager = new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        re_arrayList = new ArrayList<>();
        designAdapter = new Design_Adapter(re_arrayList,this);
        recyclerView.setAdapter(designAdapter);


        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor()).build();
        //1. 서버에 카테고리 1번을 보내기
        //2. 기본 연도 1960으로 해서 보내기
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ServerInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        serverInterface = retrofit.create(ServerInterface.class);
//        Log.e("year",sendYear);
        serverInterface.signUp(sendCategory,sendYear)
                .enqueue(new Callback<MainPageDesignResult>() {
                    @Override
                    public void onResponse(Call<MainPageDesignResult> call, Response<MainPageDesignResult> response) {
                        MainPageDesignResult result = response.body();
                        Log.e("ResponseResult",result.toString());
                        String design = result.getDesign1();
                        String url1 = result.getUrl1();
                        String tag_1_1 = result.getTag1_1();
                        String tag_1_2 = result.getTag1_2();
                        String tag_1_3 = result.getTag1_3();
                        /**            형태 태그 가져오는 부분 추가                 **/
                        String depth1 = result.getDepth1();
                        String depth2 = result.getDepth2();
                        String depth3 = result.getDepth3();
                        String depth4 = result.getDepth4();
                        String depth5 = result.getDepth5();

                        //임시로 책상만 가능하게
                        DeskInfo deskInfo = new DeskInfo();
                        rankTagDep_1.setText(deskInfo.desk_dep1);
                        rankTagDep_2.setText(deskInfo.desk_dep2);
                        rankTagDep_3.setText(deskInfo.desk_dep3);
                        rankTagDep_4.setText(deskInfo.desk_dep4);
                        rankTagDep_5.setText(deskInfo.desk_dep5);

                        depToImgConverter.deskConverter_dep_1(depth1,btn_rankTag_1);
                        depToImgConverter.deskConverter_dep_2(depth2,btn_rankTag_2);
                        depToImgConverter.deskConverter_dep_3(depth3,btn_rankTag_3);
                        depToImgConverter.deskConverter_dep_4(depth4,btn_rankTag_4);
                        depToImgConverter.deskConverter_dep_5(depth5,btn_rankTag_5);



                        Log.e("tag",tag_1_1);
                        Log.e("tag",tag_1_2);
                        Log.e("tag",tag_1_3);
                        Log.e("depth1",depth1);
                        Log.e("depth2",depth2);
                        Log.e("depth3",depth3);
                        Log.e("depth4",depth4);
                        Log.e("depth5",depth5);
                        Glide.with(iv_design_1).load(url1).into(iv_design_1);

                        Design_Data design_data = new Design_Data(design,url1,tag_1_1,tag_1_2,tag_1_3);
                        re_arrayList.add(design_data);

                        String design2 = result.getDesign2();
                        String url2 = result.getUrl2();
                        String tag_2_1 = result.getTag2_1();
                        String tag_2_2 = result.getTag2_2();
                        String tag_2_3 = result.getTag2_3();
                        Glide.with(iv_design_2).load(url2).into(iv_design_2);

                        Design_Data design_data2 = new Design_Data(design2,url2,tag_2_1,tag_2_2,tag_2_3);
                        re_arrayList.add(design_data2);

                        String design3 = result.getDesign3();
                        String url3 = result.getUrl3();
                        String tag_3_1 = result.getTag3_1();
                        String tag_3_2 = result.getTag3_2();
                        String tag_3_3 = result.getTag3_3();
                        Glide.with(iv_design_3).load(url3).into(iv_design_3);

                        Design_Data design_data3 = new Design_Data(design3,url3,tag_3_1,tag_3_2,tag_3_3);
                        re_arrayList.add(design_data3);

                        String design4 = result.getDesign4();
                        String url4 = result.getUrl4();
                        String tag_4_1 = result.getTag4_1();
                        String tag_4_2 = result.getTag4_2();
                        String tag_4_3 = result.getTag4_3();
                        Glide.with(iv_design_4).load(url4).into(iv_design_4);

                        Design_Data design_data4 = new Design_Data(design4,url4,tag_4_1,tag_4_2,tag_4_3);
                        re_arrayList.add(design_data4);

                        String design5 = result.getDesign5();
                        String url5 = result.getUrl5();
                        String tag_5_1 = result.getTag5_1();
                        String tag_5_2 = result.getTag5_2();
                        String tag_5_3 = result.getTag5_3();
                        Glide.with(iv_design_5).load(url5).into(iv_design_5);

                        Design_Data design_data5 = new Design_Data(design5,url5,tag_5_1,tag_5_2,tag_5_3);
                        re_arrayList.add(design_data5);

                        designAdapter.notifyDataSetChanged();






                    }

                    @Override
                    public void onFailure(Call<MainPageDesignResult> call, Throwable t) {
                        Log.e("networkError",t.toString());
                    }
                });






        btn_choiceRevise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int count=choiceArr.size();
                Log.e("count", String.valueOf(choiceArr.size()));

                switch (count){
                    case 1:
                        InitSelectPage.REVISE_CODE=1;
                        Intent intent = new Intent(MainPageTab.this,InitSelectPage.class);
                        intent.putExtra("size","1");
                        intent.putExtra("existChoice_1",choiceArr.get(0).toString());
                        startActivity(intent);

                        break;
                    case 2:
                        InitSelectPage.REVISE_CODE=1;
                        Intent intent2 = new Intent(MainPageTab.this,InitSelectPage.class);
                        intent2.putExtra("size","2");
                        intent2.putExtra("existChoice_1",choiceArr.get(0).toString());
                        intent2.putExtra("existChoice_2",choiceArr.get(1).toString());
                        startActivity(intent2);
                        break;
                    case 3:
                        InitSelectPage.REVISE_CODE=1;
                        Intent intent3 = new Intent(MainPageTab.this,InitSelectPage.class);
                        intent3.putExtra("size","3");
                        intent3.putExtra("existChoice_1",choiceArr.get(0).toString());
                        intent3.putExtra("existChoice_2",choiceArr.get(1).toString());
                        intent3.putExtra("existChoice_3",choiceArr.get(2).toString());
                        startActivity(intent3);
                        break;
                    case 4:
                        InitSelectPage.REVISE_CODE=1;
                        Intent intent4 = new Intent(MainPageTab.this,InitSelectPage.class);
                        intent4.putExtra("size","4");
                        intent4.putExtra("existChoice_1",choiceArr.get(0).toString());
                        intent4.putExtra("existChoice_2",choiceArr.get(1).toString());
                        intent4.putExtra("existChoice_3",choiceArr.get(2).toString());
                        intent4.putExtra("existChoice_4",choiceArr.get(3).toString());
                        startActivity(intent4);
                        break;
                    case 5:
                        InitSelectPage.REVISE_CODE=1;
                        Intent intent5 = new Intent(MainPageTab.this,InitSelectPage.class);
                        intent5.putExtra("size","5");
                        intent5.putExtra("existChoice_1",choiceArr.get(0).toString());
                        intent5.putExtra("existChoice_2",choiceArr.get(1).toString());
                        intent5.putExtra("existChoice_3",choiceArr.get(2).toString());
                        intent5.putExtra("existChoice_4",choiceArr.get(3).toString());
                        intent5.putExtra("existChoice_5",choiceArr.get(4).toString());
                        startActivity(intent5);
                        break;
                    default:
                        break;
                }



            }
        });

        //1. 검색 페이지로 가는 버튼

        btn_searchPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPageTab.this, SearchingTab.class);
                startActivity(intent);
            }
        });

        //1. 연도 눌렸을 경우
        //2. 버튼을 누른 코드 값을 주고
        //3. 서버에 연도 값을 보낼때 해당 코드 값의 연도를 보낸다.
//        btn_year_1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                YEAR_1_CODE=1;
//                onResume();
//            }
//        });
//
//        btn_year_2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                YEAR_2_CODE=1;
//                onResume();
//            }
//        });
//
//        btn_year_3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                YEAR_3_CODE=1;
//                onResume();
//            }
//        });
//
//        btn_year_4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                YEAR_4_CODE=1;
//                onResume();
//            }
//        });
//
//        btn_year_5.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                YEAR_5_CODE=1;
//                onResume();
//            }
//        });





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

    public String categoryToEngConverter(String categoryName){
        switch (categoryName){
            case "#책상":
                categoryName="desk";
                break;
            case "#의자":
                categoryName="chair";
                break;
            case "#테이블":
                categoryName="table";
                break;
            case "#소파":
                categoryName="sofa";
                break;
            case "#전등/등":
                categoryName="lamp";
                break;
        }
        return categoryName;
    }




}
