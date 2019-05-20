package com.example.jayatulsiani.smar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.widget.Toast.LENGTH_LONG;

public class forgotpassword extends AppCompatActivity {

    private static final String TAG = forgotpassword.class.getSimpleName();

    EditText regemail, newp;
    Button submit;

    private ProgressDialog pDialog;
    private SQLiteHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);

        regemail = findViewById(R.id.email1);
        newp = findViewById(R.id.newpwd);
        submit = findViewById(R.id.changepwd);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String remail = regemail.getText().toString().trim();
                String npassword = newp.getText().toString().trim();

                if (!remail.isEmpty() && !npassword.isEmpty()) {
                    // update database
                    updatePassword(remail, npassword);

                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(),
                            "Please enter all the credentials", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });
    }

    private void updatePassword(final String regemail,
                                final String newpassword) {
        // Tag used to cancel the request
        String tag_string_req = "req_update";

        pDialog.setMessage("Updating ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_FORGOTPASSWORD, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Updation Response: " + response);

                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        // User successfully stored in MySQL
                        // Now store the user in SQLite
                        String uid = jObj.getString("uid");
                        JSONObject user = jObj.getJSONObject("user");
                        String name = user.getString("name");
                        String email = user.getString("email");
                        String created_at = user
                                .getString("created_at");

                        // Inserting row in users table
                      //  db.updatePassword(regemail, newpassword);

                        // Launch main activity
                        Intent intent = new Intent(forgotpassword.this,
                                StartLogin.class);
                        startActivity(intent);
                        finish();
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
                params.put("password", newpassword);

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
