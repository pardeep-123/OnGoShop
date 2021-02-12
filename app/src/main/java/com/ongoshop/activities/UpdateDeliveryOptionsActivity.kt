package com.ongoshop.activities


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ongoshop.R

import com.ongoshop.adapter.UpdateDeliveryOptionsAdapter
import com.ongoshop.base.BaseActivity
import com.ongoshop.manager.restApi.RestObservable
import com.ongoshop.manager.restApi.Status
import com.ongoshop.pojo.EditProfileAddShopResponsess
import com.ongoshop.pojo.VendorDeliveryOption
import com.ongoshop.utils.helperclasses.DeliveryOptionsClicklisetener
import com.ongoshop.utils.others.Constants
import com.ongoshop.utils.others.MyApplication
import com.ongoshop.utils.others.SharedPrefUtil
import com.ongoshop.utils.others.ValidationsClass
import com.ongoshop.viewmodel.AuthViewModel
import kotlinx.android.synthetic.main.activity_my_shop_edit.*
import kotlinx.android.synthetic.main.activity_update_delivery_options.*
import kotlinx.android.synthetic.main.activity_update_delivery_options.ivBack

import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.File
import java.util.HashMap


class UpdateDeliveryOptionsActivity : BaseActivity(), View.OnClickListener, DeliveryOptionsClicklisetener, Observer<RestObservable> {

    lateinit var recyclerview: RecyclerView
    private var shopABN=""
    private var shopName = ""
    private var shopCategory = ""
    private var isDeliver = ""
    private var deliveriesPerDay = ""
    private var shopBuildingNumber = ""
    private var shopStreetNumber = ""
    private var shopCity = ""
    private var shopState = ""
    private var shopCountry = ""
    private var shopPostCode = ""
    private var shopImage = ""
    private var openTime = ""
    private var closeTime = ""
    lateinit var deliveryOptions1Adapter: UpdateDeliveryOptionsAdapter
    private lateinit var mContext: UpdateDeliveryOptionsActivity
    private var vendorDeliveryOptionsList: ArrayList<VendorDeliveryOption>? = ArrayList()
    private var vendorDeliveryOptionsupdatedList: ArrayList<VendorDeliveryOption>? = ArrayList()
    private val viewModel: AuthViewModel
            by lazy { ViewModelProviders.of(this).get(AuthViewModel::class.java) }


    private var vendorDeliveryOptionsListJsonArray = ""

    override fun getContentId(): Int {
        return R.layout.activity_update_delivery_options
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this

        btnConfirm.setOnClickListener(mContext)
        ivBack.setOnClickListener(mContext)

        recyclerview = findViewById(R.id.recyclerview)



        if (intent != null) {


            shopName = intent.getStringExtra("shopName")!!
            shopCategory = intent.getStringExtra("shopCategory")!!
            shopABN = intent.getStringExtra("shopABN")!!
          //  shopAddress = intent.getStringExtra("shopAddress")!!
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


            et_max_number.setText(deliveriesPerDay)

            vendorDeliveryOptionsList = intent.getParcelableArrayListExtra<VendorDeliveryOption>("vendorDeliveryOptions") as
                    ArrayList<VendorDeliveryOption>
            Log.e("UpdateDeliOptionSize", vendorDeliveryOptionsList!!.size.toString())

            deliveryOptions1Adapter = UpdateDeliveryOptionsAdapter(mContext, vendorDeliveryOptionsList!!, mContext, mContext)
            recyclerview.layoutManager = LinearLayoutManager(mContext)
            recyclerview.adapter = deliveryOptions1Adapter

        }

    }


    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnConfirm -> {
                makejsonArray()
            }

