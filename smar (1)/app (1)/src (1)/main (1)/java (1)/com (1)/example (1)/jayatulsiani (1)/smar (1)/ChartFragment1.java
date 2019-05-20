package com.example.jayatulsiani.smar;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChartFragment1 extends Fragment {

    private BarChart mbarChart;

    public ChartFragment1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_chart_fragment1, container, false);
        mbarChart=view.findViewById(R.id.barChart);
        getGrowthChart(getArguments().getString("email"),getArguments().getString("time_taken"));

        return view;
    }





    private void getGrowthChart(String email1, final String time)
    {
        Call<List<Growth>> call= ApiClient.getApiClient().create(ApiInterface.class).getGrowthInfo();

    call.enqueue(new Callback<List<Growth>>() {
        @Override
        public void onResponse(Call<List<Growth>> call, Response<List<Growth>> response) {
            if(response.body()!=null) {
                List<BarEntry> barEntries = new ArrayList<>();
                for(Growth growth:response.body()) {

                    barEntries.add(new BarEntry(10,20));
                    barEntries.add(new BarEntry(10, 20));
                    BarDataSet barDataSet = new BarDataSet(barEntries, "Average");
                    barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

                    BarData barData = new BarData(barDataSet);
                    barData.setBarWidth(0.1f);

                    mbarChart.setVisibility(View.VISIBLE);
                    mbarChart.animateY(5000);
                    mbarChart.setData(barData);
                    mbarChart.setFitBars(true);

                    Description description = new Description();
                    description.setText("Rank");
                    mbarChart.setDescription(description);
                    mbarChart.invalidate();
                }
            }

        }

        @Override
        public void onFailure(Call<List<Growth>> call, Throwable t) {

        }
    });



    }


}
