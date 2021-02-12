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
import com.ongoshop.adapter.NewOrderAdapter
import com.ongoshop.base.BaseActivity

class NewOrdersActivity : BaseActivity(), View.OnClickListener {

    var ivBack: ImageView? = null
    var ivNoti: ImageView? = null
    var mContext: NewOrdersActivity? = null
    var recyclerview: RecyclerView? = null
    var newOrderAdapter: NewOrderAdapter? = null

    override fun getContentId(): Int {
        return R.layout.activity_new_orders
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mContext = this
        ivBack = findViewById(R.id.ivBack)
        ivNoti = findViewById(R.id.ivNoti)
        recyclerview = findViewById(R.id.recyclerview)

        ivBack!!.setOnClickListener(mContext)
        ivNoti!!.setOnClickListener(mContext)

        newOrderAdapter = NewOrderAdapter(mContext!!)
        recyclerview!!.setLayoutManager(LinearLayoutManager(mContext))
        recyclerview!!.adapter = newOrderAdapter
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.ivBack ->{
                onLeftIconClick()
            }
         R.id.ivNoti ->{
             val i = Intent(mContext, NotificationActivity::class.java)
             startActivity(i)
            }
        }
    }
}