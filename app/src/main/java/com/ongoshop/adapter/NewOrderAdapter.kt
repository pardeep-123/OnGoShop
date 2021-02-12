package com.ongoshop.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.ongoshop.R
import com.ongoshop.activities.DetailActivity

class NewOrderAdapter(var context: Context) : RecyclerView.Adapter<NewOrderAdapter.RecyclerViewHolder>() {
    var inflater: LayoutInflater
    var ll_1: LinearLayout? = null

    inner class RecyclerViewHolder(view: View?) : RecyclerView.ViewHolder(view!!)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val v = inflater.inflate(R.layout.list_new_order, parent, false)
        ll_1 = v.findViewById(R.id.ll_1)
        ll_1!!.setOnClickListener(View.OnClickListener { context.startActivity(Intent(context, DetailActivity::class.java)) })
        return RecyclerViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {}

    override fun getItemCount(): Int {
        return 2
    }

    init {
        inflater = LayoutInflater.from(context)
    }
}