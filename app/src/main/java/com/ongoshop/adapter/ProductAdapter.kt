package com.ongoshop.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import com.ongoshop.R
import com.ongoshop.activities.ProductDetailActivity

class ProductAdapter(var context: Context) : RecyclerView.Adapter<ProductAdapter.RecyclerViewHolder>() {
    var inflater: LayoutInflater
    var rl_list: RelativeLayout? = null

    inner class RecyclerViewHolder(view: View?) : RecyclerView.ViewHolder(view!!)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val v = inflater.inflate(R.layout.list_products, parent, false)
        rl_list = v.findViewById(R.id.rl_list)
        rl_list!!.setOnClickListener(View.OnClickListener { context.startActivity(Intent(context, ProductDetailActivity::class.java)) })
        return RecyclerViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {}
    override fun getItemCount(): Int {
        return 4
    }

    init {
        inflater = LayoutInflater.from(context)
    }
}