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
    private var openTimeTimestamp: Long = 0
    private var closeTimeTimestamp: Long = 0
    private var closeTime: String = ""
    private var startTime: String = ""
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

            tvDay.setText(vendorDeliveryOptionList.day)
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
                    startTime = SimpleDateFormat("hh:mm a").format(cal.time)
                    openTimeTimestamp = (CommonMethods.time_to_timestamp(tvFromTime.text.toString(), "hh:mm a"))
                    tvFromTime.text = startTime
                    Log.e("startTimeTimestamp", openTimeTimestamp.toString())
                    vendorDeliveryOptionList[adapterPosition].deliveryTimeFrom= startTime
                    vendorDeliveryOptionList.set(adapterPosition, vendorDeliveryOptionList[adapterPosition])
                    deliveryOptionsActivity.setUpdatedList(adapterPosition, vendorDeliveryOptionList)


                } else {
                    closeTimeTimestamp = (CommonMethods.time_to_timestamp(SimpleDateFormat("hh:mm a").format(cal.time), "hh:mm a"))
                    if (closeTimeTimestamp >= openTimeTimestamp) {
                        closeTime = SimpleDateFormat("hh:mm a").format(cal.time)
                        tvToTime.text = closeTime
                        vendorDeliveryOptionList[adapterPosition].deliveryTimeTo= closeTime
                        vendorDeliveryOptionList.set(adapterPosition, vendorDeliveryOptionList[adapterPosition])
                        deliveryOptionsActivity.setUpdatedList(adapterPosition, vendorDeliveryOptionList)

                        Log.e("endTimeTimestamp", closeTimeTimestamp.toString())

                    } else {
                        //it's before current'
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

