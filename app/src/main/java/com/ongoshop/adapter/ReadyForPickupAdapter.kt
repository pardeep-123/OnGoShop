package com.ongoshop.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ongoshop.R
import com.ongoshop.activities.OrderDeliveryAddressActivity
import com.ongoshop.activities.OrderDetailActivity
import com.ongoshop.pojo.DeliveryAndPickupOrderListResponse
import com.ongoshop.utils.others.CommonMethods

class ReadyForPickupAdapter(var context: Context, internal var pickupOrderList: ArrayList<DeliveryAndPickupOrderListResponse.Body>)
    : RecyclerView.Adapter<ReadyForPickupAdapter.RecyclerViewHolder>() {
    var inflater: LayoutInflater

    inner class RecyclerViewHolder(view: View?) : RecyclerView.ViewHolder(view!!){
        var ll_1 = view!!.findViewById(R.id.ll_1) as LinearLayout
        var tvUsername = view!!.findViewById(R.id.tv_username) as TextView
        var tvDate = view!!.findViewById(R.id.tv_date) as TextView
        /*  var tvTimings = view!!.findViewById(R.id.tv_timings) as TextView*/
        var tvOrderNumber = view!!.findViewById(R.id.tv_order_number) as TextView


        fun bindItems(orderList: DeliveryAndPickupOrderListResponse.Body?) {
            tvUsername.setText(orderList!!.username)
            tvOrderNumber.setText("Order Number: "+orderList!!.orderNo)
            tvDate.setText(CommonMethods.parseDateToddMMyyyy(orderList!!.createdAt, "dd-MM-yyyy hh:mm a"))
            //  tvTimings.setText(orderList!!.deliverySlot)
        }

        init {
            itemView.setOnClickListener {
/*
                var intent= Intent(context, OrderDetailActivity::class.java)
                //   intent.putExtra("orderId", pickupOrderList!!.get(adapterPosition)!!.id.toString())
                intent.putExtra("username", pickupOrderList!!.get(adapterPosition)!!.username.toString())
                intent.putExtra("orderNo", pickupOrderList!!.get(adapterPosition)!!.orderNo.toString())
                intent.putExtra("userAddress", pickupOrderList!!.get(adapterPosition)!!.userAddress.toString())
                intent.putExtra("deliverySlot", pickupOrderList!!.get(adapterPosition)!!.deliverySlot.toString())
                intent.putExtra("phone", pickupOrderList!!.get(adapterPosition)!!.phone.toString())
                intent.putExtra("createdAt", pickupOrderList!!.get(adapterPosition)!!.createdAt.toString())
                context.startActivity(intent)
*/
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        return RecyclerViewHolder(
                LayoutInflater.from(context).inflate(R.layout.list_pickup, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.bindItems(pickupOrderList!![position])
    }
    override fun getItemCount(): Int {
        return pickupOrderList.size
    }

    init {
        inflater = LayoutInflater.from(context)
    }
}