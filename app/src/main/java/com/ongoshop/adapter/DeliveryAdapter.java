package com.ongoshop.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ongoshop.R;
import com.ongoshop.activities.DeliveryActivity2;

public class DeliveryAdapter extends RecyclerView.Adapter<DeliveryAdapter.RecyclerViewHolder> {
    Context context;
    LayoutInflater inflater;
    LinearLayout ll_1;


    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        public RecyclerViewHolder(View view) {
            super(view);

        }
    }

    public DeliveryAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public DeliveryAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.list_pickup, parent, false);
        ll_1=v.findViewById(R.id.ll_1);
        ll_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, DeliveryActivity2.class));
            }
        });
        DeliveryAdapter.RecyclerViewHolder viewHolder = new DeliveryAdapter.RecyclerViewHolder(v);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull DeliveryAdapter.RecyclerViewHolder holder, int position) {

    }
    @Override
    public int getItemCount() {
        return 2;
    }


}


