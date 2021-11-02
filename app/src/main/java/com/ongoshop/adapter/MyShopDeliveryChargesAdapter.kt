package com.ongoshop.adapter

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.text.method.KeyListener
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.ongoshop.R
import com.ongoshop.activities.DeliveryChargesActivity
import com.ongoshop.activities.MyShopActivity
import com.ongoshop.pojo.VendorDeliveryCharge


class MyShopDeliveryChargesAdapter(
        val context: Context?,
        internal var vendorDeliveryChargeList: ArrayList<VendorDeliveryCharge>,
        internal var myShopActivity: MyShopActivity
) : RecyclerView.Adapter<MyShopDeliveryChargesAdapter.MyShopDeliveryChargesHolder>() {



    override fun onBindViewHolder(holder: MyShopDeliveryChargesHolder, position: Int) {
        holder.bindItems(vendorDeliveryChargeList[position])
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyShopDeliveryChargesHolder {
        return MyShopDeliveryChargesHolder(
                LayoutInflater.from(context).inflate(R.layout.my_shop_delivery_charges, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return vendorDeliveryChargeList.size
    }


    inner class MyShopDeliveryChargesHolder(view: View) : RecyclerView.ViewHolder(view) {

        val tvDistance = itemView.findViewById(R.id.tv_distance) as TextView
        val tvDeliveryCharges = itemView.findViewById(R.id.tv_delivery_charges) as TextView


        fun bindItems(vendorDeliveryChargeList: VendorDeliveryCharge) {
            tvDistance.text = vendorDeliveryChargeList.minDistance.toString() + "-" +
                    vendorDeliveryChargeList.maxDistance.toString() + " "+context!!.getString(R.string.kms)
            tvDeliveryCharges.text = vendorDeliveryChargeList.price.toString() +"$"

        }




        init {
            itemView.setOnClickListener {

            }
        }
    }
}

