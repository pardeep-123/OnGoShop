package com.ongoshop.activities

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import com.ongoshop.R
import com.ongoshop.utils.helperclasses.image
import kotlinx.android.synthetic.main.activity_add_product.*

class AddProductActivity : image(), View.OnClickListener {
    var btnProceed: Button? = null
    var mContext: AddProductActivity? = null
    var ivOn: ImageView? = null
    var ivOff: ImageView? = null
    var ll_chackbox: LinearLayout? = null
    var temp = 2
    var ivImg: ImageView? = null
    var ivcamera: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)
        mContext = this

        ivImg = findViewById(R.id.ivImg)
        ivcamera = findViewById(R.id.ivcamera)
        ivOn = findViewById(R.id.ivOn)
        ivOff = findViewById(R.id.ivOff)
        ll_chackbox = findViewById(R.id.ll_chackbox)
        btnProceed = findViewById(R.id.btnProceed)

        ll_chackbox!!.setOnClickListener(mContext)
        ivcamera!!.setOnClickListener(mContext)
        btnProceed!!.setOnClickListener(mContext)
        ivBack.setOnClickListener(mContext)

    }

    override fun selectedImage(var1: Bitmap, var2: String) {
        ivImg!!.setImageBitmap(var1)
    }

    override fun onClick(v: View?) {
       when(v!!.id){
        R.id.ivBack -> {
            finish()
        }
        R.id.ivcamera -> {
            image("all")
        }
       R.id.ll_chackbox -> {
           if (temp % 2 == 0) {
               ivOff!!.setVisibility(View.GONE)
               ivOn!!.setVisibility(View.VISIBLE)
           } else {
               ivOn!!.setVisibility(View.GONE)
               ivOff!!.setVisibility(View.VISIBLE)
           }
           temp++
        }
        R.id.btnProceed -> {
            val i = Intent(mContext, HomeActivity::class.java)
            i.putExtra("type", "my")
            startActivity(i)
        }
    }
    }
}