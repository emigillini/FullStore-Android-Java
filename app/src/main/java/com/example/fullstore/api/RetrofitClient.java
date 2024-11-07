package com.example.fullstore.api;

import android.content.Context;

import com.example.fullstore.Data.SessionManager;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "http://10.0.2.2:3000/api/";
    private static Retrofit retrofit;

    public static Retrofit getRetrofit(SessionManager sessionManager, Context context) {
        if (retrofit == null) {
            OkHttpClient okHttpClient = OkHttpProvider.getClient(sessionManager, context);
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                    .build();
        }
        return retrofit;
    }
}

/*  "http://192.168.0.12:3000/api/"*/