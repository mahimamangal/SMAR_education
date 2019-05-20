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

public class bio_MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private static final String TAG = "bio_MainActivity";
    private ArrayList<ListItem> allList;
    private ArrayList<ListItem> newList;

    private RecyclerView mRecyclerView;
    private MyRecyclerAdapter adapter;
    private SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bio__main);
        //Toast.makeText(this, "in bio main", Toast.LENGTH_LONG).show();

        mSearchView = (SearchView) findViewById(R.id.search_view);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.addOnItemTouchListener(
                new MyRecyclerItemClickListener(this, new MyRecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        switch (position) {
                            case 0:
                                Toast.makeText(getApplicationContext(),"inside switch", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(view.getContext(), MultipleSceneActivity.class);
                                intent.putExtra("sub", "0");
                                startActivity(intent);
                                break;

                            case 1:
                                Toast.makeText(getApplicationContext(),"inside switch 2", Toast.LENGTH_LONG).show();
                                intent = new Intent(getApplicationContext(), MultipleSceneActivity.class);
                                intent.putExtra("sub", "3");
                                startActivity(intent);
                                //Toast.makeText(getApplicationContext(),"intent thai gayu", Toast.LENGTH_LONG).show();
                                break;
                        }

                    }
                })
        );

        setList();

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new MyRecyclerAdapter(this, allList);
        mRecyclerView.setAdapter(adapter);

        setupSearchView();
    }

    public void setList() {

        allList = new ArrayList<ListItem>();

        ListItem item = new ListItem();
        item.setData("Human Brain", R.drawable.brain);
        allList.add(item);

        item = new ListItem();
        item.setData("Human Ear", R.drawable.ear);
        allList.add(item);

        /*item = new ListItem();
        item.setData("Samsung", R.drawable.ear);
        allList.add(item);

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
