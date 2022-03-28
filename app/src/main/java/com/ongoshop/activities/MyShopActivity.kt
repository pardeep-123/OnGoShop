package com.ongoshop.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import com.ongoshop.R
import com.ongoshop.adapter.MyShopDeliveryChargesAdapter
import com.ongoshop.base.BaseActivity
import com.ongoshop.pojo.*
import com.ongoshop.utils.others.SharedPrefUtil
import kotlinx.android.synthetic.main.activity_my_shop.*
import kotlin.collections.ArrayList

class MyShopActivity : BaseActivity(), View.OnClickListener {

    private var shopName = ""
    private lateinit var myShopDeliveryChargesAdapter: MyShopDeliveryChargesAdapter
    private var shopCategory = ""
    private var shopAddress = ""
    private var isDeliver = ""
    private var deliveriesPerDay = ""
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

    companion object {
        lateinit var mContext: MyShopActivity
    }

    override fun getContentId(): Int {
        return R.layout.activity_my_shop
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this

        ivBack.setOnClickListener(mContext)
        btnEdit.setOnClickListener(mContext)
        //   tv_category_name.setOnClickListener(mContext)

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
            isDeliver = intent.getStringExtra("homeDelivery")!!
            deliveriesPerDay = intent.getStringExtra("deliveriesPerDay")!!

            vendorDeliveryOptionsList =
                intent.getParcelableArrayListExtra<VendorDeliveryOption>("vendorDeliveryOptions")
                        as ArrayList<VendorDeliveryOption>
            vendorDeliveryChargesList =
                intent.getParcelableArrayListExtra<VendorDeliveryCharge>("vendorDeliveryCharges")
                        as ArrayList<VendorDeliveryCharge>

            tv_shop_name.text = shopName
            tv_category_name.text = shopCategory
            tv_abn.text = shopABN
            tv_address.text = shopCity
            tv_shop_address.text =
                shopStreetNumber + "," + shopCity + "," + shopCountry + "," + shopPostCode
            tv_building_number.text = shopBuildingNumber
            tv_postal_code.text = shopPostCode
            Glide.with(mContext).load(shopImage).placeholder(R.drawable.ic_image_placeholder).error(R.drawable.ic_image_placeholder)
                .into(iv_shop)

            if (isDeliver.equals("1")) {
                ll_delivery_options.visibility = View.VISIBLE
                ll_delivery_charges.visibility = View.VISIBLE

                if (vendorDeliveryOptionsList!!.get(0).deliveryTimeFrom.equals("")) {
                    ll_sunday.visibility = View.GONE
                } else {
                    ll_sunday.visibility = View.VISIBLE
                    tv_delivery_timings_sunday.setText(
                        vendorDeliveryOptionsList!!.get(0).deliveryTimeFrom + " - " + vendorDeliveryOptionsList!!.get(
                            0
                        ).deliveryTimeTo
                    )
                }
                if (vendorDeliveryOptionsList!!.get(1).deliveryTimeFrom.equals("")) {
                    ll_monday.visibility = View.GONE
                } else {
                    ll_monday.visibility = View.VISIBLE
                    tv_delivery_timings_monday.setText(
                        vendorDeliveryOptionsList!!.get(1).deliveryTimeFrom + " - " + vendorDeliveryOptionsList!!.get(
                            1
                        ).deliveryTimeTo
                    )
                }
                if (vendorDeliveryOptionsList!!.get(2).deliveryTimeFrom.equals("")) {
                    ll_tuesday.visibility = View.GONE
                } else {
                    ll_tuesday.visibility = View.VISIBLE
                    tv_delivery_timings_tuesday.setText(
                        vendorDeliveryOptionsList!!.get(2).deliveryTimeFrom + " - " + vendorDeliveryOptionsList!!.get(
                            2
                        ).deliveryTimeTo
                    )
                }
                if (vendorDeliveryOptionsList!!.get(3).deliveryTimeFrom.equals("")) {
                    ll_wednesday.visibility = View.GONE
                } else {
                    ll_wednesday.visibility = View.VISIBLE
                    tv_delivery_timings_wednesday.setText(
                        vendorDeliveryOptionsList!!.get(3).deliveryTimeFrom + " - " + vendorDeliveryOptionsList!!.get(
                            3
                        ).deliveryTimeTo
                    )
                }
                if (vendorDeliveryOptionsList!!.get(4).deliveryTimeFrom.equals("")) {
                    ll_thursday.visibility = View.GONE
                } else {
                    ll_thursday.visibility = View.VISIBLE
                    tv_delivery_timings_thursday.setText(
                        vendorDeliveryOptionsList!!.get(4).deliveryTimeFrom + " - " + vendorDeliveryOptionsList!!.get(
                            4
                        ).deliveryTimeTo
                    )
                }
                if (vendorDeliveryOptionsList!!.get(5).deliveryTimeFrom.equals("")) {
                    ll_friday.visibility = View.GONE
                } else {
                    ll_friday.visibility = View.VISIBLE
                    tv_delivery_timings_friday.setText(
                        vendorDeliveryOptionsList!!.get(5).deliveryTimeFrom + " - " + vendorDeliveryOptionsList!!.get(
                            5
                        ).deliveryTimeTo
                    )
                }
                if (vendorDeliveryOptionsList!!.get(6).deliveryTimeFrom.equals("")) {
                    ll_saturday.visibility = View.GONE
                } else {
                    ll_saturday.visibility = View.VISIBLE
                    tv_delivery_timings_saturday.setText(
                        vendorDeliveryOptionsList!!.get(6).deliveryTimeFrom + " - " + vendorDeliveryOptionsList!!.get(
                            6
                        ).deliveryTimeTo
                    )
                }

                myShopDeliveryChargesAdapter =
                    MyShopDeliveryChargesAdapter(mContext, vendorDeliveryChargesList!!, mContext)
                rv_delivery_charges.layoutManager = LinearLayoutManager(mContext)
                rv_delivery_charges.adapter = myShopDeliveryChargesAdapter
            } else {
                ll_delivery_options.visibility = View.GONE
                ll_delivery_charges.visibility = View.GONE
            }
        }
    }

    override fun onRestart() {
        super.onRestart()
        val gson = GsonBuilder().create()

        val body = gson.fromJson<EditProfileAddShopResponsess.Body>(
            SharedPrefUtil.getInstance().editProfileRespone,
            EditProfileAddShopResponsess.Body::class.java
        )
        if (body != null) {
            if (body.shopName!=null)
            shopName = body.shopName.toString()
            if (body.shopCategory!=null)
            shopCategory = body.shopCategory.toString()
            if (body.abn!=null)
            shopABN = body.abn.toString()
            if (body.shopAddress!=null)
            shopAddress = body.shopAddress.toString()
            if (body.buildingNumber!=null)
            shopBuildingNumber = body.buildingNumber.toString()
            if (body.streetNumber!=null)
            shopStreetNumber = body.streetNumber.toString()
            if (body.city!=null)
            shopCity = body.city.toString()
            if (body.state!=null)
            shopState = body.state.toString()
            if (body.country!=null)
            shopCountry = body.country.toString()
            if (body.postalCode!=null)
            shopPostCode = body.postalCode.toString()
            if (body.image!=null)
            shopImage = body.image.toString()
            if (body.shopOpenTime!=null)
            openTime = body.shopOpenTime.toString()
            if (body.shopCloseTime!=null)
            closeTime = body.shopCloseTime.toString()
            if (body.homeDelivery!=null)
            isDeliver = body.homeDelivery.toString()
            if (body.deliveriesPerDay!=null)
            deliveriesPerDay = body.deliveriesPerDay.toString()
            Log.e("onRestart", "callled")

            vendorDeliveryOptionsList = body.vendorDeliveryOptions
            vendorDeliveryChargesList = body.vendorDeliveryCharges

            tv_shop_name.setText(shopName)
            tv_category_name.setText(shopCategory)
            tv_abn.setText(shopABN)
            tv_shop_address.setText(shopStreetNumber + "," + shopCity + "," + shopCountry + "," + shopPostCode)
            tv_building_number.setText(shopBuildingNumber)
            tv_postal_code.setText(shopPostCode)
            Glide.with(mContext).load(shopImage).placeholder(R.drawable.ic_image_placeholder).error(R.drawable.ic_image_placeholder)
                .into(iv_shop)

            if (vendorDeliveryOptionsList!!.get(0).deliveryTimeFrom.equals("")) {
                ll_sunday.visibility = View.GONE
            } else {
                ll_sunday.visibility = View.VISIBLE
                tv_delivery_timings_sunday.setText(
                    vendorDeliveryOptionsList!!.get(0).deliveryTimeFrom + "-" + vendorDeliveryOptionsList!!.get(
                        0
                    ).deliveryTimeTo
                )
            }
            if (vendorDeliveryOptionsList!!.get(1).deliveryTimeFrom.equals("")) {
                ll_monday.visibility = View.GONE
            } else {
                ll_monday.visibility = View.VISIBLE
                tv_delivery_timings_monday.setText(
                    vendorDeliveryOptionsList!!.get(1).deliveryTimeFrom + "-" + vendorDeliveryOptionsList!!.get(
                        1
                    ).deliveryTimeTo
                )
            }
            if (vendorDeliveryOptionsList!!.get(2).deliveryTimeFrom.equals("")) {
                ll_tuesday.visibility = View.GONE
            } else {
                ll_tuesday.visibility = View.VISIBLE
                tv_delivery_timings_tuesday.setText(
                    vendorDeliveryOptionsList!!.get(2).deliveryTimeFrom + "-" + vendorDeliveryOptionsList!!.get(
                        2
                    ).deliveryTimeTo
                )
            }
            if (vendorDeliveryOptionsList!!.get(3).deliveryTimeFrom.equals("")) {
                ll_wednesday.visibility = View.GONE
            } else {
                ll_wednesday.visibility = View.VISIBLE
                tv_delivery_timings_wednesday.setText(
                    vendorDeliveryOptionsList!!.get(3).deliveryTimeFrom + "-" + vendorDeliveryOptionsList!!.get(
                        3
                    ).deliveryTimeTo
                )
            }
            if (vendorDeliveryOptionsList!!.get(4).deliveryTimeFrom.equals("")) {
                ll_thursday.visibility = View.GONE
            } else {
                ll_thursday.visibility = View.VISIBLE
                tv_delivery_timings_thursday.setText(
                    vendorDeliveryOptionsList!!.get(4).deliveryTimeFrom + "-" + vendorDeliveryOptionsList!!.get(
                        4
                    ).deliveryTimeTo
                )
            }
            if (vendorDeliveryOptionsList!!.get(5).deliveryTimeFrom.equals("")) {
                ll_friday.visibility = View.GONE
            } else {
                ll_friday.visibility = View.VISIBLE
                tv_delivery_timings_friday.setText(
                    vendorDeliveryOptionsList!!.get(5).deliveryTimeFrom + "-" + vendorDeliveryOptionsList!!.get(
                        5
                    ).deliveryTimeTo
                )
            }
            if (vendorDeliveryOptionsList!!.get(6).deliveryTimeFrom.equals("")) {
                ll_saturday.visibility = View.GONE
            } else {
                ll_saturday.visibility = View.VISIBLE
                tv_delivery_timings_saturday.text =
                    vendorDeliveryOptionsList!!.get(6).deliveryTimeFrom + "-" + vendorDeliveryOptionsList!!.get(
                        6
                    ).deliveryTimeTo
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
                intent.putExtra("homeDelivery", isDeliver)
                intent.putExtra("deliveriesPerDay", deliveriesPerDay)
                intent.putParcelableArrayListExtra(
                    "vendorDeliveryOptions",
                    vendorDeliveryOptionsList
                )
                intent.putParcelableArrayListExtra(
                    "vendorDeliveryCharges",
                    vendorDeliveryChargesList
                )
                startActivity(intent)
            }
        }
    }
}
