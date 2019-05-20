package com.example.jayatulsiani.smar;

import com.google.gson.annotations.SerializedName;

public class Growth {
    @SerializedName("time_taken")
    private int Time_taken;
   /* @SerializedName("rank")
    private float Rank;*/
   @SerializedName("average")
   private float avg1;

   public int getTime_taken() {
        return Time_taken;
    }
public float getAvg()
{
    return avg1;
}
   /* public float getRank() {
        return Rank;
    }*/
}
