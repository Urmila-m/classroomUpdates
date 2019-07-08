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
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    public static final String BASE_URL="http://minor.abhinavd/api/";
    static Retrofit retrofitObj;

    public static Retrofit getRetrofitObj(){
        final SharedPreferences preferences= Globals.context.getSharedPreferences("myPreference", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= preferences.edit();

        Interceptor interceptor= new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request= chain.request();
                request= request.newBuilder().addHeader("Authentication", preferences.getString("token", "")).build();
                Response response= chain.proceed(request);
                return response;
            }
        };

        OkHttpClient client=new OkHttpClient().newBuilder().addInterceptor(interceptor).build();

        Gson gson=new GsonBuilder()
                .setLenient()
                .create();

        if(retrofitObj==null){
            retrofitObj=new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
//                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();
        }
        return retrofitObj;
    }

}
