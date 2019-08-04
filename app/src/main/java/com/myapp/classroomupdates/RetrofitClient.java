package com.myapp.classroomupdates;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import com.myapp.classroomupdates.Globals;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    public static final String BASE_URL="http://minor.abhinavdev.com.np/api/";
    static Retrofit retrofitObj;

    public static Retrofit getRetrofitObj(){
        OkHttpClient.Builder clientBuilder= new OkHttpClient.Builder();
        HttpLoggingInterceptor interceptor1= new HttpLoggingInterceptor();
        interceptor1.setLevel(HttpLoggingInterceptor.Level.BODY);
        clientBuilder.addInterceptor(interceptor1);

        Gson gson=new GsonBuilder()
                .setLenient()
                .create();

        if(retrofitObj==null){
            retrofitObj=new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(clientBuilder.build())
                    .addConverterFactory(GsonConverterFactory.create(gson))
//                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();
        }
        return retrofitObj;
    }
}
