package com.ongoshop.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ongoshop.R
import com.ongoshop.pojo.OrderItemsListResponse

class OrderDetailAdapter(var context: Context,  private var orderItemsList: ArrayList<OrderItemsListResponse.Body?>) : RecyclerView.Adapter<OrderDetailAdapter.RecyclerViewHolder>() {
    var inflater: LayoutInflater
    var btnBasket: Button? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val v = inflater.inflate(R.layout.list_order_detail, parent, false)
        return RecyclerViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.bindItems(orderItemsList[position])
    }
    override fun getItemCount(): Int {
        return orderItemsList.size
    }

    init {
        inflater = LayoutInflater.from(context)
    }

    inner class RecyclerViewHolder(view: View?) : RecyclerView.ViewHolder(view!!){
        val ivProduct = itemView.findViewById(R.id.iv_product_image) as ImageView
        val tvProductName = itemView.findViewById(R.id.tv_product_name) as TextView
        val tvProductWeight = itemView.findViewById(R.id.tv_product_weight) as TextView
        val tvProductDesc = itemView.findViewById(R.id.tv_product_desc) as TextView
        val tvQty = itemView.findViewById(R.id.tv_qty) as TextView
        //  val tvProductPrice = itemView.findViewById(R.id.tv_product_price) as TextView

        fun bindItems(orderItemsListing: OrderItemsListResponse.Body?) {
            tvProductName.text = orderItemsListing!!.product!!.name
            tvQty.text = context.getString(R.string.qty)+orderItemsListing!!.qty.toString()

            if (orderItemsListing.product!!.weightUnit!!.equals(0)){
                tvProductWeight.text = orderItemsListing!!.product!!.weight+" "+ context.getString(R.string.kilograms)
            }else{
                tvProductWeight.text = orderItemsListing!!.product!!.weight+" "+ context.getString(R.string.pounds)
            }

            tvProductDesc.text = orderItemsListing!!.product!!.description
            //  tvProductPrice.setText("$"+orderItemsListing!!.netAmount)
            Glide.with(context).load(orderItemsListing.product!!.image).error(R.mipmap.no_image_placeholder).into(ivProduct)



        }
    }

}