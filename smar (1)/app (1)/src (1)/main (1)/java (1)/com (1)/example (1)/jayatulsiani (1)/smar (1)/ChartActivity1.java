package com.example.jayatulsiani.smar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class ChartActivity1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart1);
        String email1;
        email1 = getIntent().getStringExtra("email");
      //  Toast.makeText(getApplicationContext(), String.valueOf(email1), LENGTH_LONG).show();
        ChartFragment chartFragment = new ChartFragment();
        Bundle bundle = new Bundle();
        //  bundle.putString("method", method);
        bundle.putString("email", email1);
        chartFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,chartFragment).commit();


    }
}
