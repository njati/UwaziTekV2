package com.example.uwazitek.auth.api

import android.content.Context
import com.example.uwazitek.auth.tokenManager.TokenManager
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    // Function to create an interceptor that adds the token to the request header
    private fun authInterceptor(context: Context): Interceptor {
        return Interceptor { chain ->
            val token = TokenManager(context).getToken() // Retrieve token from TokenManager
            val request = chain.request().newBuilder()

            // Add token to the header if it's available
            if (!token.isNullOrEmpty()) {
                request.addHeader("Authorization", "Bearer $token")
            }

            chain.proceed(request.build())
        }
    }

    // Function to build OkHttp client with the interceptor
    private fun getOkHttpClient(context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor(context))
            .build()
    }

    // Function to initialize Retrofit with OkHttp client
    private fun getRetrofit(context: Context): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://52.22.245.63/")
            .client(getOkHttpClient(context)) // Set custom OkHttp client
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // AuthService instance
    fun getAuthService(context: Context): AuthService {
        return getRetrofit(context).create(AuthService::class.java)
    }


}


//package com.example.uwazitek.auth.api
//
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//
//object RetrofitInstance {
//    private val retrofit by lazy {
//        Retrofit.Builder()
//            .baseUrl("https://your-backend-url.com/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//    }
//
//    val authService: AuthService by lazy {
//        retrofit.create(AuthService::class.java)
//    }
//}


