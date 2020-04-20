package com.myapp.sidi.Interface;

import com.myapp.sidi.DTO.MainPageDesignResult;
import com.myapp.sidi.DTO.SearchingTabDesignResult;
import com.myapp.sidi.DTO.SimilarImageDetailData;
import com.myapp.sidi.DTO.SimilarImageResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface ServerInterface {

    String BASE_URL = "http://ec2-13-125-249-181.ap-northeast-2.compute.amazonaws.com/";

    @GET("mainResponse.php")
    Call<MainPageDesignResult> signUp(@Query("cate") String cate,
                                      @Query("age") int age);

    @Headers("Content-Type: application/json")
    @GET("/sidi/searchingEngine.php")
    Call<SearchingTabDesignResult> searching(@Query("cate")String cate,
                               @Query("stime")String sTime,
                               @Query("etime")String eTime,
                               @Query("nation")String nation,
                               @Query("dep_1")String dep_1,
                               @Query("dep_2")String dep_2,
                               @Query("dep_3")String dep_3,
                               @Query("dep_4")String dep_4,
                               @Query("dep_5")String dep_5,
                               @Query("pageNum")int pageNum);

    @GET("/sidi/findSimilarImages.php")
    Call<SimilarImageResult> similar(@Query("file") String file);

    @GET("/sidi/searchByNumber.php")
    Call<SimilarImageDetailData> similarDetail(@Query("cate") String cate,
                                               @Query("appNumber") String appNumber);


}
