package com.ongoshop.adapter

import android.app.TimePickerDialog
import android.content.Context
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout

import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.ongoshop.R

import com.ongoshop.activities.DeliveryOptionsActivity
import com.ongoshop.pojo.VendorDeliveryOption
import com.ongoshop.utils.helperclasses.DeliveryOptionsClicklisetener
import com.ongoshop.utils.others.CommonMethods
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class DeliveryOptionsAdapter(
        val context: Context?,
        internal var vendorDeliveryOptionList: ArrayList<VendorDeliveryOption>,
        internal var deliveryOptionsActivity: DeliveryOptionsActivity, internal var deliveryOptionsClicklisetener: DeliveryOptionsClicklisetener
) : RecyclerView.Adapter<DeliveryOptionsAdapter.DeliveryOptionsHolder>() {

    private var isNoDelivery: Int = 0

    override fun onBindViewHolder(holder: DeliveryOptionsHolder, position: Int) {
        holder.bindItems(vendorDeliveryOptionList[position])

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeliveryOptionsHolder {
        return DeliveryOptionsHolder(
                LayoutInflater.from(context).inflate(R.layout.list_del_option1, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return vendorDeliveryOptionList.size
    }

    inner class DeliveryOptionsHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Holds the TextView that will add each animal to

        val tvDay = itemView.findViewById(R.id.tv_day) as TextView
        val llDeliveryTimings = itemView.findViewById(R.id.ll_delivery_timings) as LinearLayout
        val tvFromTime = itemView.findViewById(R.id.tv_from_time) as TextView
        val tvToTime = itemView.findViewById(R.id.tv_to_time) as TextView
        val ivOn = itemView.findViewById(R.id.ivOn) as ImageView
        val ivOff = itemView.findViewById(R.id.ivOff) as ImageView


        fun bindItems(vendorDeliveryOptionList: VendorDeliveryOption) {

            if (vendorDeliveryOptionList.day.equals("sun")){
                tvDay.setText("Sunday")
            }
            if (vendorDeliveryOptionList.day.equals("mon")){
                tvDay.setText("Monday")
            }
            if (vendorDeliveryOptionList.day.equals("tue")){
                tvDay.setText("Tuesday")
            }
            if (vendorDeliveryOptionList.day.equals("wed")){
                tvDay.setText("Wednesday")
            }
            if (vendorDeliveryOptionList.day.equals("thu")){
                tvDay.setText("Thursday")
            }
             if (vendorDeliveryOptionList.day.equals("fri")){
                tvDay.setText("Friday")
            }
             if (vendorDeliveryOptionList.day.equals("sat")){
                tvDay.setText("Saturday")
            }

            ivOn.setOnClickListener {
                 setNoDelivery("on")
            }
            ivOff.setOnClickListener {
                setNoDelivery("off")

            }
            tvFromTime.setOnClickListener {
                timee("open")
            }
            tvToTime.setOnClickListener {
                timee("close")
            }

        }

        fun setNoDelivery(type: String){
            if (type.equals("on")){
                ivOff.visibility = View.VISIBLE
                ivOn.visibility = View.GONE
                isNoDelivery=0
                vendorDeliveryOptionList[adapterPosition].noDelivery= isNoDelivery
                vendorDeliveryOptionList.set(adapterPosition, vendorDeliveryOptionList[adapterPosition])
                deliveryOptionsActivity.setUpdatedList(adapterPosition, vendorDeliveryOptionList)
                llDeliveryTimings.visibility= View.VISIBLE

            }else {
                ivOff.visibility = View.GONE
                ivOn.visibility = View.VISIBLE
                isNoDelivery=1
                vendorDeliveryOptionList[adapterPosition].noDelivery= isNoDelivery
                vendorDeliveryOptionList.set(adapterPosition, vendorDeliveryOptionList[adapterPosition])
                deliveryOptionsActivity.setUpdatedList(adapterPosition, vendorDeliveryOptionList)
                llDeliveryTimings.visibility= View.GONE
            }
        }

        fun timee(type: String) {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)

                //it's after current
                if (type.equals("open")) {
                    vendorDeliveryOptionList[adapterPosition].deliveryTimeFrom = SimpleDateFormat("hh:mm a").format(cal.time)
                    vendorDeliveryOptionList[adapterPosition].deliverystartmiliseconds = (CommonMethods.time_to_timestamp(SimpleDateFormat("hh:mm a").format(cal.time), "hh:mm a"))
                    tvFromTime.text = vendorDeliveryOptionList[adapterPosition].deliveryTimeFrom

                   // vendorDeliveryOptionList.set(adapterPosition, vendorDeliveryOptionList[adapterPosition])
                    deliveryOptionsActivity.setUpdatedList(adapterPosition, vendorDeliveryOptionList)


                } else {
                    vendorDeliveryOptionList[adapterPosition].delivertendmiliseconds = (CommonMethods.time_to_timestamp(SimpleDateFormat("hh:mm a").format(cal.time), "hh:mm a"))

                    if (vendorDeliveryOptionList[adapterPosition].delivertendmiliseconds >= vendorDeliveryOptionList[adapterPosition].deliverystartmiliseconds) {

                        vendorDeliveryOptionList[adapterPosition].deliveryTimeTo =SimpleDateFormat("hh:mm a").format(cal.time)

                        tvToTime.text = vendorDeliveryOptionList[adapterPosition].deliveryTimeTo
                       // vendorDeliveryOptionList.set(adapterPosition, vendorDeliveryOptionList[adapterPosition])
                        deliveryOptionsActivity.setUpdatedList(adapterPosition, vendorDeliveryOptionList)

                       // Log.e("endTimeTimestamp", closeTimeTimestamp.toString())

                    } else {
                        //it's before current'
                        tvToTime.text = ""
                        vendorDeliveryOptionList[adapterPosition].deliveryTimeTo = ""
                        CommonMethods.AlertErrorMessage(deliveryOptionsActivity, "Invalid Time")
                    }

                }

            }
            TimePickerDialog(context, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }



        init {
            itemView.setOnClickListener {

            }
        }
    }
}

