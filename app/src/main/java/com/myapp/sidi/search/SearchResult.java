package com.myapp.sidi.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.myapp.sidi.Adapter.SearchResult_Adapter;
import com.myapp.sidi.DTO.SearchResultData;
import com.myapp.sidi.DTO.SearchingTabDesignResult;
import com.myapp.sidi.Interface.ServerInterface;
import com.myapp.sidi.R;
import com.myapp.sidi.SketchSearch;
import com.myapp.sidi.main.MainPageTab;
import com.myapp.sidi.main.SearchingTab;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchResult extends AppCompatActivity {

    private Button btn_subSearch,btn_searchSketch,btn_reSearch;
    private ArrayList<SearchResultData> re_arrayList;
    private SearchResult_Adapter searchResultAdapter;
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private String furnitureChoiceResult,startTimeChoiceResult,endTimeChoiceResult,nationChoiceResult,dep_1_ChoiceResult,dep_2_ChoiceResult,
    dep_3_ChoiceResult,dep_4_ChoiceResult,dep_5_ChoiceResult;
    private int pageNum;
    private ServerInterface serverInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        recyclerView = findViewById(R.id.recyclerView);
        btn_subSearch = findViewById(R.id.btn_subSearch);
        btn_searchSketch= findViewById(R.id.btn_searchSketch);
        btn_reSearch = findViewById(R.id.btn_reSearch);

        final Intent intent = getIntent();
//        furnitureChoiceResult = intent.getExtras().getString("furnitureChoiceResult");
        furnitureChoiceResult = "desk";
        startTimeChoiceResult = intent.getExtras().getString("startTimeChoiceResult");
        endTimeChoiceResult = intent.getExtras().getString("endTimeChoiceResult");
        nationChoiceResult = intent.getExtras().getString("nationChoiceResult");
        dep_1_ChoiceResult = intent.getExtras().getString("dep_1_ChoiceResult");
        dep_2_ChoiceResult = intent.getExtras().getString("dep_2_ChoiceResult");
        dep_3_ChoiceResult = intent.getExtras().getString("dep_3_ChoiceResult");
        dep_4_ChoiceResult = intent.getExtras().getString("dep_4_ChoiceResult");
        dep_5_ChoiceResult = intent.getExtras().getString("dep_5_ChoiceResult");



        btn_reSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(SearchResult.this, SearchingTab.class);
                startActivity(intent1);
                finish();
            }
        });



