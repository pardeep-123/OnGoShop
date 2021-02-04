package com.ongoshop.adapter

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.text.method.KeyListener
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ongoshop.R
import com.ongoshop.activities.UpdateDeliveryChargesActivity
import com.ongoshop.pojo.VendorDeliveryCharge


class UpdateDeliveryChargesAdapter(
        val context: Context?,
        internal var vendorDeliveryChargeList: ArrayList<VendorDeliveryCharge>,
        internal var updateDeliveryChargesActivity: UpdateDeliveryChargesActivity
) : RecyclerView.Adapter<UpdateDeliveryChargesAdapter.DeliveryChargesHolder>() {

    private var strPrice: String = ""
    private var isFreeDelivery: Int = 0

    override fun onBindViewHolder(holder: DeliveryChargesHolder, position: Int) {
        holder.bindItems(vendorDeliveryChargeList[position])

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeliveryChargesHolder {
        return DeliveryChargesHolder(
                LayoutInflater.from(context).inflate(R.layout.list_del_option3, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return vendorDeliveryChargeList.size
    }

    inner class DeliveryChargesHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Holds the TextView that will add each animal to

        val tvMinDistance = itemView.findViewById(R.id.tv_min_distance) as TextView
        val tvMaxDistance = itemView.findViewById(R.id.tv_max_distance) as TextView
        val etPrice = itemView.findViewById(R.id.et_price) as EditText
        val llCheckboxFree = itemView.findViewById(R.id.ll_chackbox) as LinearLayout
        val ivOn = itemView.findViewById(R.id.ivOn) as ImageView
        val ivOff = itemView.findViewById(R.id.ivOff) as ImageView


        fun bindItems(vendorDeliveryChargeList: VendorDeliveryCharge) {

            tvMinDistance.setText(vendorDeliveryChargeList.minDistance.toString())
            tvMaxDistance.setText(vendorDeliveryChargeList.maxDistance.toString())

            etPrice.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable) {
                    if (s.length >= 1) {
                        setPrice(etPrice.text.toString())
                    } else {

                    }
                }
            })

            ivOn.setOnClickListener {
                setFreeDelivery("on")
            }
            ivOff.setOnClickListener {
                setFreeDelivery("off")

            }

        }

        fun setPrice(price: String) {
            strPrice = price
            vendorDeliveryChargeList[adapterPosition].price = strPrice
            vendorDeliveryChargeList.set(adapterPosition, vendorDeliveryChargeList[adapterPosition])
            updateDeliveryChargesActivity.setUpdatedList(adapterPosition, vendorDeliveryChargeList)

        }

        fun setFreeDelivery(type: String) {
            if (type.equals("on")) {
                ivOff.visibility = View.VISIBLE
                ivOn.visibility = View.GONE
                isFreeDelivery = 0
                vendorDeliveryChargeList[adapterPosition].freeDelivery = isFreeDelivery
                vendorDeliveryChargeList.set(adapterPosition, vendorDeliveryChargeList[adapterPosition])
                updateDeliveryChargesActivity.setUpdatedList(adapterPosition, vendorDeliveryChargeList)

                etPrice.setKeyListener(etPrice.getTag() as KeyListener)
            } else {
                ivOff.visibility = View.GONE
                ivOn.visibility = View.VISIBLE
                isFreeDelivery = 1
                vendorDeliveryChargeList[adapterPosition].freeDelivery = isFreeDelivery
                vendorDeliveryChargeList.set(adapterPosition, vendorDeliveryChargeList[adapterPosition])
                updateDeliveryChargesActivity.setUpdatedList(adapterPosition, vendorDeliveryChargeList)
                etPrice.setTag(etPrice.getKeyListener())
                etPrice.setKeyListener(null)
                etPrice.setText("00")
            }
        }


        init {
            itemView.setOnClickListener {

            }
        }
    }
}

