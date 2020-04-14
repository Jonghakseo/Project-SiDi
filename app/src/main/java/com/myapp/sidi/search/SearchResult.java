package com.myapp.sidi.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.myapp.sidi.Adapter.SearchResult_Adapter;
import com.myapp.sidi.DTO.SearchResultData;
import com.myapp.sidi.DTO.SearchingTabDesignResult;
import com.myapp.sidi.DTO.Test1;
import com.myapp.sidi.Interface.ServerInterface;
import com.myapp.sidi.R;

import java.util.ArrayList;
import java.util.List;

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
    private String furnitureChoiceResult, startTimeChoiceResult,endTimeChoiceResult,nationChoiceResult,dep_1_ChoiceResult,dep_2_ChoiceResult,
            dep_3_ChoiceResult,dep_4_ChoiceResult,dep_5_ChoiceResult;
    private ServerInterface serverInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        recyclerView = findViewById(R.id.recyclerView);
        btn_add = findViewById(R.id.btn_add);

        Intent intent = getIntent();
        furnitureChoiceResult = intent.getExtras().getString("furnitureChoiceResult");
        startTimeChoiceResult = intent.getExtras().getString("startTimeChoiceResult");
        endTimeChoiceResult = intent.getExtras().getString("endTimeChoiceResult");
        nationChoiceResult = intent.getExtras().getString("nationChoiceResult");
        dep_1_ChoiceResult = intent.getExtras().getString("dep_1_ChoiceResult");
        dep_2_ChoiceResult = intent.getExtras().getString("dep_2_ChoiceResult");
        dep_3_ChoiceResult = intent.getExtras().getString("dep_3_ChoiceResult");
        dep_4_ChoiceResult = intent.getExtras().getString("dep_4_ChoiceResult");
        dep_5_ChoiceResult = intent.getExtras().getString("dep_5_ChoiceResult");



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ServerInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        serverInterface = retrofit.create(ServerInterface.class);

        serverInterface.searching(furnitureChoiceResult,
                startTimeChoiceResult,
                endTimeChoiceResult,
                nationChoiceResult,
                dep_1_ChoiceResult,
                dep_2_ChoiceResult,
                dep_3_ChoiceResult,
                dep_4_ChoiceResult,
                dep_5_ChoiceResult).enqueue(new Callback<List<Test1>>() {
            @Override
            public void onResponse(Call<List<Test1>> call, Response<List<Test1>> response) {
                List<Test1> list =response.body();
                Log.e("test",list.toString());
            }

            @Override
            public void onFailure(Call<List<Test1>> call, Throwable t) {

            }
        });



        gridLayoutManager = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(gridLayoutManager);
        re_arrayList = new ArrayList<>();
        searchResultAdapter = new SearchResult_Adapter(re_arrayList,this);
        recyclerView.setAdapter(searchResultAdapter);

        final String url = "http://img.designmap.or.kr//IMG_P200/thumbnail/KR/D2330C/3020190001577/M001/thumb_3020190001577.jpg";
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchResultData searchResultData = new SearchResultData(url,"id");
                re_arrayList.add(searchResultData);
                searchResultAdapter.notifyDataSetChanged();
            }
        });


    }
}
