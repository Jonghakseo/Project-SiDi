package com.myapp.sidi.sketch;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.myapp.sidi.Adapter.SketchListAdapter;
import com.myapp.sidi.DTO.SketchListData;
import com.myapp.sidi.DTO.SketchListResult;
import com.myapp.sidi.Interface.ServerInterface;
import com.myapp.sidi.R;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SketchList extends AppCompatActivity {

    private ImageView iv_mainImage;
    private Button btn_scrap;
    private LinearLayout Linear_imageListPart;
    private String registrationNum;
    private OkHttpClient client;
    private Retrofit retrofit;
    private Gson gson;
    private ServerInterface serverInterface;
    private ArrayList<SketchListData> sketchListArr;
    private SketchListAdapter sketchListAdapter;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sketch_list);

        iv_mainImage = findViewById(R.id.iv_mainImage);
        btn_scrap = findViewById(R.id.btn_scrap);
        Linear_imageListPart =  findViewById(R.id.Linear_imageListPart);
        recyclerView = findViewById(R.id.recyclerView);



        Intent intent = getIntent();
        registrationNum = intent.getExtras().getString("registrationNum");
        Log.e("registrationNum",registrationNum);


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


        //TODO registNum 확인해보고 수정해야함
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
                            if (sketchListArr.get(0)!=null){
                                Glide.with(SketchList.this).load(sketchListArr.get(0).getUrl()).into(iv_mainImage);
                            }
                            sketchListAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<SketchListResult> call, Throwable t) {

                        Log.e("networkError",t.toString());
                    }
                });






        linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        sketchListArr = new ArrayList<>();
        sketchListAdapter = new SketchListAdapter(sketchListArr,this);
        recyclerView.setAdapter(sketchListAdapter);
        sketchListAdapter.setOnItemClickListener(new SketchListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                iv_mainImage.setBackground(null);
                Glide.with(SketchList.this).load(sketchListArr.get(position).getUrl()).into(iv_mainImage);

            }
        });


    }
    @Override
    protected void onResume() {
        super.onResume();









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
