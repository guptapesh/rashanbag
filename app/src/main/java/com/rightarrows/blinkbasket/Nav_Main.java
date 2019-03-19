package com.rightarrows.blinkbasket;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

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
import java.util.HashMap;
import java.util.Map;

public class Nav_Main extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    SliderLayout sliderLayout;
    ArrayList<ColumnPojo> rowSection;
    RecyclerView my_recycler_view;
    RecyclerViewDataAdapter adapter;
    public  static Map<String,String> myMap = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav__main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav__main, menu);
        return true;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
                                    rowPojos.add(new RowPojo(   json2.getString("name"),json2.getString("img"),json2.getString("item_id")));
                                    myMap.put(json2.getString("name"),json2.getString("item_id"));
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
        Volley.newRequestQueue(this).add(request);
    }
    private void setSliderViews() {

       /* final ProgressDialog dialog = new ProgressDialog(getApplicationContext());
        dialog.setMessage("Please Wait");
        dialog.setCancelable(false);
        dialog.show();*/
        final StringRequest request = new StringRequest(Request.Method.GET, "http://hoax.rightarrows.com/blink/request.php?action=get-home-slider",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject= new JSONObject(response);

                            for(int i=0;i<jsonObject.length();i++)
                            {
                                SliderView sliderView = new SliderView(getApplicationContext());
                                sliderView.setImageUrl(jsonObject.getString(i+""));
                                sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
                                sliderView.setDescription("setDescription ");
                                final int finalI = i;
                                sliderView.setOnSliderClickListener(new SliderView.OnSliderClickListener() {
                                    @Override
                                    public void onSliderClick(SliderView sliderView) {
                                        Toast.makeText(Nav_Main.this, "This is slider " + (finalI + 1), Toast.LENGTH_SHORT).show();
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
        Volley.newRequestQueue(this).add(request);
    }
}
