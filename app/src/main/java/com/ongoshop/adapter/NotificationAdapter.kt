package com.ongoshop.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ongoshop.R
import com.ongoshop.pojo.NotificationsListResponse
import com.ongoshop.pojo.PastOrderListResponse
import com.ongoshop.utils.others.CommonMethods

class NotificationAdapter(var context: Context, internal var notificationsList: ArrayList<NotificationsListResponse.Body?>)
    : RecyclerView.Adapter<NotificationAdapter.RecyclerViewHolder>() {
    var inflater: LayoutInflater
    var rl_list: ImageView? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val v = inflater.inflate(R.layout.list_noti, parent, false)
        return RecyclerViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.bindItems(notificationsList!![position])
    }

    override fun getItemCount(): Int {
        return notificationsList.size
    }

    init {
        inflater = LayoutInflater.from(context)
    }

    inner class RecyclerViewHolder(view: View?) : RecyclerView.ViewHolder(view!!){
        var tvMessage = view!!.findViewById(R.id.tv_message) as TextView
        var tvTime = view!!.findViewById(R.id.tv_time) as TextView

        fun bindItems(notificationsList: NotificationsListResponse.Body?) {
            tvMessage.setText(notificationsList!!.getMessage())

            var convertDate= CommonMethods.parseDateToddMMyyyy(notificationsList.getCreatedAt(), "dd-MM-yyyy hh:mm a")
            var timestamp= CommonMethods.time_to_timestamp(convertDate, "dd-MM-yyyy hh:mm a")
            Log.e("timestamp123", timestamp.toString())

            tvTime.text = CommonMethods.getNotificationTime(timestamp)
        }


    }


}