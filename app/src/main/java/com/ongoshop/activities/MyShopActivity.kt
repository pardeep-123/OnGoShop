package com.ongoshop.activities

import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.ongoshop.R

import com.ongoshop.base.BaseActivity
import com.ongoshop.pojo.CategoryListResponse
import com.ongoshop.pojo.VendorDeliveryCharge
import com.ongoshop.pojo.VendorDeliveryOption
import com.ongoshop.utils.others.CommonMethods
import com.ongoshop.utils.others.Constants
import kotlinx.android.synthetic.main.activity_my_shop.*
import java.util.*
import kotlin.collections.ArrayList


class MyShopActivity : BaseActivity(), View.OnClickListener {

    lateinit var mContext: MyShopActivity

    private var shopName = ""
    private var shopCategory = ""
    private var shopAddress = ""
    private var shopABN = ""
    private var shopBuildingNumber = ""
    private var shopStreetNumber = ""
    private var shopCity = ""
    private var shopState = ""
    private var shopCountry = ""
    private var shopPostCode = ""
    private var shopImage = ""
    private var openTime = ""
    private var closeTime = ""
    private var vendorDeliveryOptionsList: ArrayList<VendorDeliveryOption>? = ArrayList()
    private var vendorDeliveryChargesList: ArrayList<VendorDeliveryCharge>? = ArrayList()


    override fun getContentId(): Int {
        return R.layout.activity_my_shop
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this

        ivBack.setOnClickListener(mContext)
        btnEdit.setOnClickListener(mContext)
        tv_category_name.setOnClickListener(mContext)

        if (intent != null) {
            shopName = intent.getStringExtra("shopName")!!
            shopCategory = intent.getStringExtra("shopCategory")!!
            shopABN = intent.getStringExtra("shopABN")!!
            shopAddress = intent.getStringExtra("shopAddress")!!
            shopBuildingNumber = intent.getStringExtra("shopBuildingNumber")!!
            shopStreetNumber = intent.getStringExtra("shopStreetNumber")!!
            shopCity = intent.getStringExtra("shopCity")!!
            shopState = intent.getStringExtra("shopState")!!
            shopCountry = intent.getStringExtra("shopCountry")!!
            shopPostCode = intent.getStringExtra("shopPostCode")!!
            shopImage = intent.getStringExtra("shopImage")!!
            openTime = intent.getStringExtra("openTime")!!
            closeTime = intent.getStringExtra("closeTime")!!

            vendorDeliveryOptionsList = intent.getParcelableArrayListExtra<VendorDeliveryOption>("vendorDeliveryOptions") as ArrayList<VendorDeliveryOption>
            vendorDeliveryChargesList = intent.getParcelableArrayListExtra<VendorDeliveryCharge>("vendorDeliveryCharges") as ArrayList<VendorDeliveryCharge>

            tv_shop_name.setText(shopName)
            tv_category_name.setText(shopCategory)
            tv_abn.setText(shopABN)
            tv_shop_address.setText(shopStreetNumber + "," + shopCity + "," + shopCountry + "," + shopPostCode)
            tv_building_number.setText(shopBuildingNumber)
            tv_postal_code.setText(shopPostCode)
            Glide.with(mContext).load(shopImage).error(R.drawable.ic_image_placeholder).into(iv_shop)

            if (vendorDeliveryOptionsList!!.get(0).deliveryTimeFrom.equals("")) {
                ll_sunday.visibility = View.GONE
            } else {
                ll_sunday.visibility = View.VISIBLE
                tv_delivery_timings_sunday.setText(vendorDeliveryOptionsList!!.get(0).deliveryTimeFrom + "-" + vendorDeliveryOptionsList!!.get(0).deliveryTimeTo)
            }
            if (vendorDeliveryOptionsList!!.get(1).deliveryTimeFrom.equals("")) {
                ll_monday.visibility = View.GONE
            } else {
                ll_monday.visibility = View.VISIBLE
                tv_delivery_timings_monday.setText(vendorDeliveryOptionsList!!.get(1).deliveryTimeFrom + "-" + vendorDeliveryOptionsList!!.get(1).deliveryTimeTo)
            }
            if (vendorDeliveryOptionsList!!.get(2).deliveryTimeFrom.equals("")) {
                ll_tuesday.visibility = View.GONE
            } else {
                ll_tuesday.visibility = View.VISIBLE
                tv_delivery_timings_tuesday.setText(vendorDeliveryOptionsList!!.get(2).deliveryTimeFrom + "-" + vendorDeliveryOptionsList!!.get(2).deliveryTimeTo)
            }
            if (vendorDeliveryOptionsList!!.get(3).deliveryTimeFrom.equals("")) {
                ll_wednesday.visibility = View.GONE
            } else {
                ll_wednesday.visibility = View.VISIBLE
                tv_delivery_timings_wednesday.setText(vendorDeliveryOptionsList!!.get(3).deliveryTimeFrom + "-" + vendorDeliveryOptionsList!!.get(3).deliveryTimeTo)
            }
            if (vendorDeliveryOptionsList!!.get(4).deliveryTimeFrom.equals("")) {
                ll_thursday.visibility = View.GONE
            } else {
                ll_thursday.visibility = View.VISIBLE
                tv_delivery_timings_thursday.setText(vendorDeliveryOptionsList!!.get(4).deliveryTimeFrom + "-" + vendorDeliveryOptionsList!!.get(4).deliveryTimeTo)
            }
            if (vendorDeliveryOptionsList!!.get(5).deliveryTimeFrom.equals("")) {
                ll_friday.visibility = View.GONE
            } else {
                ll_friday.visibility = View.VISIBLE
                tv_delivery_timings_friday.setText(vendorDeliveryOptionsList!!.get(5).deliveryTimeFrom + "-" + vendorDeliveryOptionsList!!.get(5).deliveryTimeTo)
            }
            if (vendorDeliveryOptionsList!!.get(6).deliveryTimeFrom.equals("")) {
                ll_saturday.visibility = View.GONE
            } else {
                ll_saturday.visibility = View.VISIBLE
                tv_delivery_timings_saturday.setText(vendorDeliveryOptionsList!!.get(6).deliveryTimeFrom + "-" + vendorDeliveryOptionsList!!.get(6).deliveryTimeTo)
            }

        }
    }


    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.ivBack -> {
                onLeftIconClick()
            }
            R.id.btnEdit -> {
                val intent = Intent(mContext, MyShopEditActivity::class.java)
                intent.putExtra("shopName", shopName)
                intent.putExtra("shopCategory", shopCategory)
                intent.putExtra("shopABN", shopABN)
                intent.putExtra("shopAddress", shopAddress)
                intent.putExtra("shopBuildingNumber", shopBuildingNumber)
                intent.putExtra("shopStreetNumber", shopStreetNumber)
                intent.putExtra("shopCity", shopCity)
                intent.putExtra("shopState", shopState)
                intent.putExtra("shopCountry", shopCountry)
                intent.putExtra("shopPostCode", shopPostCode)
                intent.putExtra("shopImage", shopImage)
                intent.putExtra("openTime", openTime)
                intent.putExtra("closeTime", closeTime)
                intent.putParcelableArrayListExtra("vendorDeliveryOptions", vendorDeliveryOptionsList)
                intent.putParcelableArrayListExtra("vendorDeliveryCharges", vendorDeliveryChargesList)
                startActivity(intent)
            }
        }
    }
}
