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

class SearchBarcode1Activity : BaseActivity(), View.OnClickListener {
    var mContext: SearchBarcode1Activity? = null
    var btnEntry: Button? = null
    var ivBack: ImageView? = null

    override fun getContentId(): Int {
        return R.layout.activity_search_barcode1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this

        ivBack = findViewById(R.id.ivBack)
        btnEntry = findViewById(R.id.btnEntry)

        btnEntry!!.setOnClickListener(mContext)
        ivBack!!.setOnClickListener(mContext)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.ivBack -> {
                onLeftIconClick()
            }
            R.id.btnEntry -> {
                val i = Intent(mContext, AddProductActivity::class.java)
                startActivity(i)
            }
        }
    }

}