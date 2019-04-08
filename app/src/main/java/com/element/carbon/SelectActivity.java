package com.element.carbon;

import android.content.Intent;
import android.support.design.bottomappbar.BottomAppBar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SelectActivity extends AppCompatActivity {
    private BottomAppBar bottomAppBar;
    private RequestQueue requestQueue;
    private TextView productName, productBrand, productPrice, productDesc,billPrice;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        bottomAppBar = (BottomAppBar) findViewById(R.id.bottomAppBarSelect);
        setSupportActionBar(bottomAppBar);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        final String sessionID = (String) bundle.get("sessionID");
        final String userID = (String)bundle.get("userID");
        final String barcodeData = (String) bundle.get("barcodeData");
        requestQueue = Volley.newRequestQueue(this);
        productName = (TextView) findViewById(R.id.productName);
        productBrand = (TextView) findViewById(R.id.productBrand);
        productPrice = (TextView) findViewById(R.id.productPrice);
        productDesc = (TextView) findViewById(R.id.productDesc);

        getProduct(barcodeData);
        fab = (FloatingActionButton) findViewById(R.id.addCart);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToCart(sessionID,barcodeData,userID);
            }
        });



    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getProduct(String barcode) {
        String url = "http://192.168.43.34/mall-client/getcart.php";
        //String reqBody = "{barcode: " + barcode + "}";
        final String Barcode = barcode;
        try {
            requestQueue = Volley.newRequestQueue(this);
           // final JSONObject request = new JSONObject(reqBody);
            StringRequest stringrequest = new StringRequest
                    (Request.Method.POST, url, new Response.Listener<String>() {


                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jo = new JSONObject(response);
                                productName.setText(jo.getString("foodname"));
                                productBrand.setText(jo.getString("brandid"));
                                productPrice.setText(jo.getString("price"));
                                productDesc.setText(jo.getString("expirydate"));

                        } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    }){
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("barcode", Barcode);

                    return params;
                }
            };
            requestQueue.add(stringrequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addToCart(String sessionID, String barcode, final String userID){
        try{
            String url = "http://192.168.43.34/mall-client/addTocart.php";
            requestQueue = Volley.newRequestQueue(this);
            final String Barcode = barcode;
            final String userid = userID;
            //final String reqBody = "{customerID:"+ sessionID +" ,barcode:"+ barcode +"}";
            //JSONObject request = new JSONObject(reqBody);
            StringRequest stringRequest = new StringRequest
                    (Request.Method.POST, url, new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            try {
                                finish();
                                Toast.makeText(getApplicationContext(),userID,Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {


                        }
                    }){
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("barcode", Barcode);
                    params.put("userID",userid);
                    return params;
                }
            };
            requestQueue.add(stringRequest);
        } catch (Exception e) {

        }
    }

}
