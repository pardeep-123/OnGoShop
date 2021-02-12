package com.ongoshop.activities

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import com.ongoshop.R
import com.ongoshop.utils.helperclasses.image

class BarcodeScannerActivity : image(), View.OnClickListener {
    var mContext: BarcodeScannerActivity? = null
    var ivBack: ImageView? = null
    var ivImg: ImageView? = null
    var btnSearch: Button? = null
    var btnItem: Button? = null
    var llBarcode: RelativeLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_barcode_scanner)
        mContext = this
        ivBack = findViewById(R.id.ivBack)
        ivBack!!.setOnClickListener(mContext)
        btnItem = findViewById(R.id.btnItem)
        ivImg = findViewById(R.id.ivImg)
        llBarcode = findViewById(R.id.llBarcode)
        llBarcode!!.setOnClickListener(mContext)

        btnSearch = findViewById(R.id.btnSearch)
        btnSearch!!.setOnClickListener(mContext)
        btnItem!!.setOnClickListener(mContext)
    }

    override fun selectedImage(var1: Bitmap, var2: String) {}
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.ivBack -> {
                finish()
            }
            R.id.btnSearch -> {
                val i = Intent(mContext, SearchBarcodeActivity::class.java)
                startActivity(i)
            }
            R.id.btnItem -> {
                val i = Intent(mContext, SearchBarcode1Activity::class.java)
                startActivity(i)
            }
            R.id.llBarcode -> {
                image("camera")
            }
        }
    }

}