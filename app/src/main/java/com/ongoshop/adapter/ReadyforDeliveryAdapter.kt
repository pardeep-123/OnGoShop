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
import com.ongoshop.pojo.DeliveryAndPickupOrderListResponse
import com.ongoshop.utils.others.CommonMethods

class ReadyforDeliveryAdapter(var context: Context, private var deliveryOrderList: ArrayList<DeliveryAndPickupOrderListResponse.Body>?)
    : RecyclerView.Adapter<ReadyforDeliveryAdapter.RecyclerViewHolder>() {
    var inflater: LayoutInflater
    var ll_1: LinearLayout? = null

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
                var intent= Intent(context, OrderDeliveryAddressActivity::class.java)
                intent.putExtra("orderId", deliveryOrderList!!.get(adapterPosition)!!.id.toString())
                intent.putExtra("username", deliveryOrderList!!.get(adapterPosition)!!.username.toString())
                intent.putExtra("orderNo", deliveryOrderList!!.get(adapterPosition)!!.orderNo.toString())
                intent.putExtra("userAddress", deliveryOrderList!!.get(adapterPosition)!!.userAddress.toString())
                intent.putExtra("deliverySlot", deliveryOrderList!!.get(adapterPosition)!!.deliverySlot.toString())
                intent.putExtra("phone", deliveryOrderList!!.get(adapterPosition)!!.phone.toString())
                intent.putExtra("createdAt", deliveryOrderList!!.get(adapterPosition)!!.createdAt.toString())
                context.startActivity(intent)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        return RecyclerViewHolder(
                LayoutInflater.from(context).inflate(R.layout.list_pickup, parent, false))
        
       /* val v = inflater.inflate(R.layout.list_pickup, parent, false)
        ll_1 = v.findViewById(R.id.ll_1)
        ll_1!!.setOnClickListener(View.OnClickListener { context.startActivity(Intent(context, DeliveryActivity2::class.java)) })
        return RecyclerViewHolder(v)*/
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.bindItems(deliveryOrderList!![position])
    }

    override fun getItemCount(): Int {
        return deliveryOrderList!!.size
    }

    init {
        inflater = LayoutInflater.from(context)
    }
}