package com.machinetest.quiz.network;


import android.content.Context;
import android.util.Log;

import com.machinetest.quiz.utils.NetworkUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Javed.Salat on 18-03-2016.
 */

public class RestAPI {

    private static final String TAG = "RestAPI";

    public static Retrofit initializeRetrofit(Context context) {
        Retrofit retrofit = null;
        try {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addNetworkInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();

                    // Request customization: add request headers
                    Request.Builder requestBuilder = original.newBuilder()
                            .header("Cache-Control", "String.format(\"max-age=%d, no-cached, max-stale=%d\", 1, 0)")
                            .addHeader("Cache-Control", "no-store")
                            .cacheControl(CacheControl.FORCE_NETWORK); // <-- this is the important line

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });


            OkHttpClient client = httpClient
                    .readTimeout(60, TimeUnit.SECONDS)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .build();



           /* Commented because currently we are not using specific Certificate for authentication.*/
            retrofit = new Retrofit.Builder()
                    .baseUrl(NetworkUtils.API_SYNC_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(client)
                    .build();
        } catch (Exception e) {
           Log.e( "Exception", e.getMessage().toString());
        }
        return retrofit;
    }



}
