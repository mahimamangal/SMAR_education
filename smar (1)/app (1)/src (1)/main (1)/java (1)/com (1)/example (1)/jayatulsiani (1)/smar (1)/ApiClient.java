package com.example.jayatulsiani.smar;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
 public static String URL_GETSCORE = "http://hunnyvankawala.000webhostapp.com/get_test_info.php/";
//public static String URL_GETSCORE = "http://127.0.0.1:8088/smarbackup/get_test_info.php/";

    private static Retrofit retrofit,retrofit2;

    public static Retrofit getApiClient()
    {
        if(retrofit==null)
        {
            retrofit=new Retrofit.Builder().baseUrl(URL_GETSCORE).addConverterFactory(GsonConverterFactory.create()).build();

        }

        return  retrofit;
    }
}
