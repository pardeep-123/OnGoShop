package com.ongoshop.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ongoshop.R
import com.ongoshop.adapter.NotificationAdapter
import com.ongoshop.base.BaseActivity

class NotificationActivity : BaseActivity(), View.OnClickListener {
    var ivBack: ImageView? = null
    var mContext: NotificationActivity? = null
    var recyclerview: RecyclerView? = null
    var notificationAdapter: NotificationAdapter? = null
    override fun getContentId(): Int {
        return R.layout.activity_notification
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this

        ivBack = findViewById(R.id.ivBack)
        recyclerview = findViewById(R.id.recyclerview)

        ivBack!!.setOnClickListener(mContext)

        notificationAdapter = NotificationAdapter(mContext!!)
        recyclerview!!.setLayoutManager(LinearLayoutManager(mContext))
        recyclerview!!.setAdapter(notificationAdapter)
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.ivBack ->{
                onLeftIconClick()
            }
        }
    }
}