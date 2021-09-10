package com.ongoshop.adapter

import android.app.TimePickerDialog
import android.content.Context

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout

import android.widget.TextView
import android.widget.Toast

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
    internal var deliveryOptionsActivity: DeliveryOptionsActivity,
    internal var deliveryOptionsClicklisetener: DeliveryOptionsClicklisetener,
    var openTime: String,
   var closeTime: String
) : RecyclerView.Adapter<DeliveryOptionsAdapter.DeliveryOptionsHolder>() {

    private var isNoDelivery: Int = 0
    private var startChooseTime = ""
    private var closeChooseTime = ""

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
                tvDay.text = "Sunday"
            }
            if (vendorDeliveryOptionList.day.equals("mon")){
                tvDay.text = "Monday"
            }
            if (vendorDeliveryOptionList.day.equals("tue")){
                tvDay.text = "Tuesday"
            }
            if (vendorDeliveryOptionList.day.equals("wed")){
                tvDay.text = "Wednesday"
            }
            if (vendorDeliveryOptionList.day.equals("thu")){
                tvDay.text = "Thursday"
            }
             if (vendorDeliveryOptionList.day.equals("fri")){
                 tvDay.text = "Friday"
            }
             if (vendorDeliveryOptionList.day.equals("sat")){
                 tvDay.text = "Saturday"
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
        /**
         * Function to get the current Date
         * @author Pardeep Sharma
         *  implemented on 07 Sep 2021
         */
        fun getCurrentDate(): String {
            val c = Calendar.getInstance().time
            println("Current time => $c")

            val df = SimpleDateFormat("yyyy-MM-dd")
            return df.format(c)
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
                    // get value of selected time
                    startChooseTime = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(cal.time)
                    val currentStartTime = getCurrentDate() + " " + startChooseTime
                    val shopStartTime = getCurrentDate() + " " + openTime
                    val dfDate = SimpleDateFormat("yyyy-MM-dd hh:mm a",Locale.getDefault())

                    //check date before or after the shop time
                    if (dfDate.parse(currentStartTime).before(dfDate.parse(shopStartTime))){
                        Toast.makeText(context,"Please Choose time After your Shop Open time",
                            Toast.LENGTH_LONG).show()
                    }else {
                        // else set the time if all good
                        vendorDeliveryOptionList[adapterPosition].deliveryTimeFrom =
                            SimpleDateFormat("hh:mm a", Locale.getDefault()).format(cal.time)
                        vendorDeliveryOptionList[adapterPosition].deliverystartmiliseconds =
                            (CommonMethods.time_to_timestamp(
                                SimpleDateFormat("hh:mm a", Locale.getDefault()).format(cal.time),
                                "hh:mm a"
                            ))
                        tvFromTime.text = vendorDeliveryOptionList[adapterPosition].deliveryTimeFrom

                        // vendorDeliveryOptionList.set(adapterPosition, vendorDeliveryOptionList[adapterPosition])
                        deliveryOptionsActivity.setUpdatedList(
                            adapterPosition,
                            vendorDeliveryOptionList
                        )
                    }

                } else {
                    vendorDeliveryOptionList[adapterPosition].delivertendmiliseconds = (CommonMethods.time_to_timestamp(SimpleDateFormat("hh:mm a").format(cal.time), "hh:mm a"))

                    if (vendorDeliveryOptionList[adapterPosition].delivertendmiliseconds >= vendorDeliveryOptionList[adapterPosition].deliverystartmiliseconds) {

                        closeChooseTime = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(cal.time)
                        val currentTime = getCurrentDate() +" "+ closeChooseTime
                        val shopTime = getCurrentDate() +" "+ closeTime
                        val dfDate = SimpleDateFormat("yyyy-MM-dd hh:mm a", Locale.getDefault())
                        if (dfDate.parse(currentTime).after(dfDate.parse(shopTime))){
                            Toast.makeText(context,"Please Choose time before your Shop close time",Toast.LENGTH_LONG).show()
                        }else {


                            vendorDeliveryOptionList[adapterPosition].deliveryTimeTo =
                                SimpleDateFormat("hh:mm a").format(cal.time)

                            tvToTime.text = vendorDeliveryOptionList[adapterPosition].deliveryTimeTo
                            // vendorDeliveryOptionList.set(adapterPosition, vendorDeliveryOptionList[adapterPosition])
                            deliveryOptionsActivity.setUpdatedList(
                                adapterPosition,
                                vendorDeliveryOptionList
                            )

                            // Log.e("endTimeTimestamp", closeTimeTimestamp.toString())
                        }
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

