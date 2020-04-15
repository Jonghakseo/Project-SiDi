package com.myapp.sidi.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    private Button btn_add;
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
        btn_add = findViewById(R.id.btn_add);

        Intent intent = getIntent();
//        furnitureChoiceResult = intent.getExtras().getString("furnitureChoiceResult");
//        startTimeChoiceResult = intent.getExtras().getString("startTimeChoiceResult");
//        endTimeChoiceResult = intent.getExtras().getString("endTimeChoiceResult");
//        nationChoiceResult = intent.getExtras().getString("nationChoiceResult");
//        dep_1_ChoiceResult = intent.getExtras().getString("dep_1_ChoiceResult");
//        dep_2_ChoiceResult = intent.getExtras().getString("dep_2_ChoiceResult");
//        dep_3_ChoiceResult = intent.getExtras().getString("dep_3_ChoiceResult");
//        dep_4_ChoiceResult = intent.getExtras().getString("dep_4_ChoiceResult");
//        dep_5_ChoiceResult = intent.getExtras().getString("dep_5_ChoiceResult");

        furnitureChoiceResult = "desk";
        startTimeChoiceResult = "1999";
        endTimeChoiceResult = "2010";
        nationChoiceResult = 1+","+2+","+3+","+"4";
        dep_1_ChoiceResult = "1";
        dep_2_ChoiceResult = "1"+","+"2"+","+"3"+","+"4"+","+"5"+","+"6"+","+"7"+","+"8"+","+"9";
        dep_3_ChoiceResult = "1"+","+"2"+","+"3";
        dep_4_ChoiceResult = "1"+","+"2"+","+"3"+","+"4";
        dep_5_ChoiceResult = "1"+","+"2"+","+"3";


        gridLayoutManager = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(gridLayoutManager);
        re_arrayList = new ArrayList<>();
        searchResultAdapter = new SearchResult_Adapter(re_arrayList,this);
        recyclerView.setAdapter(searchResultAdapter);

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

                for (SearchingTabDesignResult.Result result1 : list){

                    String url = "http://"+result1.getUrl();
                    SearchResultData searchResultData = new SearchResultData(url,
                            result1.getServerIndex(),
                            result1.getDesignNum(),
                            result1.getDesignCode(),
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
            }
        });






        final String url = "http://img.designmap.or.kr//IMG_P200/thumbnail/KR/D2330C/3020190001577/M001/thumb_3020190001577.jpg";
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                SearchResultData searchResultData = new SearchResultData(url,"id");
//                re_arrayList.add(searchResultData);
//                searchResultAdapter.notifyDataSetChanged();
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
