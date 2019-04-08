package com.element.carbon;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LogonActivity extends AppCompatActivity {

    TextView mTextView;
    Button mButton;
    RequestQueue requestQueue;
    TextInputLayout  emailField, passwordField;
    ProgressBar logOnProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logon);
        final String url = "http://192.168.43.34/mall-client/login.php";

        mTextView = (TextView) findViewById(R.id.progressStatus);
        mButton = (Button) findViewById(R.id.submit);
        logOnProgress = (ProgressBar) findViewById(R.id.LogOnProgress);
        emailField = (TextInputLayout) findViewById(R.id.nameLayout);
        passwordField = (TextInputLayout) findViewById(R.id.emailLayout);



        logOnProgress.setVisibility(View.INVISIBLE);

        requestQueue = Volley.newRequestQueue(this);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    logOnProgress.setVisibility(View.VISIBLE);
                    final String email = emailField.getEditText().getText().toString();
                    final String password = passwordField.getEditText().getText().toString();

                    //final String reqBody = "{email:'"+cName+"',password:'"+cEmail+"'}";
                    // JSONObject req = new JSONObject(reqBody);
                    //CacheHandler.saveFile("data.cache",getFilesDir(),reqBody);
                    StringRequest stringrequest = new StringRequest
                            (Request.Method.POST, url, new Response.Listener<String>() {

                                @Override
                                public void onResponse(String response) {
                                    try {
                                        logOnProgress.setVisibility(View.INVISIBLE);
                                        JSONObject jo = new JSONObject(response);
                                        if(jo.getBoolean("status")){
                                            Toast.makeText(getApplicationContext(),
                                                    jo.getString("session_id"), Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(LogonActivity.this, HomeActivity.class);
                                            intent.putExtra("sessionID", jo.getString("session_id"));
                                            intent.putExtra("userID",jo.getString("userid"));
                                            startActivity(intent);
                                        }
                                        else{
                                            Toast.makeText(getApplicationContext(),"Wrong details",Toast.LENGTH_SHORT).show();
                                        }

                                        //final String data = "{email: '" + email + "', password: '" + password + "'}";
                                        //CacheHandler.saveFile("data.cache", getFilesDir(), data);



                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    mTextView.setText(error.toString());

                                }
                            }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<>();
                            params.put("email", email);
                            params.put("password", password);
                            return params;
                        }
                    };
                    requestQueue.add(stringrequest);
                } catch (Exception e) {
                    mTextView.setText(e.toString());
                }
            }
        });
    }
}
