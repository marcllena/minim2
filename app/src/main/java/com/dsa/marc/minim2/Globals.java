package com.dsa.marc.minim2;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

//Singleton per les Variables Globals
public class Globals {
    private static Globals instance;

    // Global variables
    public static String API_URL="http://api.dsamola.tk";
    ClientAPI clientRetrofit;


    //Al constructor, establim la connexió amb el servidor
    private Globals(){
        //Afagim el OkHTTP
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        //Creem el Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .client(client)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        clientRetrofit = retrofit.create(ClientAPI.class);
    }

    public static synchronized Globals getInstance(){
        if(instance==null){
            instance=new Globals();
        }
        return instance;
    }

    public static String getApiUrl() {
        return API_URL;
    }

    public ClientAPI getclientRetrofit() {
        return clientRetrofit;
    }
}
