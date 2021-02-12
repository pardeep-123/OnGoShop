package com.ongoshop.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import com.ongoshop.R
import com.ongoshop.base.BaseActivity

class AddCardActivity : BaseActivity(), View.OnClickListener {
    lateinit var mContext: AddCardActivity
    lateinit var ivBack: ImageView
    lateinit var ivOn: ImageView
    lateinit var ivOff: ImageView
    lateinit var ivImg: ImageView
    lateinit var ll_chackbox: LinearLayout
    lateinit var btnSave: Button
    var temp = 2

    override fun getContentId(): Int {
        return R.layout.activity_add_card
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        ivBack = findViewById(R.id.ivBack)
        ivOn = findViewById(R.id.ivOn)
        ivOff = findViewById(R.id.ivOff)
        ivBack.setOnClickListener(View.OnClickListener { finish() })
        btnSave = findViewById(R.id.btnSave)
        btnSave.setOnClickListener(View.OnClickListener {

        })
        ll_chackbox = findViewById(R.id.ll_chackbox)

    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.ivBack -> {
                onLeftIconClick()
            }
            R.id.btnSave -> {
                val intent = Intent(mContext, PaymentActivity::class.java)
                startActivity(intent)
            }
            R.id.ll_chackbox -> {
                if (temp % 2 == 0) {
                    ivOff.setVisibility(View.GONE)
                    ivOn.setVisibility(View.VISIBLE)
                } else {
                    ivOn.setVisibility(View.GONE)
                    ivOff.setVisibility(View.VISIBLE)
                }
                temp++

            }
        }
    }
}