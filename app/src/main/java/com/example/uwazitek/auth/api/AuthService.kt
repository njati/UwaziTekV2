package com.example.uwazitek.auth.api

import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Response

interface AuthService {
    @POST("api/auth/login")
    suspend fun login(@Body loginData: Map<String, String>): Response<Map<String, String>>
}