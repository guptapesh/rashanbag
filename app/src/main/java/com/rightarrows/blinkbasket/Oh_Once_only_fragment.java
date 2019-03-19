package com.rightarrows.blinkbasket;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Oh_Once_only_fragment extends android.support.v4.app.Fragment {

    ArrayList<Oh_once_pojo> arrayList_once;
    Oh_once_adapter adapter;
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.oh_once_fragment, container, false);

         recyclerView= (RecyclerView) rootView.findViewById(R.id.oh_once_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        arrayList_once = new ArrayList<Oh_once_pojo>();
        adapter = new Oh_once_adapter(getActivity(), arrayList_once);
        recyclerView.setAdapter(adapter);
        fetchItems();

        return rootView;

    }

    private void fetchItems() {
        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setCancelable(false);
        dialog.setTitle("Loading...");
        dialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, "http://hoax.rightarrows.com/blink/request.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.cancel();
                        try {
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
                params.put("action", "get-order-once-history");
                params.put("uid","3");
                return params;
            }
        };

        Volley.newRequestQueue(getActivity()).add(request);

    }

    private void handleresponse(String response) throws JSONException {


        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for(int i=0;i<jsonArray.length();i++)
        {

            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Oh_once_pojo pojo = new Oh_once_pojo();
            pojo.setOrder_id(jsonObject.getString("order_id"));
            pojo.setOrder_date(jsonObject.getString("order_date"));
            pojo.setOnce_amount(jsonObject.getString("total_amount"));
            pojo.setOrder_status(jsonObject.getString("order_status"));
            pojo.setDelivery_date(jsonObject.getString("deliver_date"));
            pojo.setItems(jsonObject.getString("item_list"));
            arrayList_once.add(pojo);


        }

        adapter.notifyDataSetChanged();

    }
}
