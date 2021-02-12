package com.ongoshop.activities

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import com.ongoshop.R
import com.ongoshop.utils.helperclasses.image

class AddEmployeeActivity : image(), View.OnClickListener {
    var btnSubmit: Button? = null
    var mContext: AddEmployeeActivity? = null
    var ivBack: ImageView? = null
    var ivImg: ImageView? = null
    var ivAdd: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_employee)
        mContext = this
        btnSubmit = findViewById(R.id.btnSubmit)
        ivBack = findViewById(R.id.ivBack)
        ivImg = findViewById(R.id.iv_profile)
        ivAdd = findViewById(R.id.ivAdd)

        ivAdd!!.setOnClickListener(mContext)
        ivBack!!.setOnClickListener(mContext)
        btnSubmit!!.setOnClickListener(mContext)
    }

    override fun selectedImage(var1: Bitmap, var2: String) {
        ivImg!!.setImageBitmap(var1)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.ivBack -> {
                finish()
            }
          R.id.ivAdd -> {
              image("all")
            }
            R.id.btnSubmit -> {
                val i = Intent(mContext, ManagerManagementActivity::class.java)
                startActivity(i)
            }
        }
    }
}