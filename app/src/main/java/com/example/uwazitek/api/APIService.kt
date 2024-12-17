package com.example.uwazitek.api

import android.content.Context
import android.util.Log
import com.example.uwazitek.auth.tokenManager.TokenManager
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APIService {

    private const val BASE_URL = "https://uwazi-api.onrender.com/"
    private var retrofit: Retrofit? = null

    fun getClient(context: Context): Retrofit {
        if (retrofit == null) {
            // Create logging interceptor for debugging HTTP requests
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            // Create OkHttpClient with token interceptor and logging
            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(AuthInterceptor(context))
                .build()

            // Build Retrofit instance
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!
    }

    // Retrieve the AuthServiceInterface instance
    fun getAuthService(context: Context): AuthServiceInterface {
        return getClient(context).create(AuthServiceInterface::class.java)
    }

    // Retrieve the DashboardServiceInterface instance
    fun getDashboardService(context: Context): DashboardServiceInterface {
        return getClient(context).create(DashboardServiceInterface::class.java)
    }

    // Interceptor to add the token to request headers
    private class AuthInterceptor(private val context: Context) : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val token = TokenManager(context).getToken() // Retrieve the token
            val requestBuilder = chain.request().newBuilder()

            // Add Authorization header if the token exists
            if (!token.isNullOrEmpty()) {
                Log.e("----------------", token )
                requestBuilder.addHeader("Authorization", "Bearer $token")
            }

            return chain.proceed(requestBuilder.build())
        }
    }
}
