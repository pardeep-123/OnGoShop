package com.ongoshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ongoshop.R;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.RecyclerViewHolder> {
    Context context;
    LayoutInflater inflater;
    Button btnBasket;


    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        public RecyclerViewHolder(View view) {
            super(view);

        }
    }

    public OrderDetailAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public OrderDetailAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.list_order_detail1, parent, false);
        /*btnBasket=v.findViewById(R.id.btnBasket);
        btnBasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, BarcodeScannerActivity.class));
            }
        });*/
        OrderDetailAdapter.RecyclerViewHolder viewHolder = new OrderDetailAdapter.RecyclerViewHolder(v);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull OrderDetailAdapter.RecyclerViewHolder holder, int position) {

    }
    @Override
    public int getItemCount() {
        return 3;
    }


}