            R.id.ivBack -> {
                onLeftIconClick()
            }
        }
    }


    open fun setUpdatedList(pos: Int, vendorDeliveryOptionsList: ArrayList<VendorDeliveryOption>) {
        vendorDeliveryOptionsupdatedList = vendorDeliveryOptionsList
        Log.e("delivertybe", vendorDeliveryOptionsupdatedList!!.get(pos).deliveryTimeTo)
        Log.e("list", vendorDeliveryOptionsupdatedList.toString())
    }


    fun makejsonArray() {
        val jsonArray = JSONArray()
        for (i in 0 until vendorDeliveryOptionsupdatedList!!.size) {
            val vendorDeliveryOptionLists = JSONObject()
            try {
                vendorDeliveryOptionLists.put("id", vendorDeliveryOptionsupdatedList!!.get(i).id)
                vendorDeliveryOptionLists.put("vendorId", vendorDeliveryOptionsupdatedList!!.get(i).vendorId)
                vendorDeliveryOptionLists.put("sortOrder", vendorDeliveryOptionsupdatedList!!.get(i).sortOrder)
                vendorDeliveryOptionLists.put("day", vendorDeliveryOptionsupdatedList!!.get(i).day)
                vendorDeliveryOptionLists.put("deliveryTimeFrom", vendorDeliveryOptionsupdatedList!!.get(i).deliveryTimeFrom)
                vendorDeliveryOptionLists.put("deliveryTimeTo", vendorDeliveryOptionsupdatedList!!.get(i).deliveryTimeTo)
                vendorDeliveryOptionLists.put("noDelivery", vendorDeliveryOptionsupdatedList!!.get(i).noDelivery)
                vendorDeliveryOptionLists.put("created", vendorDeliveryOptionsupdatedList!!.get(i).created)
                vendorDeliveryOptionLists.put("updated", vendorDeliveryOptionsupdatedList!!.get(i).updated)
                vendorDeliveryOptionLists.put("createdAt", vendorDeliveryOptionsupdatedList!!.get(i).createdAt)
                vendorDeliveryOptionLists.put("updatedAt", vendorDeliveryOptionsupdatedList!!.get(i).updatedAt)
            } catch (e: JSONException) { // TODO Auto-generated catch block
                e.printStackTrace()
            }

            jsonArray.put(vendorDeliveryOptionLists)


        }
        Log.e("jsonString:", jsonArray.toString())
        editShopWithDeliveryOptions(jsonArray.toString())

        /* }*/


    }

    private fun isValid(): Boolean {
        var check = false
        if (!mValidationClass.isNetworkConnected)
            showAlerterRed(resources.getString(R.string.no_internet))
        else if (mValidationClass.checkStringNull(et_max_number.text.toString().trim()))
            showAlerterRed(resources.getString(R.string.max_deliveries_error))
        else
            check = true
        return check
    }

    private fun editShopWithDeliveryOptions(deliveryOptionsJsonArray: String) {
        if (isValid()) {
            if (intent != null) {
                     // val bodyimage = mValidationClass.prepareFilePart("shopLogo", File(intent.getStringExtra("shopImage")))
                val partShopName = mValidationClass.createPartFromString(shopName)
                val partCategoryName = mValidationClass.createPartFromString(shopCategory)
                val partShopABN = mValidationClass.createPartFromString(shopABN)
                val partBuildingNumber = mValidationClass.createPartFromString(shopBuildingNumber)
                val partStreetNumber = mValidationClass.createPartFromString(shopStreetNumber)
                val partCity = mValidationClass.createPartFromString(shopCity)
                val partState = mValidationClass.createPartFromString(shopState)
                val partCountry = mValidationClass.createPartFromString(shopCountry)
                val partPostalCode = mValidationClass.createPartFromString(shopPostCode)
                val partOpenTime = mValidationClass.createPartFromString(openTime)
                val partCloseTime = mValidationClass.createPartFromString(closeTime)
                val partHomeDelivery = mValidationClass.createPartFromString(isDeliver)
                val partDeliveriesPerDay = mValidationClass.createPartFromString(et_max_number.text.toString())
                val partDeliveryOptionsJsonArrayString = mValidationClass.createPartFromString(deliveryOptionsJsonArray)

                val partToken = mValidationClass.createPartFromString(SharedPrefUtil.getInstance().deviceToken)
                val partType = mValidationClass.createPartFromString(Constants.Device_Type)


                val map = HashMap<String, RequestBody>()
                map.put("shopName", partShopName)
                map.put("shopCategory", partCategoryName)
                map.put("abn", partShopABN)
                map.put("buildingNumber", partBuildingNumber)
                map.put("streetNumber", partStreetNumber)
                map.put("city", partCity)
                map.put("state", partState)
                map.put("country", partCountry)
                map.put("postalCode", partPostalCode)
                map.put("shopOpenTime", partOpenTime)
                map.put("shopCloseTime", partCloseTime)
                map.put("homeDelivery", partHomeDelivery)
                map.put("deviceType", partType)
                map.put("deviceToken", partToken)
                map.put("deliveriesPerDay", partDeliveriesPerDay)
                map.put("homeDelivery", partHomeDelivery)
                map.put("vendorDeliveryOptions", partDeliveryOptionsJsonArrayString)

                var bodyimage = ""

                if (!shopImage.equals("NoImageChanged")) {
                    bodyimage = intent.getStringExtra("shopImage")!!
                } else {
                    bodyimage = ""
                }
                viewModel.editShopDelivery(mContext, true, map, bodyimage!!,  mValidationClass)
                viewModel.mResponse.observe(this, this)

            }
        }
    }


    override fun deliveryOptionClick(deliveryOptionJsonArray: String) {
        vendorDeliveryOptionsListJsonArray = deliveryOptionJsonArray
    }

    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {
                if (it.data is EditProfileAddShopResponsess) {
                    val addShopResponsess: EditProfileAddShopResponsess = it.data
                    if (addShopResponsess.getCode() == Constants.success_code) {
                        showSuccessToast(mContext, addShopResponsess.message)

                        MyApplication.getnstance()
                                .setString(
                                        Constants.AuthKey,
                                        addShopResponsess!!.body.token!!
                                )
                        MyApplication.instance!!.setString(Constants.UserData, modelToString(addShopResponsess))

                        SharedPrefUtil.getInstance().saveAuthToken(addShopResponsess.body.token)
                        SharedPrefUtil.getInstance().saveImage(addShopResponsess.body.image)
                        SharedPrefUtil.getInstance().saveUserId(addShopResponsess.body.id.toString())
                        SharedPrefUtil.getInstance().saveEmail(addShopResponsess.body.email)
                        SharedPrefUtil.getInstance().saveName(addShopResponsess.body.name)
                        MyShopActivity.mContext.finish()
                        MyShopEditActivity.mContext.finish()
                        finish()
                    }

                }

            }
            it.status == Status.ERROR -> {
                if (it.data != null) {
                    showAlerterRed(it.data as String)
                } else {
                    showAlerterRed(it.error!!.toString())
                }
            }
            it.status == Status.LOADING -> {

            }
        }

    }

}
