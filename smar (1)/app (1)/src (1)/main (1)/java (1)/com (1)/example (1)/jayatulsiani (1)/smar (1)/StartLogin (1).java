package com.example.jayatulsiani.smar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class StartLogin extends AppCompatActivity {

    private static final String TAG = StartLogin.class.getSimpleName();

    RelativeLayout rellay1, rellay2;

    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;
    EditText username1, pass;
    Button sub, passreset;
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {

        @Override
        public void run() {
            rellay1.setVisibility(View.VISIBLE);
            rellay2.setVisibility(View.VISIBLE);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_login);
        rellay1 = (RelativeLayout) findViewById(R.id.rellay1);
        rellay2 = (RelativeLayout) findViewById(R.id.rellay2);

        handler.postDelayed(runnable, 2000); //2000 is the timeout for the splash

        bindControls();

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Session manager
        session = new SessionManager(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(StartLogin.this, NavActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void bindControls() {
        username1 = findViewById(R.id.username);
        pass = findViewById(R.id.password);
        sub = findViewById(R.id.submit);
        passreset = findViewById(R.id.passreset);
    }

    public void OnLogin(View view) {

        String email = username1.getText().toString().trim().toLowerCase();
        String password = pass.getText().toString().trim();

        // Check for empty data in the form
        if (!email.isEmpty() && !password.isEmpty()) {
            // login user
            checkLogin(email, password);

        } else {
            // Prompt user to enter credentials
            Toast.makeText(getApplicationContext(),
                    "Please enter the credentials!", Toast.LENGTH_LONG)
                    .show();
        }
    }

    public void openReg(View view) {
        Intent myIntent = new Intent(view.getContext(), Registration.class);
        startActivityForResult(myIntent, 0);
        finish();
    }

    public void openForgotPassword(View view) {
        Intent myIntent = new Intent(view.getContext(), forgotpassword.class);
        startActivityForResult(myIntent, 0);
        finish();
    }

    private void checkLogin(final String email, final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pDialog.setMessage("Logging in ...");
        showDialog();

        StringRequest strReq = new StringRequest(Method.POST,
                AppConfig.URL_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        // user successfully logged in
                        // Create login session
                        session.setLogin(true);

                        // Now store the user in SQLite
                        String uid = jObj.getString("uid");

                        JSONObject user = jObj.getJSONObject("user");
                        String name = user.getString("name");
                        String email = user.getString("email");
                        String created_at = user
                                .getString("created_at");

                        // Inserting row in users table
                        db.addUser(name, email, uid, created_at);

                        // Launch main activity
                        Intent intent = new Intent(StartLogin.this,
                                NavActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", password);

                return params;
            }

        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}