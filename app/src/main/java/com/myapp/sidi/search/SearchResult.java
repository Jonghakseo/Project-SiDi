package com.myapp.sidi.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.myapp.sidi.Adapter.Design_Adapter;
import com.myapp.sidi.Adapter.SearchResult_Adapter;
import com.myapp.sidi.DTO.Design_Data;
import com.myapp.sidi.DTO.SearchResultData;
import com.myapp.sidi.R;

import java.util.ArrayList;

public class SearchResult extends AppCompatActivity {

    private Button btn_add;
    private ArrayList<SearchResultData> re_arrayList;
    private SearchResult_Adapter searchResultAdapter;
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        recyclerView = findViewById(R.id.recyclerView);
        btn_add = findViewById(R.id.btn_add);

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
