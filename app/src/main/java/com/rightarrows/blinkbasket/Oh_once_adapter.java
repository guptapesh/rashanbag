package com.rightarrows.blinkbasket;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class Oh_once_adapter extends RecyclerView.Adapter<Oh_once_adapter.ViewHolder> {
    private ArrayList<Oh_once_pojo> once;
    private Context context;
    int cancel=0;

    public Oh_once_adapter(Context context, ArrayList<Oh_once_pojo> once) {
        this.once = once;
        this.context = context;
    }
    @NonNull
    @Override
    public Oh_once_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_once_order_history, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final Oh_once_adapter.ViewHolder holder, final int i) {

        holder.once_order_date.setText(once.get(i).getOrder_date());
        holder.once_delivery_date.setText(once.get(i).getDelivery_date());
        holder.once_order_no.setText("Order No "+once.get(i).getOrder_id());
        holder.once_item_name.setText(once.get(i).getItems());
        holder.once_total_amount.setText("₹"+once.get(i).getOnce_amount());
        holder.once_Cancel_button.setVisibility(View.INVISIBLE);
        if(once.get(i).getOrder_status().equals("1"))
        {
            holder.once_Cancel_button.setVisibility(View.VISIBLE);
            cancel=1;
            holder.once_delivery_status.setText(" Not Delivered");
            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            String formattedDate = df.format(c);
            if(!formattedDate.equals(once.get(i).getDelivery_date())) {
                holder.once_Cancel_button.setTextColor(Color.parseColor("#FFE60909"));
                holder.once_delivery_status.setTextColor(Color.parseColor("#FF938C8C"));
            }
        }

        else if(once.get(i).getOrder_status().equals("2"))
        {
            holder.once_delivery_status.setText(" Delivered");
            holder.once_delivery_status.setTextColor(Color.parseColor("#FF20E72A"));
        }

        else if(once.get(i).getOrder_status().equals("3"))
        {
            holder.once_delivery_status.setText(" Cancelled");
            holder.once_delivery_status.setTextColor(Color.parseColor("#FFE60909"));
        }

        else if(once.get(i).getOrder_status().equals("4"))
        {
            holder.once_delivery_status.setText(" Error");
            holder.once_delivery_status.setTextColor(Color.parseColor("#FFE60909"));
        }

        holder.once_Cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cancel==1)
                {
                    AlertDialog alertDialog = new AlertDialog.Builder(context)
                            .setIcon(android.R.drawable.ic_dialog_alert)

                            .setMessage("Do you want to cancel this order")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int l) {
                                        setCancel(once.get(i).getOrder_id());
                                    dialogInterface.dismiss();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            })
                            .show();
                }


            }
        });

    }

    private void setCancel(final String s) {

        StringRequest request = new StringRequest(Request.Method.POST, "http://hoax.rightarrows.com/blink/request.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        ) {
            @Override   //use to post data to servers.
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("action", "cancel-order-once");
                params.put("uid","3");
                params.put("oid",s);
                return params;
            }
        };

        Volley.newRequestQueue(context).add(request);
    }

    @Override
    public int getItemCount() {
        return once.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView once_order_no;
        private TextView once_order_date;
        private TextView once_item_name;
        private TextView once_delivery_date;
        private TextView once_total_amount;
        private TextView once_delivery_status;
        private TextView once_Cancel_button;


        public ViewHolder(View view) {
            super(view);

        once_delivery_status= view.findViewById(R.id.once_delivery_status);
        once_item_name= view.findViewById(R.id.once_items);
        once_total_amount= view.findViewById(R.id.once_total_amount);
        once_order_no= view.findViewById(R.id.once_order_id);
        once_order_date= view.findViewById(R.id.once_order_date);
        once_delivery_date= view.findViewById(R.id.once_delivery_date);
        once_Cancel_button= view.findViewById(R.id.once_order_cancel_button);

        }
    }
}
