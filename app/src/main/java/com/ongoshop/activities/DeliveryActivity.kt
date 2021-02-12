package com.ongoshop.activities

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ongoshop.R
import com.ongoshop.adapter.DeliveryAdapter
import com.ongoshop.base.BaseActivity

class DeliveryActivity : BaseActivity() {
    var ivBack: ImageView? = null
    var mContext: DeliveryActivity? = null
    var btnFinish: Button? = null
    var recyclerview: RecyclerView? = null
    var deliveryAdapter: DeliveryAdapter? = null
    override fun getContentId(): Int {
        return R.layout.activity_delivery
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delivery)
        mContext = this
        ivBack = findViewById(R.id.ivBack)
        ivBack!!.setOnClickListener(View.OnClickListener { finish() })
        recyclerview = findViewById(R.id.recyclerview)

        deliveryAdapter = DeliveryAdapter(this)
        recyclerview!!.layoutManager = LinearLayoutManager(mContext)
        recyclerview!!.adapter = deliveryAdapter
    }
}