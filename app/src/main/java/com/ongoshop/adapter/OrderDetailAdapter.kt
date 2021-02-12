package com.ongoshop.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.ongoshop.R

class OrderDetailAdapter(var context: Context) : RecyclerView.Adapter<OrderDetailAdapter.RecyclerViewHolder>() {
    var inflater: LayoutInflater
    var btnBasket: Button? = null

    inner class RecyclerViewHolder(view: View?) : RecyclerView.ViewHolder(view!!)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val v = inflater.inflate(R.layout.list_order_detail1, parent, false)
        /*btnBasket=v.findViewById(R.id.btnBasket);
        btnBasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, BarcodeScannerActivity.class));
            }
        });*/
        return RecyclerViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {}
    override fun getItemCount(): Int {
        return 3
    }

    init {
        inflater = LayoutInflater.from(context)
    }
}