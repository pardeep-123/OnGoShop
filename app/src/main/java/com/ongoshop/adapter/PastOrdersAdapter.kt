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
import com.ongoshop.activities.DetailActivity
import com.ongoshop.pojo.OrderListResponse
import com.ongoshop.pojo.PastOrderListResponse
import com.ongoshop.utils.others.CommonMethods

class PastOrdersAdapter(internal var context: Context, private var pastOrderList: ArrayList<PastOrderListResponse.Body?>?)
    : RecyclerView.Adapter<PastOrdersAdapter.NewOrderViewHolder>() {

    var inflater: LayoutInflater
    var ll_1: LinearLayout? = null

    inner class NewOrderViewHolder(view: View?) : RecyclerView.ViewHolder(view!!){
        var ll_1 = view!!.findViewById(R.id.ll_1) as LinearLayout
        var tvUsername = view!!.findViewById(R.id.tv_username) as TextView
        var tvDate = view!!.findViewById(R.id.tv_date) as TextView
        var tvTimings = view!!.findViewById(R.id.tv_timings) as TextView
        var tvOrderNumber = view!!.findViewById(R.id.tv_order_number) as TextView


        fun bindItems(orderList: PastOrderListResponse.Body?) {
            tvUsername.setText(orderList!!.username)
            tvOrderNumber.setText("Order Number: "+orderList!!.orderNo)
            tvDate.setText(CommonMethods.parseDateToddMMyyyy(orderList!!.createdAt, "dd-MM-yyyy hh:mm a"))
            tvTimings.setText(orderList!!.deliverySlot)
        }

        init {
            itemView.setOnClickListener {
                var intent= Intent(context, DetailActivity::class.java)
                intent.putExtra("from", "Past")
                intent.putExtra("orderId", pastOrderList!!.get(adapterPosition)!!.id.toString())
                intent.putExtra("username", pastOrderList!!.get(adapterPosition)!!.username.toString())
                intent.putExtra("orderNo", pastOrderList!!.get(adapterPosition)!!.orderNo.toString())
                intent.putExtra("deliverySlot", pastOrderList!!.get(adapterPosition)!!.deliverySlot.toString())
                intent.putExtra("createdAt", pastOrderList!!.get(adapterPosition)!!.createdAt.toString())
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewOrderViewHolder {
        return NewOrderViewHolder(
                LayoutInflater.from(context).inflate(R.layout.list_new_order, parent, false))
      
       /* val v = inflater.inflate(R.layout.list_new_order, parent, false)
        ll_1 = v.findViewById(R.id.ll_1)
        ll_1!!.setOnClickListener(View.OnClickListener { context.startActivity(Intent(context, DetailActivity::class.java)) })
        return NewOrderViewHolder(v)*/
    }

    override fun onBindViewHolder(holder: NewOrderViewHolder, position: Int) {
        holder.bindItems(pastOrderList!![position])
    }

    override fun getItemCount(): Int {
        return pastOrderList!!.size
    }

    init {
        inflater = LayoutInflater.from(context)
    }
}