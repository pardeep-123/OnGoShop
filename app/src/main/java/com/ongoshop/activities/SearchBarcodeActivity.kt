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

class SearchBarcodeActivity : BaseActivity() {
    var mContext: SearchBarcodeActivity? = null
    var btnSearchBarcode: Button? = null
    var ivBack: ImageView? = null
    override fun getContentId(): Int {
        return R.layout.activity_search_barcode
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        ivBack = findViewById(R.id.ivBack)
        btnSearchBarcode = findViewById(R.id.btnSearchBarcode)
        ivBack!!.setOnClickListener {finish() }

        btnSearchBarcode!!.setOnClickListener{
            val i = Intent(mContext, ProductDetailActivity::class.java)
            startActivity(i)
        }
    }
}