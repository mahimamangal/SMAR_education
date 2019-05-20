package com.example.jayatulsiani.smar;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;

public class NavActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    CardView biolo,phy,chem,elec;

    private TextView txtName, txtEmail;
    private SQLiteHandler db;
    private SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        biolo=(CardView) findViewById(R.id.biolo);
        chem=findViewById(R.id.chem);
        phy=findViewById(R.id.phy);
        //elec=(CardView) findViewById(R.id.elec);

       /* biolo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentUnity = new Intent(NavActivity.this, MultipleSceneActivity.class);
                intentUnity.putExtra("sub","0");
                startActivity(intentUnity);
            }
        });*/
        phy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Intent intentUnity = new Intent(NavActivity.this, MultipleSceneActivity.class);
                intentUnity.putExtra("sub","7");
                startActivity(intentUnity);
            }
        });

       biolo.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent=new Intent(NavActivity.this,bio_MainActivity.class);
               startActivity(intent);
           }
       });


        chem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(NavActivity.this,chem_MainActivity.class);
                startActivity(intent);
            }
        });
        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        txtName = (TextView) findViewById(R.id.txtName);
        txtEmail = (TextView) findViewById(R.id.userid);

        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }

        // Fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();

        String name = user.get("name");
        String email = user.get("email");

        // Displaying the user details on the screen
        txtName.setText("Welcome "+name);
//        txtEmail.setText(email);


    }

    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(NavActivity.this, StartLogin.class);
        startActivity(intent);
        finish();
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.


        int id = item.getItemId();

        if (id == R.id.nav_quiz) {
            HashMap<String, String> user = db.getUserDetails();

            //String name = user.get("name");
            String email1 = user.get("email");
            Intent intent=new Intent(this,MultipleSceneActivity.class);
            // intent.putExtra("method","bars");
            intent.putExtra("sub",email1);
            startActivity(intent);

            // Handle the camera action
        } else if (id == R.id.nav_test) {

            db = new SQLiteHandler(getApplicationContext());

            // session manager
            session = new SessionManager(getApplicationContext());

            if (!session.isLoggedIn()) {
                logoutUser();
            }

            // Fetching user details from sqlite
            HashMap<String, String> user = db.getUserDetails();

            String name = user.get("name");
            String email1 = user.get("email");
            Intent intent=new Intent(this,ChartActivity.class);
           // intent.putExtra("method","bars");
            intent.putExtra("email",email1);
            startActivity(intent);

        } /*else if (id == R.id.nav_share) {

        }*/ else if (id == R.id.nav_logout) {
            logoutUser();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
