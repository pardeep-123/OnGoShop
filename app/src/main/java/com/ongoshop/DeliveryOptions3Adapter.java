package com.ongoshop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DeliveryOptions3Adapter extends RecyclerView.Adapter<DeliveryOptions3Adapter.RecyclerViewHolder> {
    Context context;
    LayoutInflater inflater;
    Button btnBasket;


    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        ImageView ivOn,ivOff;
        int temp=2;
        LinearLayout ll_chackbox;
        public RecyclerViewHolder(View view) {
            super(view);
            ll_chackbox=view.findViewById(R.id.ll_chackbox);
            ivOn=view.findViewById(R.id.ivOn);
            ivOff=view.findViewById(R.id.ivOff);
        }
    }

    public DeliveryOptions3Adapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public DeliveryOptions3Adapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.list_del_option3, parent, false);
        DeliveryOptions3Adapter.RecyclerViewHolder viewHolder = new DeliveryOptions3Adapter.RecyclerViewHolder(v);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull final DeliveryOptions3Adapter.RecyclerViewHolder holder, int position) {
        holder.ll_chackbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.temp%2==0)
                {
                    holder. ivOff.setVisibility(View.GONE);
                    holder. ivOn.setVisibility(View.VISIBLE);
                }
                else
                {
                    holder.ivOn.setVisibility(View.GONE);
                    holder.ivOff.setVisibility(View.VISIBLE);
                }
                holder.temp++;
            }
        });
    }
    @Override
    public int getItemCount() {
        return 7;
    }


}



