package com.example.jayatulsiani.smar;

import android.app.LauncherActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class chem_MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private static final String TAG = "chem_MainActivity";
    private ArrayList<ListItem> allList;

    private RecyclerView mRecyclerView;
    private MyRecyclerAdapter adapter;
    private SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chem__main);

        mSearchView = (SearchView) findViewById(R.id.search_view);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        setList();

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new MyRecyclerAdapter(this, allList);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addOnItemTouchListener(
                new MyRecyclerItemClickListener(this, new MyRecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        //Log.i(TAG, "Clicked Item Position : " + position);
                        switch (position) {
                            case 0:
                                Intent intent = new Intent(view.getContext(), MultipleSceneActivity.class);
                                intent.putExtra("sub", "13");
                                startActivity(intent);
                                break;
                            //Toast.makeText(getApplicationContext(),"Human brain",Toast.LENGTH_LONG).show();

                            case 1:
                                Intent intent1 = new Intent(view.getContext(), MultipleSceneActivity.class);
                                intent1.putExtra("sub", "14");
                                startActivity(intent1);
                                break;
                            //Toast.makeText(getApplicationContext(),"Human ear",Toast.LENGTH_LONG).show();

                        }

                    }
                })
        );

        setupSearchView();
    }

    public void setList() {

        allList = new ArrayList<ListItem>();

        ListItem item = new ListItem();
        item.setData("Sodium and Chlorine", R.drawable.salt);
        allList.add(item);

        item = new ListItem();
        item.setData("Sulphur and Oxygen", R.drawable.sulphur);
        allList.add(item);

        /*item = new ListItem();
        item.setData("Samsung", R.drawable.ear);
        allList.add(item);0

        item = new ListItem();
        item.setData("Sony", R.drawable.ear);
        allList.add(item);

        item = new ListItem();
        item.setData("HTC", R.drawable.brain);
        allList.add(item);*/



    }

    private void setupSearchView() {
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setQueryHint("Search Here");
    }

    public boolean onQueryTextChange(String newText) {
        adapter.filter(newText);
        return true;
    }

    public boolean onQueryTextSubmit(String query) {
        return false;
    }

}
