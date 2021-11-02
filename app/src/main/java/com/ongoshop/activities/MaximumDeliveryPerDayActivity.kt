package com.ongoshop.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.ongoshop.R
import com.ongoshop.base.BaseActivity
import com.ongoshop.pojo.VendorDeliveryCharge
import com.ongoshop.utils.others.CommonMethods
import java.util.*

class MaximumDeliveryPerDayActivity : BaseActivity() {
    var btnConfirm: Button? = null
    private var mContext: MaximumDeliveryPerDayActivity? = null
    private var vendorDeliveryChargesList: ArrayList<VendorDeliveryCharge?>? = ArrayList<VendorDeliveryCharge?>()
    private var et_max_number: EditText? = null

    override fun getContentId(): Int {
        return R.layout.activity_max_delivery_perday
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        btnConfirm = findViewById(R.id.btnConfirm)
        val ivBack = findViewById<ImageView>(R.id.ivBack)
        et_max_number = findViewById(R.id.et_max_number)
        if (intent != null) {
            vendorDeliveryChargesList = intent.getParcelableArrayListExtra("vendorDeliveryCharges")
        }
        ivBack.setOnClickListener { finish() }
        btnConfirm!!.setOnClickListener(View.OnClickListener { Go() })
    }

    fun Go() {
        if (et_max_number!!.text.toString() == "" || et_max_number!!.text.toString().isEmpty()) {
            CommonMethods.AlertErrorMessage(mContext, getString(R.string.max_deliveries_error))
        } else {
            if (intent != null) {
                val intent = Intent(mContext, DeliveryChargesActivity::class.java)
                intent.putExtra("shopName", getIntent().getStringExtra("shopName"))
                intent.putExtra("categoryName", getIntent().getStringExtra("categoryName"))
                intent.putExtra("shop_category_id", getIntent().getStringExtra("shop_category_id"))
                intent.putExtra("shopABN", getIntent().getStringExtra("shopABN"))
                intent.putExtra("buildingNumber", getIntent().getStringExtra("buildingNumber"))
                intent.putExtra("streetNumber", getIntent().getStringExtra("streetNumber"))
                intent.putExtra("city", getIntent().getStringExtra("city"))
                intent.putExtra("state", getIntent().getStringExtra("state"))
                intent.putExtra("country", getIntent().getStringExtra("country"))
                intent.putExtra("postalCode", getIntent().getStringExtra("postalCode"))
                intent.putExtra("openTime", getIntent().getStringExtra("openTime"))
                intent.putExtra("closeTime", getIntent().getStringExtra("closeTime"))
                intent.putExtra("shopImage", getIntent().getStringExtra("shopImage"))
                intent.putExtra("homeDelivery", getIntent().getStringExtra("homeDelivery"))
                intent.putExtra("deliveriesPerDay", et_max_number!!.text.toString().trim { it <= ' ' })
                intent.putExtra("deliveryOptionsJsonString", getIntent().getStringExtra("deliveryOptionsJsonString"))
                intent.putParcelableArrayListExtra("vendorDeliveryCharges", vendorDeliveryChargesList)
                startActivity(intent)
            }
        }
    }
}