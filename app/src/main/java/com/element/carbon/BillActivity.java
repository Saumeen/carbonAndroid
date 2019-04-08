package com.element.carbon;

import android.content.Intent;
import android.support.design.bottomappbar.BottomAppBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class BillActivity extends AppCompatActivity {

    private BottomAppBar bottomAppBar;
    private String sessionID,userID;
    TextView bill;
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        bottomAppBar = (BottomAppBar) findViewById(R.id.bottomAppBar);
        setSupportActionBar(bottomAppBar);
        final Intent intent = this.getIntent();
        Bundle b = intent.getExtras();
        sessionID = b.get("sessionID").toString();
        userID = b.get("userID").toString();
        bill = (TextView) findViewById(R.id.bill);

        finalBill();

    }

    private void finalBill(){
        final String url = "http://192.168.43.34/mall-client/finalBill.php";
        requestQueue = Volley.newRequestQueue(this);
        StringRequest stringrequest = new StringRequest
                (Request.Method.POST, url, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jo =new JSONObject(response);
                            String price =jo.getString("finalprice");
                            bill.setText(price);
                            }
                         catch (Exception e) {
                             Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                             e.printStackTrace();
                         }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("userid", userID);
                return params;
            }
        };
        requestQueue.add(stringrequest);

    }
}
