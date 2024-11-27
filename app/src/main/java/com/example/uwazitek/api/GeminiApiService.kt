package com.example.uwazitek.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

data class MessageRequest(val message: String)
data class MessageResponse(val response: String)

interface GeminiApiService {
    @POST("path/to/your/endpoint")
    suspend fun sendMessage(@Body request: MessageRequest): MessageResponse
}

object RetrofitInstance {
    val api: GeminiApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.google.com/gemini/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GeminiApiService::class.java)
    }
}