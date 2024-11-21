package com.example.uwazitek.api;

import android.util.Log;

import com.example.uwazitek.api.dto.Login;

import java.io.IOException;

import kotlin.jvm.Throws;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class APIService {
    private static final String BASE_URL = "http://52.22.245.63/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {

        if (retrofit == null) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }

    public static APIServiceInterface getAPIService() {
        return getClient().create(APIServiceInterface.class);
    }

    public static Call<ResponseBody> login(String policy_number, String password) {
        Log.i("APIService", "Logging in with policy_number: " + policy_number + " and password: " + password);
        return getAPIService().login(new Login(policy_number, password));
    }

    public static Call<ResponseBody> register(String username, String password) {
        return getAPIService().register(username, password);
    }
}


// Interceptor to log HTTP request and response