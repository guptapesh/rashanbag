package com.rightarrows.blinkbasket;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class Oh_subs_adapter extends RecyclerView.Adapter<Oh_subs_adapter.ViewHolder> {
    private ArrayList<Oh_subs_pojo> subs;
    private Context context;

    public Oh_subs_adapter(Context context, ArrayList<Oh_subs_pojo> subs) {
        this.subs = subs;
        this.context = context;
    }
    @NonNull
    @Override
    public Oh_subs_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subscribe_order_history, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull Oh_subs_adapter.ViewHolder holder, int i) {

        holder.size.setText(subs.get(i).getSize_item());
        holder.deliveries_status.setText(" Deliverd/ Total Deliveries : "+subs.get(i).getDeliveries());
        holder.total_price.setText("₹"+subs.get(i).getTotal_price());
        holder.quantity.setText("₹"+subs.get(i).getQuantity_andprice());
        holder.order_id.setText("Subscribe Id: "+subs.get(i).getOrder_id());
        holder.from_Date.setText("From :"+subs.get(i).getFrom_date());
        holder.item_Desc.setText(subs.get(i).getItemname());
        holder.to_date.setText("To :"+subs.get(i).getTo_Date());
        Glide.with(context)
                .load(subs.get(i).getImg_url())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.user_profile_img)
                        .fitCenter())
                .into(holder.img_url);
    }

    @Override
    public int getItemCount() {
        return subs.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView size,total_price,quantity,deliveries_status,order_id,from_Date,to_date,item_Desc;
         private ImageView img_url;



        public ViewHolder(View view) {
            super(view);
            item_Desc= view.findViewById(R.id.tv_itemname_subs_oh);
            order_id= view.findViewById(R.id.orderid_subs_oh);
            from_Date= view.findViewById(R.id.tv_from_subs_oh);
            to_date= view.findViewById(R.id.tv_to_subs_oh);
            img_url= view.findViewById(R.id.img_subs_oh);
            size= view.findViewById(R.id.tv_size_subs_oh);
            total_price= view.findViewById(R.id.tv_price_subs_oh);
            quantity=view.findViewById(R.id.tv_quantity_subs_oh);
            deliveries_status= view.findViewById(R.id.tv_deliveries_subs_oh);


        }
    }
}