package com.example.uwazitek.api

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers

public interface DashboardServiceInterface {

    @GET("api/v1/auth/login")
    @Headers("Content-Type: application/json")
    suspend fun getDashboardStat(): Response<ResponseBody>

}