package com.myapp.sidi.Interface;

import com.myapp.sidi.DTO.MainPageDesignResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ServerInterface {

    String BASE_URL = "http://ec2-13-125-249-181.ap-northeast-2.compute.amazonaws.com/";

    @GET("sidiMainPage_temp.php")
    Call<MainPageDesignResult> signUp(@Query("tag") String tag,
                                      @Query("year") String year);
}
