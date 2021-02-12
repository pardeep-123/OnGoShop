package com.ongoshop.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.ongoshop.R

class ManagerManagementAdapter(var context: Context) : RecyclerView.Adapter<ManagerManagementAdapter.RecyclerViewHolder>() {
    var inflater: LayoutInflater
    var rl_list: ImageView? = null

    inner class RecyclerViewHolder(view: View?) : RecyclerView.ViewHolder(view!!)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val v = inflater.inflate(R.layout.list_managment, parent, false)
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