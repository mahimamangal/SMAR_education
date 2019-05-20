package com.example.jayatulsiani.smar;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface1 {
    @GET("get_avg.php")
    Call<List<Growth>> getGrowthInfo();

}
