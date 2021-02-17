package com.ongoshop.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.ongoshop.R;
import com.ongoshop.activities.SubCategoriesActivity;
import com.ongoshop.pojo.CategoryListResponse;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.RecyclerViewHolder> {
    Context context;
    LayoutInflater inflater;

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;

        public RecyclerViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.tvName);
        }
    }

    ArrayList<CategoryListResponse.Body> list;

    public CategoryAdapter(Context context, ArrayList<CategoryListResponse.Body> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public CategoryAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.res_shopcategory, parent, false);
        CategoryAdapter.RecyclerViewHolder viewHolder = new CategoryAdapter.RecyclerViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CategoryAdapter.RecyclerViewHolder holder, int position) {
        holder.tvName.setText(list.get(position).getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, SubCategoriesActivity.class);
                i.putExtra("categoryId", list.get(position).getId().toString());
                i.putExtra("categoryName", list.get(position).getName());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}



