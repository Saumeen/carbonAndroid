package com.element.carbon;
import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ravi Tamada on 18/05/16.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {

    private Context mContext,context;
    private List<Item> itemList;
    private  RequestQueue requestQueue;
    private CartActivity remove1 = new CartActivity();
    String userID;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count, summary, price;
        public Button remove;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            summary = (TextView) view.findViewById(R.id.info);
            count = (TextView) view.findViewById(R.id.count);
            price = (TextView) view.findViewById(R.id.price);
            remove =(Button)view.findViewById(R.id.remove);
        }
    }


    public ItemAdapter(Context mContext, List<Item> albumList,String userID) {
        this.mContext = mContext;
        this.itemList = albumList;
        this.userID = userID;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Item item = itemList.get(position);
        holder.title.setText(item.getBarcode());
        holder.count.setText(new Integer(item.getProduct_quantity()).toString());
        holder.price.setText(new Integer(item.getProduct_price()).toString());
        holder.summary.setText(item.getProduct_summary());
        holder.remove.setTag(item.getProduct_id());
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("ibarcode",item.getBarcode());
                Toast.makeText(v.getContext(),item.getBarcode(),Toast.LENGTH_SHORT).show();
                removeItems(v.getContext(),item.getBarcode());
            }
        });
    }

        public void removeItems(final Context mContext, String barcode) {
            String url = "http://192.168.43.34/mall-client/remove.php";
            final String barcodeID = barcode;
            try {
                requestQueue  = Volley.newRequestQueue(mContext);

                StringRequest stringrequest = new StringRequest
                        (Request.Method.POST, url, new Response.Listener<String>() {

                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject job = new JSONObject(response);
                                    Log.e("print","call");
                                    Toast.makeText(mContext, response, Toast.LENGTH_LONG).show();
                                    if(job.getBoolean("status")) {
                                        prepareItems(mContext);
                                        Toast.makeText(mContext,"true",Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(mContext,"false",Toast.LENGTH_SHORT).show();

                                    }


                                } catch (Exception e) {
                                    //Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
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
                        params.put("barcode", barcodeID);
                        return params;
                    }
                };
                requestQueue.add(stringrequest);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // loading album cover using Glide library

//        holder.overflow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showPopupMenu(holder.overflow);
//            }
//        });


    /**
     * Showing popup menu when tapping on 3 dots
     */
    /*private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_album, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    /**
     * Click listener for popup menu items
     */
    /*class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_add_favourite:
                    Toast.makeText(mContext, "Add to favourite", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_play_next:
                    Toast.makeText(mContext, "Play next", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }*/

    private void prepareItems(final Context context) {
        String url = "http://192.168.43.34/mall-client/cartActivity.php";
        try {
            requestQueue = Volley.newRequestQueue(context);
            StringRequest stringrequest = new StringRequest
                    (Request.Method.POST, url, new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jo = new JSONObject(response);
                                JSONArray ja = jo.getJSONArray("cart");
                                JSONArray barcodes = jo.getJSONArray("barcode");
                                itemList.clear();
                                for(int i=0; i<ja.length();i++){
                                    JSONObject it = ja.getJSONObject(i);
                                    Item item = new Item(it.getString("PRODUCT_ID"), it.getString("FOOD_NAME"), it.getString("WEIGHT"), it.getInt("PRICE"),barcodes.getString(i));
                                    itemList.add(item);
                                }
                                    notifyDataSetChanged();


                            } catch (Exception e) {
                                Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public int getItemCount() {
        return itemList.size();
    }
}