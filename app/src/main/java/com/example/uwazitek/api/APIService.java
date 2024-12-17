package com.example.uwazitek.api;

import android.content.Context;
import com.example.uwazitek.auth.tokenManager.TokenManager;
import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIService {
    private static final String BASE_URL = "https://uwazi-api-2.onrender.com";
    private static Retrofit retrofit = null;

    public static Retrofit getClient(Context context) {

        if (retrofit == null) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .addInterceptor(authInterceptor(context))
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }

    // Function to create an interceptor that adds the token to the request header
    private static Interceptor authInterceptor(Context context) {
        return new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                String token = new TokenManager(context).getToken(); // Retrieve token from TokenManager
                okhttp3.Request.Builder requestBuilder = chain.request().newBuilder();

                // Add token to the header if it's available
                if (token != null && !token.isEmpty()) {
                    requestBuilder.addHeader("Authorization", "Bearer " + token);
                }

                return chain.proceed(requestBuilder.build());
            }
        };
    }
    public static AuthServiceInterface getAuthService(Context context) {
        return getClient(context).create(AuthServiceInterface.class);
    }

    public static DashboardServiceInterface getDashboardService(Context context) {
        return getClient(context).create(DashboardServiceInterface.class);
    }

}