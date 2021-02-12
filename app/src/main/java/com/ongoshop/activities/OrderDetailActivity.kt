package com.ongoshop.activities

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ongoshop.R
import com.ongoshop.adapter.OrderDetailAdapter

class OrderDetailActivity : AppCompatActivity() {
    var ivBack: ImageView? = null
    var mContext: Context? = null
    var btnFinish: Button? = null
    var dialog: Dialog? = null
    var recyclerview: RecyclerView? = null
    var orderDetailAdapter: OrderDetailAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_detail)
        mContext = this
        ivBack = findViewById(R.id.ivBack)
        ivBack!!.setOnClickListener(View.OnClickListener { finish() })
        btnFinish = findViewById(R.id.btnFinish)
        btnFinish!!.setOnClickListener(View.OnClickListener { showDailog() })
        recyclerview = findViewById(R.id.recyclerview)
        orderDetailAdapter = OrderDetailAdapter(mContext!!)
        recyclerview!!.setLayoutManager(LinearLayoutManager(mContext))
        recyclerview!!.setAdapter(orderDetailAdapter)
    }

    fun showDailog() {
        dialog = Dialog(mContext!!)
        dialog!!.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog!!.setContentView(R.layout.alert_detail)
        dialog!!.setCancelable(true)
        val btnOkk = dialog!!.findViewById<Button>(R.id.btnOkk)
        btnOkk.setOnClickListener {
            dialog!!.dismiss()
            detailDailog()
        }
        dialog!!.show()
    }

    fun detailDailog() {
        dialog = Dialog(mContext!!)
        dialog!!.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog!!.setContentView(R.layout.alert_detail2)
        dialog!!.setCancelable(true)
        val btnOk = dialog!!.findViewById<Button>(R.id.btnOk)
        btnOk.setOnClickListener {
            mContext!!.startActivity(Intent(mContext, HomeActivity::class.java))
            dialog!!.dismiss()
        }
        dialog!!.show()
    }
}