package com.example.uwazitek.api;

import com.example.uwazitek.api.dto.Login;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface AuthServiceInterface {
    @POST("api/v1/auth/login")
    @Headers("Content-Type: application/json")
    Call<ResponseBody> login(@Body Login login);

    @FormUrlEncoded
    @POST("/auth/signup")
    Call<ResponseBody> register(@Field("username") String username, @Field("password") String password);

    @GET("api/v1/policyholder")
    @Headers("Content-Type: application/json")
    Call<ResponseBody> getPolicyholderDetails();

}