package com.ongoshop.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.ongoshop.R

class DeliveryOptions3Adapter(var context: Context) : RecyclerView.Adapter<DeliveryOptions3Adapter.RecyclerViewHolder>() {
    var inflater: LayoutInflater
    var btnBasket: Button? = null

    inner class RecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var ivOn: ImageView
        var ivOff: ImageView
        var temp = 2
        var ll_chackbox: LinearLayout

        init {
            ll_chackbox = view.findViewById(R.id.ll_chackbox)
            ivOn = view.findViewById(R.id.ivOn)
            ivOff = view.findViewById(R.id.ivOff)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val v = inflater.inflate(R.layout.list_del_option3, parent, false)
        return RecyclerViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.ll_chackbox.setOnClickListener {
            if (holder.temp % 2 == 0) {
                holder.ivOff.visibility = View.GONE
                holder.ivOn.visibility = View.VISIBLE
            } else {
                holder.ivOn.visibility = View.GONE
                holder.ivOff.visibility = View.VISIBLE
            }
            holder.temp++
        }
    }

    override fun getItemCount(): Int {
        return 7
    }

    init {
        inflater = LayoutInflater.from(context)
    }
}