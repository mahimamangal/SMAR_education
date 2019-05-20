package com.example.jayatulsiani.smar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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

import static com.android.volley.VolleyLog.TAG;

public class ChartFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private BarChart mbarChart;

   // private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";

    // TODO: Rename and change types of parameters
    //private String mParam1;
    private String mParam2;
    private String mParam3;

  //  private OnFragmentInteractionListener mListener;

    public ChartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChartFragment newInstance(String param1, String param2, String param3) {
        ChartFragment fragment = new ChartFragment();
        Bundle args = new Bundle();
       // args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
           // mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mParam3 = getArguments().getString(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_chart, container, false);
        mbarChart=view.findViewById(R.id.barChart);
        getGrowthChart(getArguments().getString("method"),getArguments().getString("email"),getArguments().getString("time_taken"));
        //getGrowthChart(getArguments().getString("email"));
        return view;
    }

    private void getGrowthChart(final String method,final String email1, final  String time)
    {
        Call<List<Growth>> call=ApiClient.getApiClient().create(ApiInterface.class).getGrowthInfo();
       // Call<List<Growth>> call1=ApiClient1.getApiClient().create(ApiInterface1.class).getGrowthInfo();

        call.enqueue(new Callback<List<Growth>>() {
            @Override
            public void onResponse(Call<List<Growth>> call, Response<List<Growth>> response) {
                //if(response.body()!=null)
                //{

                    /*if(method.equals("bars")&& email1.equals(email1) && time.equals(time))
                    {*/
                       // Toast.makeText(getContext(),email1,Toast.LENGTH_LONG).show();
                        List<BarEntry>barEntries=new ArrayList<>();
                  /*      for (Growth growth:response.body())
                        {

                  */
                Log.e(TAG, "Error: " + time);

                int t=Integer.parseInt(time);
                           // Toast.makeText(getContext(), (int) t, Toast.LENGTH_LONG).show();
                            barEntries.add(new BarEntry(1,20));
                           barEntries.add(new BarEntry(2,20));

                    //    }

                        BarDataSet barDataSet=new BarDataSet(barEntries,"Average");
                        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

                        BarData barData=new BarData(barDataSet);
                        barData.setBarWidth(0.1f);

                        mbarChart.setVisibility(View.VISIBLE);
                        mbarChart.animateY(5000);
                        mbarChart.setData(barData);
                        mbarChart.setFitBars(true);

                        Description description=new Description();
                        description.setText("Rank");
                        mbarChart.setDescription(description);
                        mbarChart.invalidate();

                    }
                //}
            //}

            @Override
            public void onFailure(Call<List<Growth>> call, Throwable t) {

            }
        });
    }
}




//String method = getIntent().getStringExtra("method");
//        email1 = getIntent().getStringExtra("email");
//
//        ChartFragment chartFragment = new ChartFragment();
//        Bundle bundle = new Bundle();
//        bundle.putString("method", method);
//        bundle.putString("email", email1);
//        String str1 = email1;
//        //ClientStack = new HttpClientStack("http://hunnyvankawala.000webhostapp.com/get_test_info.php?str1="+str1);
//        enterEmail(email1);
//        chartFragment.setArguments(bundle);
//        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, chartFragment).commit();