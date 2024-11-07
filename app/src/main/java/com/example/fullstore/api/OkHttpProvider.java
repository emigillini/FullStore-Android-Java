package com.example.fullstore.api;

import android.content.Context;
import android.util.Log;

import com.example.fullstore.Data.SessionManager;

import java.io.File;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class OkHttpProvider {
    private static OkHttpClient client = null;
    private static SessionManager sessionManager;
    private static Cache cache;

    public static OkHttpClient getClient(SessionManager sessionMgr, Context context) {
        sessionManager = sessionMgr;

        if (client == null) {

            File cacheDir = new File(context.getCacheDir(), "http_cache");
            cache = new Cache(cacheDir, 10 * 1024 * 1024);  // 10 MB
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            Interceptor authInterceptor = chain -> {
                Request originalRequest = chain.request();
                Request.Builder requestBuilder = originalRequest.newBuilder();

                String token = sessionManager.getAuthToken();
                if (token != null) {
                    requestBuilder.addHeader("Authorization", "Token " + token);
                }

                Request requestWithAuth = requestBuilder.build();
                Response response = chain.proceed(requestWithAuth);

                if (response.code() == 401) {
                    sessionManager.clearSession();
                }
                return response;
            };
            Interceptor cacheInterceptor = chain -> {
                Request request = chain.request();
                String url = request.url().toString();

                if (url.contains("products") && request.method().equals("GET")) {

                    request = request.newBuilder()
                            .header("Cache-Control", "public, max-stale=600")
                            .build();
                }

                Response response = chain.proceed(request);

                if (response.cacheResponse() != null) {
                    Log.d("Cache", "Response from cache");
                } else if (response.networkResponse() != null) {
                    Log.d("Network", "Response from network");
                }

                return response;
            };

            client = new OkHttpClient.Builder()
                    .cache(cache)
                    .addInterceptor(loggingInterceptor)
                    .addInterceptor(authInterceptor)
                    .addInterceptor(cacheInterceptor)
                    .build();
        }
        return client;
    }

}