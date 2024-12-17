package com.example.uwazitek.api

import ClaimResponse
import com.example.uwazitek.api.dto.Login
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthServiceInterface {
    @POST("api/v1/auth/login")
    @Headers("Content-Type: application/json")
    fun login(@Body login: Login): Call<ResponseBody>

    @GET("api/v1/insurance/claims")
    @Headers("Content-Type: application/json")
    fun getClaims(
    ): Call<List<ClaimResponse>>
}
