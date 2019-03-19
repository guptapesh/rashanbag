package com.rightarrows.blinkbasket;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



public class ItemShowActivity extends AppCompatActivity {

    ArrayList<AndroidVersion> androidVersions;
    DataAdapter adapter;
    private ProgressDialog pDialog;
    String cat_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_show);

        Intent intent =getIntent();
        cat_id=intent.getStringExtra("cat_id");

        initViews();
        fetchitems();
    }

    private void initViews() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(layoutManager);

        androidVersions = new ArrayList<AndroidVersion>();
        adapter = new DataAdapter(getApplicationContext(), androidVersions);
        recyclerView.setAdapter(adapter);
    }

    private void fetchitems() {


        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setTitle("Loading...");
        dialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, "http://hoax.rightarrows.com/blink/request.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.cancel();
                        try {
                            Log.d("fromfetch","1");
                            handleresponse(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.cancel();
            }
        }
        ) {
            @Override   //use to post data to servers.
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("action", "get-item-list");
                params.put("sub_cat_id",cat_id);
                return params;
            }
        };

        Volley.newRequestQueue(this).add(request);

    }


    private void handleresponse(String response) throws JSONException {

        Log.d("fromfetch","3");
        JSONArray jsonArray = new JSONArray(response);

        for(int i=0;i<jsonArray.length();i++)
        {

            JSONObject jsonObject = jsonArray.getJSONObject(i);
            AndroidVersion androidVersion = new AndroidVersion();
            androidVersion.setItem_name(jsonObject.getString("item_name"));
            androidVersion.setItem_desc(jsonObject.getString("item_desc"));
            androidVersion.setItem_price(jsonObject.getString("item_price"));
            androidVersion.setItem_id(jsonObject.getString("item_id"));
            androidVersion.setItem_img(jsonObject.getString("item_img"));

            androidVersions.add(androidVersion);
            Log.d("hello",jsonObject.getString("item_id"));


        }
        adapter.notifyDataSetChanged();


    }




   /* private ArrayList<AndroidVersion> prepareData(){

        ArrayList<AndroidVersion> android_version = new ArrayList<>();
        for(int i=0;i<android_version_names.length;i++){
            AndroidVersion androidVersion = new AndroidVersion();
            androidVersion.setAndroid_version_name(android_version_names[i]);
            androidVersion.setAndroid_image_url(android_image_urls[i]);
            android_version.add(androidVersion);
        }
        return android_version;
    }*/
}