//        Button temp = findViewById(R.id.btn_subSearch);
//        temp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(SearchResult.this, ViewDetail.class);
//                intent.putExtra("country","kor");
////                intent.putExtra("country","jap");
//                intent.putExtra("registrationNum","3020180027259");
////                intent.putExtra("registrationNum","1380586-000");//등록번호로 사용해야함 ㅠㅠ
//                intent.putExtra("depth1",1);
//                intent.putExtra("depth2",2);
//                intent.putExtra("depth3",3);
////                intent.putExtra("depth4","형태4");
//                intent.putExtra("depth5",5);
//                startActivity(intent);
//            }
//        });

        Log.e("furnitureChoiceResult",furnitureChoiceResult);
        Log.e("startTimeChoiceResult",startTimeChoiceResult);
        Log.e("endTimeChoiceResult",endTimeChoiceResult);
        Log.e("nationChoiceResult",nationChoiceResult);
        Log.e("dep_1_ChoiceResult",dep_1_ChoiceResult);
        Log.e("dep_2_ChoiceResult",dep_2_ChoiceResult);
        Log.e("dep_3_ChoiceResult",dep_3_ChoiceResult);
        Log.e("dep_4_ChoiceResult",dep_4_ChoiceResult);
        Log.e("dep_5_ChoiceResult",dep_5_ChoiceResult);

        if (dep_1_ChoiceResult.equals("ALL")){
            dep_1_ChoiceResult = "1,2,3,4,5,6,7,8,9,10,11,12,13";
        }
        if (dep_2_ChoiceResult.equals("ALL")){
            dep_2_ChoiceResult = "1,2,3,4,5,6,7,8,9,10,11,12,13";
        }
        if (dep_3_ChoiceResult.equals("ALL")){
            dep_3_ChoiceResult = "1,2,3,4,5,6,7,8,9,10,11,12,13";
        }
        if (dep_4_ChoiceResult.equals("ALL")){
            dep_4_ChoiceResult = "1,2,3,4,5,6,7,8,9,10,11,12,13";
        }
        if (dep_5_ChoiceResult.equals("ALL")){
            dep_5_ChoiceResult = "1,2,3,4,5,6,7,8,9,10,11,12,13";
        }

        Log.e("dep_1_ChoiceResult",dep_1_ChoiceResult);
        Log.e("dep_2_ChoiceResult",dep_2_ChoiceResult);
        Log.e("dep_3_ChoiceResult",dep_3_ChoiceResult);
        Log.e("dep_4_ChoiceResult",dep_4_ChoiceResult);
        Log.e("dep_5_ChoiceResult",dep_5_ChoiceResult);





        gridLayoutManager = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(gridLayoutManager);
        re_arrayList = new ArrayList<>();
        searchResultAdapter = new SearchResult_Adapter(re_arrayList,this);
        searchResultAdapter.setOnItemClickListener(new SearchResult_Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent1 = new Intent(SearchResult.this,ViewDetail.class);
                intent1.putExtra("country",re_arrayList.get(position).getCountry());
                intent1.putExtra("registrationNum",re_arrayList.get(position).getDesignNum());
                intent1.putExtra("depth1",re_arrayList.get(position).getDep_1());
                intent1.putExtra("depth2",re_arrayList.get(position).getDep_2());
                intent1.putExtra("depth3",re_arrayList.get(position).getDep_3());
                intent1.putExtra("depth4",re_arrayList.get(position).getDep_4());
                intent1.putExtra("depth5",re_arrayList.get(position).getDep_5());
                startActivity(intent1);
            }
        });

        recyclerView.setAdapter(searchResultAdapter);


        //잠시 주석처리
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();


        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor()).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ServerInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();
        serverInterface = retrofit.create(ServerInterface.class);

        pageNum=1;
        serverInterface.searching(furnitureChoiceResult,
                startTimeChoiceResult,
                endTimeChoiceResult,
                nationChoiceResult,
                dep_1_ChoiceResult,
                dep_2_ChoiceResult,
                dep_3_ChoiceResult,
                dep_4_ChoiceResult,
                dep_5_ChoiceResult,
                pageNum).enqueue(new Callback<SearchingTabDesignResult>() {
            @Override
            public void onResponse(Call<SearchingTabDesignResult> call, Response<SearchingTabDesignResult> response) {


                SearchingTabDesignResult result = response.body();
                Log.e("result",result.toString());
                List<SearchingTabDesignResult.Result> list = result.getResult();

                Log.e("test", String.valueOf(response.body()));

                for (SearchingTabDesignResult.Result result1 : list){


                    String url = "http://"+result1.getUrl();
                    SearchResultData searchResultData = new SearchResultData(url,
                            result1.getServerIndex(),
                            result1.getDesignNum(),
                            result1.getRegistrationNum(),
                            result1.getDesignCode(),
                            result1.getCountry(),
                            result1.getDesignName(),
                            result1.getRegisterPerson(),
                            result1.getDateApplication(),
                            result1.getDateRegistration(),
                            result1.getDatePublication(),
                            result1.getDep1(),
                            result1.getDep2(),
                            result1.getDep3(),
                            result1.getDep4(),
                            result1.getDep5()
                            );
                    re_arrayList.add(searchResultData);
                    searchResultAdapter.notifyDataSetChanged();

                    Log.e("getServerIndex",result1.getServerIndex());
                    Log.e("getDesignNum",result1.getDesignNum());
                    Log.e("getRegistrationNum",result1.getRegistrationNum());
                    Log.e("getCountry",result1.getCountry());
                    Log.e("getDesignCode",result1.getDesignCode());
                    Log.e("getUrl",result1.getUrl());
                    Log.e("getDesignName",result1.getDesignName());
                    Log.e("getRegisterPerson",result1.getRegisterPerson());
                    Log.e("getDateApplication",result1.getDateApplication());
                    Log.e("getDateRegistration",result1.getDateRegistration());
                    Log.e("getDatePublication",result1.getDatePublication());
                    Log.e("getDep1",result1.getDep1());
                    Log.e("getDep2",result1.getDep2());
                    Log.e("getDep3",result1.getDep3());
                    Log.e("getDep4",result1.getDep4());
                    Log.e("getDep5",result1.getDep5());
                }


                for (int i=0; i<list.size(); i++){

                    Log.e("list",list.get(i).toString());
                }
            }

            @Override
            public void onFailure(Call<SearchingTabDesignResult> call, Throwable t) {
                Log.e("error",t.toString());

                AlertDialog.Builder builder = new AlertDialog.Builder(SearchResult.this);
                builder.setTitle("해당 검색 내용이 없습니다.").setMessage("다시 검색해주세요");
                builder.setPositiveButton("돌아가기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent1 = new Intent(SearchResult.this, SearchingTab.class);
                        startActivity(intent1);
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });

        btn_searchSketch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchResult.this, SketchSearch.class);
                startActivity(intent);
            }
        });


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
