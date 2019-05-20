package com.example.jayatulsiani.smar;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.POST;

public interface ApiInterface {


    @POST("get_test_info.php")
    Call<List<Growth>> getGrowthInfo();

    //Call<List<Growth>> getGrowthInfo();


}
