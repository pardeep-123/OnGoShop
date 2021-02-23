package com.ongoshop.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ongoshop.R
import com.ongoshop.activities.SubscriptionActivity
import com.ongoshop.clickListeners.SubcriptionClick
import com.ongoshop.pojo.SubscriptionListResponse


class SubscriptionAdapter(
        val context: Context?,
        internal var subscriptionList: ArrayList<SubscriptionListResponse.Body?>,
        internal var subcriptionClick: SubcriptionClick
) : RecyclerView.Adapter<SubscriptionAdapter.DeliveryChargesHolder>() {

    override fun onBindViewHolder(holder: DeliveryChargesHolder, position: Int) {
        holder.bindItems(subscriptionList[position])

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeliveryChargesHolder {
        return DeliveryChargesHolder(
                LayoutInflater.from(context).inflate(R.layout.subscriptions_list_card, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return subscriptionList.size
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)

    }

    inner class DeliveryChargesHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Holds the TextView that will add each animal to

        val tvSubscriptionPrice = itemView.findViewById(R.id.tv_subscription_price) as TextView
        val tvTotalProductEmployee = itemView.findViewById(R.id.tv_total_product_employee) as TextView

      /*  val ivOn = itemView.findViewById(R.id.ivOn) as ImageView
        val ivOff = itemView.findViewById(R.id.ivOff) as ImageView
*/

        fun bindItems(SubscriptionList: SubscriptionListResponse.Body?) {

            tvSubscriptionPrice.setText("$"+SubscriptionList!!.getAmount().toString()+ " Per month")
            tvTotalProductEmployee.setText(SubscriptionList!!.getItem().toString() +" items and "+
                    SubscriptionList!!.getEmployes().toString()+" Employees")


/*
            ivOn.setOnClickListener {
                setFreeDelivery("on")
            }
            ivOff.setOnClickListener {
                setFreeDelivery("off")

            }
*/

        }

        init {
            itemView.setOnClickListener {

            }
        }
    }
}

