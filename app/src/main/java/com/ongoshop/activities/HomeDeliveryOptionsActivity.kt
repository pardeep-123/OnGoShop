package com.ongoshop.activities


import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ongoshop.R
import com.ongoshop.base.BaseActivity
import com.ongoshop.manager.restApi.RestObservable
import com.ongoshop.manager.restApi.Status
import com.ongoshop.pojo.EditProfileAddShopResponsess
import com.ongoshop.pojo.VendorDeliveryCharge
import com.ongoshop.pojo.VendorDeliveryOption
import com.ongoshop.utils.others.Constants
import com.ongoshop.utils.others.MyApplication
import com.ongoshop.utils.others.SharedPrefUtil
import com.ongoshop.viewmodel.AuthViewModel

import kotlinx.android.synthetic.main.activity_delivery_options.*

import okhttp3.RequestBody
import java.io.File

import java.util.*


class HomeDeliveryOptionsActivity : BaseActivity(), View.OnClickListener, Observer<RestObservable> {
    private val viewModel: AuthViewModel
            by lazy { ViewModelProviders.of(this).get(AuthViewModel::class.java) }

    private lateinit var mContext: HomeDeliveryOptionsActivity
    private var isDeliver = ""
    private var vendorDeliveryOptionsList: ArrayList<VendorDeliveryOption>? = ArrayList()
    private var vendorDeliveryChargesList: ArrayList<VendorDeliveryCharge>? = ArrayList()


    override fun getContentId(): Int {
        return R.layout.activity_delivery_options
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this

        btnYes.setOnClickListener(mContext)
        btnNo.setOnClickListener(mContext)

        if (intent !=null){
            vendorDeliveryOptionsList = intent.getParcelableArrayListExtra<VendorDeliveryOption>("vendorDeliveryOptions") as ArrayList<VendorDeliveryOption>
            vendorDeliveryChargesList = intent.getParcelableArrayListExtra<VendorDeliveryCharge>("vendorDeliveryCharges") as ArrayList<VendorDeliveryCharge>
             Log.e("HomeDeliveryOptionsList", vendorDeliveryOptionsList!!.size.toString())
             Log.e("HomeDeliveryChargeList", vendorDeliveryChargesList!!.size.toString())
        }

    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnYes -> {
                isDeliver = "1"
                if (intent != null) {
                    val intent = Intent(mContext, DeliveryOptionsActivity::class.java)
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
                    intent.putExtra("homeDelivery", isDeliver)

                    intent.putParcelableArrayListExtra("vendorDeliveryOptions", vendorDeliveryOptionsList)
                    Log.e("HomeDeliSize", vendorDeliveryOptionsList!!.size.toString())
                    intent.putParcelableArrayListExtra("vendorDeliveryCharges", vendorDeliveryChargesList)
                    Log.e("HomeDeliChargSize", vendorDeliveryChargesList!!.size.toString())
                    startActivity(intent)
                }
            }

            R.id.btnNo -> {
                isDeliver = "0"
                addShopApi()
            }
        }
    }

    private fun isValid(): Boolean {
        var check = false
        if (!mValidationClass.isNetworkConnected)
            showAlerterRed(resources.getString(R.string.no_internet))
        else
            check = true
        return check
    }

    private fun addShopApi() {
        if (isValid()) {
            if (intent != null) {
                val bodyimage = mValidationClass.prepareFilePart("shopLogo", File(intent.getStringExtra("shopImage")))
                val partShopName = mValidationClass.createPartFromString(intent.getStringExtra("shopName"))
                val partCategoryName = mValidationClass.createPartFromString(intent.getStringExtra("categoryName"))
                val partShopABN = mValidationClass.createPartFromString(intent.getStringExtra("shopABN"))
                val partBuildingNumber = mValidationClass.createPartFromString(intent.getStringExtra("buildingNumber"))
                val partStreetNumber = mValidationClass.createPartFromString(intent.getStringExtra("streetNumber"))
                val partCity = mValidationClass.createPartFromString(intent.getStringExtra("city"))
                val partState = mValidationClass.createPartFromString(intent.getStringExtra("state"))
                val partCountry = mValidationClass.createPartFromString(intent.getStringExtra("country"))
                val partPostalCode = mValidationClass.createPartFromString(intent.getStringExtra("postalCode"))
                val partOpenTime = mValidationClass.createPartFromString(intent.getStringExtra("openTime"))
                val partCloseTime = mValidationClass.createPartFromString(intent.getStringExtra("closeTime"))
                val partHomeDelivery = mValidationClass.createPartFromString(isDeliver)

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

                viewModel.addShopAndDeliveryDetailsApi(this, true, map, bodyimage)
                viewModel.mResponse.observe(this, this)

            }
        }
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
                                        addShopResponsess.getBody()!!.token!!
                                )
                        MyApplication.instance!!.setString(Constants.UserData, modelToString(addShopResponsess.getBody()!!))

                        SharedPrefUtil.getInstance().saveAuthToken(addShopResponsess.getBody()!!.token)
                        SharedPrefUtil.getInstance().saveImage(addShopResponsess.getBody()!!.image)
                        SharedPrefUtil.getInstance().saveUserId(addShopResponsess.getBody()!!.id.toString())
                        SharedPrefUtil.getInstance().saveEmail(addShopResponsess.getBody()!!.email)
                        SharedPrefUtil.getInstance().saveName(addShopResponsess.getBody()!!.name)
                        SharedPrefUtil.getInstance().isLogin = true

                        val intent = Intent(mContext, HomeActivity::class.java)
                        startActivity(intent)
                        finishAffinity()
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
