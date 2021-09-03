package com.ongoshop.adapter

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.ongoshop.R
import com.ongoshop.activities.DeliveryChargesActivity
import com.ongoshop.pojo.VendorDeliveryCharge


class DeliveryChargesAdapter(
        val context: Context?,
        internal var vendorDeliveryChargeList: ArrayList<VendorDeliveryCharge>,
        internal var deliveryChargesActivity: DeliveryChargesActivity
) : RecyclerView.Adapter<DeliveryChargesAdapter.DeliveryChargesHolder>() {



    override fun onBindViewHolder(holder: DeliveryChargesHolder, position: Int) {



        holder.tvMinDistance.setText(vendorDeliveryChargeList[position].minDistance.toString())
        holder.tvMaxDistance.setText("${vendorDeliveryChargeList[position].maxDistance.toString()} Km")
        holder.myCustomEditTextListener.updatePosition(position)
        if (vendorDeliveryChargeList[position].price==0)
        holder.etPrice.setText("")
        else
          holder.etPrice.setText(vendorDeliveryChargeList[position].price.toString())



        holder.ivOn.setOnClickListener {
            setFreeDelivery(holder.ivOff,holder.ivOn,position,"on")
        }
        holder.ivOff.setOnClickListener {
            setFreeDelivery(holder.ivOff,holder.ivOn,position,"off")

        }


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeliveryChargesHolder {
        return DeliveryChargesHolder(
                LayoutInflater.from(context).inflate(R.layout.list_del_option3, parent, false),
            MyCustomEditTextListener()
        )
    }

    override fun getItemCount(): Int {
        return vendorDeliveryChargeList.size
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)

    }

    fun getvendorchargeslist():ArrayList<VendorDeliveryCharge>{
        return vendorDeliveryChargeList
    }
    fun setFreeDelivery(ivOff:ImageView,ivOn:ImageView,position: Int,type: String) {
        if (type.equals("on")) {
            ivOff.visibility = View.VISIBLE
            ivOn.visibility = View.GONE

            vendorDeliveryChargeList[position].price = 0
            vendorDeliveryChargeList[position].freeDelivery = 0
           // deliveryChargesActivity.setUpdatedList(position, vendorDeliveryChargeList)

           // etPrice.setKeyListener(etPrice.getTag() as KeyListener)
        } else {
            ivOff.visibility = View.GONE
            ivOn.visibility = View.VISIBLE
            vendorDeliveryChargeList[position].freeDelivery = 1

           /* if (etPrice.text.toString().isEmpty())
            {
                vendorDeliveryChargeList[adapterPosition].price = 0
            }else
            {
                vendorDeliveryChargeList[adapterPosition].price = etPrice.text.toString().toInt()
            }

            deliveryChargesActivity.setUpdatedList(adapterPosition, vendorDeliveryChargeList)
            etPrice.setTag(etPrice.getKeyListener())
            etPrice.setKeyListener(null)*/

        }
    }
    inner class DeliveryChargesHolder(view: View,myCustomEditTextListener: MyCustomEditTextListener) : RecyclerView.ViewHolder(view) {
        // Holds the TextView that will add each animal to

        val tvMinDistance = itemView.findViewById(R.id.tv_min_distance) as TextView
        val tvMaxDistance = itemView.findViewById(R.id.tv_max_distance) as TextView
        val etPrice = itemView.findViewById(R.id.et_delivery_price) as EditText
        val llCheckboxFree = itemView.findViewById(R.id.ll_chackbox) as LinearLayout
        val ivOn = itemView.findViewById(R.id.ivOn) as ImageView
        val ivOff = itemView.findViewById(R.id.ivOff) as ImageView
        var myCustomEditTextListener:MyCustomEditTextListener

        init {
            this.myCustomEditTextListener =myCustomEditTextListener
            etPrice.addTextChangedListener(myCustomEditTextListener)
        }
        }
    inner class MyCustomEditTextListener : TextWatcher {
        private var position = 0
        fun updatePosition(position: Int) {
            this.position = position
        }

        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i2: Int, i3: Int) {
            // no op
        }

        override fun onTextChanged(charSequence: CharSequence, i: Int, i2: Int, i3: Int) {
            if (charSequence.isNotEmpty())
            vendorDeliveryChargeList[position].price = charSequence.toString().toInt()
        }

        override fun afterTextChanged(editable: Editable) {
            // no op
        }
    }

    }


