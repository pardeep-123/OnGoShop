package com.ongoshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ongoshop.R;

public class DeliveryOptions1Adapter extends RecyclerView.Adapter<DeliveryOptions1Adapter.RecyclerViewHolder> {
    Context context;
    LayoutInflater inflater;
    Button btnBasket;


    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        int temp=2;
        LinearLayout ll_chackbox;
        ImageView ivOn,ivOff;
        public RecyclerViewHolder(View view) {
            super(view);
            ll_chackbox=view.findViewById(R.id.ll_chackbox);
            ivOn=view.findViewById(R.id.ivOn);
            ivOff=view.findViewById(R.id.ivOff);
        }
    }

    public DeliveryOptions1Adapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public DeliveryOptions1Adapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.list_del_option1, parent, false);
        DeliveryOptions1Adapter.RecyclerViewHolder viewHolder = new DeliveryOptions1Adapter.RecyclerViewHolder(v);

        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull final DeliveryOptions1Adapter.RecyclerViewHolder holder, int position) {
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



