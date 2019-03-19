package com.rightarrows.blinkbasket;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {


    SliderLayout sliderLayout;
    ArrayList<ColumnPojo> rowSection;
    RecyclerView my_recycler_view;
    RecyclerViewDataAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sliderLayout = findViewById(R.id.imageSlider);
        sliderLayout.setIndicatorAnimation(SliderLayout.Animations.FILL); //set indicator animation by using SliderLayout.Animations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderLayout.setScrollTimeInSec(5); //set scroll delay in seconds :

        setSliderViews();
        rowSection= new ArrayList<ColumnPojo>();

        setValue();
        my_recycler_view = (RecyclerView) findViewById(R.id.rv1);

        my_recycler_view.setHasFixedSize(true);

        adapter = new RecyclerViewDataAdapter(this, rowSection);
        my_recycler_view.setNestedScrollingEnabled(false);
        my_recycler_view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        my_recycler_view.setAdapter(adapter);
    }

    private void setValue() {
        final StringRequest request = new StringRequest(Request.Method.GET, Config.CATEGORY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject= new JSONObject(response);
                            for(int i=0;i<jsonObject.length();i++)
                            {

                                ColumnPojo pojo= new ColumnPojo();
                                pojo.setCategory_name("Category "+i+1);
                                JSONObject jsonObject1=jsonObject.getJSONObject(""+i);

                                ArrayList<RowPojo> rowPojos= new ArrayList<RowPojo>();
                                for(int j=0;j<jsonObject1.length();j++)
                                {

                                    JSONObject json2=jsonObject1.getJSONObject(""+j);

                                }

                                pojo.setRowSection(rowPojos);
                                rowSection.add(pojo);
                                adapter.notifyDataSetChanged();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), R.string.err_network, Toast.LENGTH_SHORT).show();
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(10000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(this).add(request);
    }
    private void setSliderViews() {

       /* final ProgressDialog dialog = new ProgressDialog(getApplicationContext());
        dialog.setMessage("Please Wait");
        dialog.setCancelable(false);
        dialog.show();*/
        Log.d("bolo5","kuch");
         final StringRequest request = new StringRequest(Request.Method.GET, Config.SLIDER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject= new JSONObject(response);

                            for(int i=0;i<jsonObject.length();i++)
                            {
                                SliderView sliderView = new SliderView(getApplicationContext());
                                sliderView.setImageUrl(jsonObject.getString(i+""));
                                Toast.makeText(MainActivity.this, "hey"+jsonObject.getString(i+""), Toast.LENGTH_SHORT).show();
                                sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
                                sliderView.setDescription("setDescription ");
                                final int finalI = i;
                                sliderView.setOnSliderClickListener(new SliderView.OnSliderClickListener() {
                                    @Override
                                    public void onSliderClick(SliderView sliderView) {
                                        Toast.makeText(MainActivity.this, "This is slider " + (finalI + 1), Toast.LENGTH_SHORT).show();
                                    }
                                });
                                sliderLayout.addSliderView(sliderView);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), R.string.err_network, Toast.LENGTH_SHORT).show();
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(10000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(this).add(request);
    }

}
