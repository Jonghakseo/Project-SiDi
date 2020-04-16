package com.myapp.sidi.Interface;

import com.myapp.sidi.DTO.MainPageDesignResult;
import com.myapp.sidi.DTO.SearchingTabDesignResult;
import com.myapp.sidi.DTO.Test1;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ServerInterface {

    String BASE_URL = "http://ec2-13-125-249-181.ap-northeast-2.compute.amazonaws.com/";

    @GET("mainResponse.php")
    Call<MainPageDesignResult> signUp(@Query("cate") String cate,
                                      @Query("age") int age);

    @GET("temp.php")
    Call<List<Test1>> searching(@Query("cate")String cate,
                               @Query("sTime")String sTime,
                               @Query("eTime")String eTime,
                               @Query("nation")String nation,
                               @Query("dep_1")String dep_1,
                               @Query("dep_2")String dep_2,
                               @Query("dep_3")String dep_3,
                               @Query("dep_4")String dep_4,
                               @Query("dep_5")String dep_5);



}
