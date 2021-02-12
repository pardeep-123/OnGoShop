package com.ongoshop.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ongoshop.R

class PastOrderAdapter(var context: Context) : RecyclerView.Adapter<PastOrderAdapter.RecyclerViewHolder>() {
    var inflater: LayoutInflater

    inner class RecyclerViewHolder(view: View?) : RecyclerView.ViewHolder(view!!)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val v = inflater.inflate(R.layout.list_past, parent, false)
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