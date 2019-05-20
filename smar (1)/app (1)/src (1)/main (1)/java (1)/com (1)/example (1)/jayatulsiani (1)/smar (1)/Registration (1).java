package com.example.jayatulsiani.smar;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registration extends Activity {
    private static final String TAG = Registration.class.getSimpleName();
    private Button btnRegister;
    private EditText inputFullName;
    private EditText inputEmail;
    private EditText inputPassword;
    private EditText conPassword;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;
    private static final String pattern_password="((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
    private Pattern pattern;
    private Matcher matcher;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        inputFullName = (EditText) findViewById(R.id.name);
        inputEmail = (EditText) findViewById(R.id.mail);
        inputPassword = (EditText) findViewById(R.id.pwd);
        conPassword = (EditText) findViewById(R.id.cpwd);
        btnRegister = (Button) findViewById(R.id.reg);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // Session manager
        session = new SessionManager(getApplicationContext());

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(Registration.this,
                    NavActivity.class);

            startActivity(intent);
            finish();
        }

        // Register Button Click event
        btnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String name1 = inputFullName.getText().toString().trim();
                String email1 = inputEmail.getText().toString().trim();
                String pass1 = inputPassword.getText().toString().trim();
                String cpass1 = conPassword.getText().toString().trim();

                int flag = 0;
                if(validateName(name1))
                    flag++;
                if(validateEmail(email1))
                    flag++;
                if(validatePassword(pass1, cpass1))
                    flag++;

                if (flag==3) {
                    registerUser(name1, email1, pass1);

                } else {
                    Toast.makeText(getApplicationContext(),
                            "Please enter your details!",Toast.LENGTH_LONG)
                            .show();
                }
            }
        });
    }
    // Link to Login Screen
    private boolean validateName(String name1) {

        if (name1.isEmpty()) {
            inputFullName.setError("Please enter your name!");
        } else {
            return true;
        }
        return false;
    }

    private boolean validateEmail(String email1) {


        if (email1.isEmpty()) {
            inputEmail.setError("Please enter a valid email!");
        } else {
            return (Patterns.EMAIL_ADDRESS.matcher(email1).matches());
        }
        return false;
    }

    private boolean validatePassword(String pass1, String cpass1) {



        if(pass1.isEmpty()) {
            inputPassword.setError("Please set a  password!");
        }
        if(cpass1.isEmpty()) {
            conPassword.setError("Please confirm the password!");
        }


        if(!pass1.equals(cpass1))
        {
            conPassword.setError("Passwords not matching");
        }

        pattern=Pattern.compile(pattern_password);
        matcher=pattern.matcher(pass1);
        if(matcher.matches())
        {
            return true;
        }
        else
        {
            inputPassword.setError("Not a strong password");
            return false;
        }

    }


    /**
     * Function to store user in MySQL database will post params(tag, name,
     * email, password) to register url
     * */
    private void registerUser(final String name, final String email,
                              final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        pDialog.setMessage("Registering ...");
        showDialog();

        StringRequest strReq = new StringRequest(Method.POST,
                AppConfig.URL_REGISTER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    Toast.makeText(getApplicationContext(),String.valueOf(error),Toast.LENGTH_LONG);
                    if (!error) {
                        // User successfully stored in MySQL
                        // Now store the user in sqlite
                        String uid = jObj.getString("uid");

                        JSONObject user = jObj.getJSONObject("user");
                        String name = user.getString("name");
                        String email = user.getString("email");
                        String created_at = user
                                .getString("created_at");

                        // Inserting row in users table
                        db.addUser(name, email, uid, created_at);

                        Toast.makeText(getApplicationContext(), "User successfully registered. Try login now!", Toast.LENGTH_LONG).show();

                        // Launch login activity
                        Intent intent = new Intent(
                                Registration.this,
                                StartLogin.class);
                        startActivity(intent);
                        finish();
                    } else {

                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name);
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
