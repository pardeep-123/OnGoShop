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

    override fun getItemViewType(position: Int): Int {
        return position
    }
    override fun getItemCount(): Int {
        return vendorDeliveryChargeList.size
    }

    inner class DeliveryChargesHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Holds the TextView that will add each animal to

        val tvMinDistance = itemView.findViewById(R.id.tv_min_distance) as TextView
        val tvMaxDistance = itemView.findViewById(R.id.tv_max_distance) as TextView
        var etPrice = itemView.findViewById(R.id.et_delivery_price) as EditText
        val llCheckboxFree = itemView.findViewById(R.id.ll_chackbox) as LinearLayout
        val ivOn = itemView.findViewById(R.id.ivOn) as ImageView
        val ivOff = itemView.findViewById(R.id.ivOff) as ImageView


        fun bindItems(vendorDeliveryChargeList: VendorDeliveryCharge) {

            tvMinDistance.text = vendorDeliveryChargeList.minDistance.toString()
            tvMaxDistance.text = vendorDeliveryChargeList.maxDistance.toString() + context!!.getString(R.string.kms)
            etPrice.setText(vendorDeliveryChargeList.price.toString())

            etPrice.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable) {
                    if (s.isNotEmpty()) {
                        setPrice(etPrice.text.toString())
                    }
                }
            })

            ivOn.setOnClickListener {
                setFreeDelivery("on")
                etPrice.setText(vendorDeliveryChargeList.price.toString())
            }
            ivOff.setOnClickListener {
                setFreeDelivery("off")
                etPrice.setText("")
            }
            if (vendorDeliveryChargeList.freeDelivery==0)
            {
                ivOff.visibility = View.VISIBLE
                ivOn.visibility = View.GONE
                etPrice.setText(vendorDeliveryChargeList.price.toString())
            }
            else
            {
                ivOff.visibility = View.GONE
                ivOn.visibility = View.VISIBLE
                etPrice.isEnabled = false
                etPrice.setText("")

            }
        }

        fun setPrice(price: String) {
            strPrice = price
            vendorDeliveryChargeList[adapterPosition].price = strPrice.toInt()
            vendorDeliveryChargeList.set(adapterPosition, vendorDeliveryChargeList[adapterPosition])
            updateDeliveryChargesActivity.setUpdatedList(adapterPosition, vendorDeliveryChargeList)

        }

        fun setFreeDelivery(type: String) {
            if (type == "on") {
                ivOff.visibility = View.VISIBLE
                ivOn.visibility = View.GONE

                vendorDeliveryChargeList[adapterPosition].price = 0
                vendorDeliveryChargeList[adapterPosition].freeDelivery = 0
                updateDeliveryChargesActivity.setUpdatedList(adapterPosition, vendorDeliveryChargeList)
              //  etPrice.isClickable = true
                etPrice.isEnabled = true

              // etPrice.setKeyListener(etPrice.getTag() as KeyListener)

            } else {
                ivOff.visibility = View.GONE
                ivOn.visibility = View.VISIBLE
                vendorDeliveryChargeList[adapterPosition].freeDelivery = 1
                vendorDeliveryChargeList[adapterPosition].price = 0
//                if (etPrice.text.toString().isEmpty())
//                {
//                    vendorDeliveryChargeList[adapterPosition].price = 0
//                }else
//                {
//                   // vendorDeliveryChargeList[adapterPosition].price = etPrice.text.toString().toInt()
//                    vendorDeliveryChargeList[adapterPosition].price =0
//                }

                updateDeliveryChargesActivity.setUpdatedList(adapterPosition, vendorDeliveryChargeList)
               /* etPrice.setTag(etPrice.getKeyListener())
                etPrice.setKeyListener(null)*/
             //   etPrice.isClickable = false
                etPrice.isEnabled = false
            }
        }

        init {
            itemView.setOnClickListener {

            }
        }
    }
}

