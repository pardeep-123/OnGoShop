package com.ongoshop.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.ongoshop.R
import com.ongoshop.base.BaseActivity

class SubscriptionActivity : BaseActivity() {
    var mContext: SubscriptionActivity? = null
    var btnSubscription: Button? = null
    var ivBack: ImageView? = null

    override fun getContentId(): Int {
       return R.layout.activity_subscription
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
         ivBack = findViewById<ImageView>(R.id.ivBack)
        btnSubscription = findViewById(R.id.btnSubscription)

        ivBack!!.setOnClickListener { onLeftIconClick() }

        btnSubscription!!.setOnClickListener{
            val intent = Intent(mContext, PaymentActivity::class.java)
            startActivity(intent)
        }
    }
}