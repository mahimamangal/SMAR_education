package com.example.jayatulsiani.smar;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.widget.Toast.LENGTH_LONG;

public class ChartActivity extends AppCompatActivity {

    private static final String TAG = ChartActivity.class.getSimpleName();

    private ProgressDialog pDialog;
    private SQLiteHandler db;
    String email1;
    private float t,av;
    BarChart barChart;
    final ArrayList<String>label=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
barChart = findViewById(R.id.barChart1);

      //  String method = getIntent().getStringExtra("method");
        email1 = getIntent().getStringExtra("email");
     //   Toast.makeText(getApplicationContext(), String.valueOf(email1), LENGTH_LONG).show();
        ChartFragment chartFragment = new ChartFragment();
        Bundle bundle = new Bundle();
      //  bundle.putString("method", method);
        bundle.putString("email", email1);
        enterEmail(email1);
        //Float ti= (Float) enterEmail(email1);
         //Toast.makeText(getApplicationContext(), String.valueOf(ti), LENGTH_LONG);


    }

    private void enterEmail(final String regemail) {
        // Tag used to cancel the request
        String tag_string_req = "req_getemail";

        pDialog.setMessage("Updating ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_GETSCORE, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Updation Response: " + response.toString());

                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error) {

                        JSONObject user = jObj.getJSONObject("user");
                        String time = user.getString("time_taken");
                        String name = user.getString("name");
                       // Toast.makeText(getApplicationContext(), String.valueOf("hello"), LENGTH_LONG);
                        String avg=user.getString("average");

                       // String s = String.format("%.2f", avg);

                        t = Float.parseFloat(time);
                        av = Float.parseFloat(avg);

                        BarDataSet barDataSet1 = new BarDataSet(dataValues1(t, av), "Time taken");

                        ArrayList<String>labels=new ArrayList<>();
                        labels.add("Average");
                        labels.add(name);

                        BarData barData = new BarData(barDataSet1);
                      //  barData.addDataSet(barDataSet1);
                        barChart.setData(barData);

                        String[] str1=new String[]{"Average",name};
                        XAxis x=barChart.getXAxis();
                        x.setValueFormatter(new IndexAxisValueFormatter(str1));
                       x.setCenterAxisLabels(true);
                       // x.setPosition();
                        x.setPosition(XAxis.XAxisPosition.BOTTOM);
                        x.setGranularity(1);

                        x.setGranularityEnabled(true);

                        barChart.setDragEnabled(true);
                       // barChart.getXAxis().setAxisMinimum(0);
                        //barChart.getXAxis().setAxisMaximum(0+barChart.getBarData().getBarWidth(0.44f,0.08f)*2);
                        barChart.getAxisLeft().setAxisMinimum(0);

                       // barChart.groupBars(0,0.44f,0.08f);






                        barData.setBarWidth(0.5f);
                        barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);
                        barChart.animateY(1000);
                        barChart.setFitBars(true);
                        barChart.invalidate();

                        /*Description description=new Description();
                        description.setText(email1);
                        barChart.setDescription(description);

                        Description description1=new Description();
                        description.setText("average");
                        barChart.setDescription(description1);*/

                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Updation Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to forgot url
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", regemail);
                //params.put("password", newpassword);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
        //return t;
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    private ArrayList<BarEntry> dataValues1(float t, float av) {
        ArrayList<BarEntry> dataVals = new ArrayList<>();
        dataVals.add(new BarEntry(1, av));
        dataVals.add(new BarEntry(2, t));
        return dataVals;
    }

}
