package com.ongoshop.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import com.ongoshop.R
import com.ongoshop.base.BaseActivity

class ManageCategoryActivity : BaseActivity() {

    var llIndividual: LinearLayout? = null
    var llProduct: LinearLayout? = null
    var mContext: ManageCategoryActivity? = null

    override fun getContentId(): Int {
        return R.layout.activity_manage_category
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        val ivBack = findViewById<ImageView>(R.id.ivBack)
        ivBack.setOnClickListener { finish() }
        llProduct = findViewById(R.id.llProduct)
        llIndividual = findViewById(R.id.llIndividual)

        llProduct!!.setOnClickListener {
            val i = Intent(mContext, AddCategoryActivity::class.java)
            startActivity(i)
        }

        llIndividual!!.setOnClickListener {
            val i = Intent(mContext, BarcodeScannerActivity::class.java)
            startActivity(i)
        }
    }
}