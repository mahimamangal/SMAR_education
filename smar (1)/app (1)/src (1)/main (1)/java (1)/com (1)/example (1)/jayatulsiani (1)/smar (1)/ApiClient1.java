package com.example.jayatulsiani.smar;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient1 {
    public static String URL_GETavg= "http://hunnyvankawala.000webhostapp.com/get_avg.php/";

    private static Retrofit retrofit2;
    public static Retrofit getApiClient() {
        if (retrofit2 == null) {
            retrofit2 = new Retrofit.Builder().baseUrl(URL_GETavg).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit2;
    }

}
