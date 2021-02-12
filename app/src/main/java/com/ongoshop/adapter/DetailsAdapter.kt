package com.ongoshop.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.ongoshop.R
import com.ongoshop.activities.BarcodeScannerActivity

class DetailsAdapter(var context: Context) : RecyclerView.Adapter<DetailsAdapter.RecyclerViewHolder>() {
    var inflater: LayoutInflater
    var btnBasket: Button? = null

    inner class RecyclerViewHolder(view: View?) : RecyclerView.ViewHolder(view!!)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val v = inflater.inflate(R.layout.list_order_detail, parent, false)
        btnBasket = v.findViewById(R.id.btnBasket)
        btnBasket!!.setOnClickListener(View.OnClickListener {
            context.startActivity(Intent(context, BarcodeScannerActivity::class.java)) })
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