package com.rightarrows.blinkbasket;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private ArrayList<AndroidVersion> android;
    private Context context;

    public DataAdapter(Context context,ArrayList<AndroidVersion> android) {
        this.android = android;
        this.context = context;
    }

    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder viewHolder, int i) {

        viewHolder.item_name.setText(android.get(i).getItem_name());
        viewHolder.item_desc.setText(android.get(i).getItem_desc());
        viewHolder.item_quantity.setText(android.get(i).getItem_quantity());
        Picasso.with(context).load(android.get(i).getItem_img()).resize(240, 120).into(viewHolder.item_img);
    }


    @Override
    public int getItemCount() {
        return android.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView item_img;
        private TextView item_name;
        private TextView item_desc;
        private TextView item_quantity;

        public ViewHolder(View view) {
            super(view);

            item_img = view.findViewById(R.id.img_item);
            item_desc = view.findViewById(R.id.item_desc);
            item_name = view.findViewById(R.id.item_name);
            item_quantity = view.findViewById(R.id.item_quantity);

        }
    }

}
