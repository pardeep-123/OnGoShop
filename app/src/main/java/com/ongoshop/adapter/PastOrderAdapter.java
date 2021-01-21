package com.ongoshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ongoshop.R;

public class PastOrderAdapter  extends RecyclerView.Adapter<PastOrderAdapter.RecyclerViewHolder> {
    Context context;
    LayoutInflater inflater;

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        public RecyclerViewHolder(View view) {
            super(view);
        }
    }

    public PastOrderAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public PastOrderAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.list_past, parent, false);
        PastOrderAdapter.RecyclerViewHolder viewHolder = new PastOrderAdapter.RecyclerViewHolder(v);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull PastOrderAdapter.RecyclerViewHolder holder, int position) {

    }
    @Override
    public int getItemCount() {
        return 3;
    }

}


