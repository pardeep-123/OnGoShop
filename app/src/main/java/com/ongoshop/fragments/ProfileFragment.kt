package com.ongoshop.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.RelativeLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.ongoshop.R
import com.ongoshop.activities.*
import com.ongoshop.base.BaseFragment
import com.ongoshop.manager.restApi.RestObservable
import com.ongoshop.manager.restApi.Status
import com.ongoshop.pojo.GetProfileResponse
import com.ongoshop.pojo.VendorDeliveryCharge
import com.ongoshop.pojo.VendorDeliveryOption
import com.ongoshop.utils.others.CommonMethods
import com.ongoshop.utils.others.Constants
import com.ongoshop.utils.others.MyApplication
import com.ongoshop.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_profile.*


class ProfileFragment : BaseFragment(), View.OnClickListener, Observer<RestObservable> {

    private val viewModel: HomeViewModel
            by lazy { ViewModelProviders.of(this).get(HomeViewModel::class.java) }

    lateinit var v: View
    private var shopName=""
    private var shopCategory=""
    private var shopAddress=""
    private var shopABN=""
    private var shopBuildingNumber=""
    private var shopStreetNumber=""
    private var shopPostCode=""
    private var shopCity=""
    private var shopState=""
    private var openTime=""
    private var closeTime=""
    private var shopCountry=""
    private var shopImage =""
    private var phone =""
    private var countryCode =""
    private var userId =""
    private var userImage =""

    lateinit var btnEditProfile: Button
    lateinit var rlShop: RelativeLayout
    private var vendorDeliveryOptionsList: ArrayList<VendorDeliveryOption>? = ArrayList()
    private var vendorDeliveryChargesList: ArrayList<VendorDeliveryCharge>? = ArrayList()

    companion object {

        fun newInstance(): ProfileFragment {
            return ProfileFragment()
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_profile, container, false)

        rlShop = v.findViewById<RelativeLayout>(R.id.rlShop)
        btnEditProfile = v.findViewById<Button>(R.id.btnEditProfile)



        rlShop.setOnClickListener(this)
        btnEditProfile.setOnClickListener(this)

        return v
    }


    override fun onResume() {
        super.onResume()
        getProfileApi()
    }
    private fun getProfileApi() {
        if (!MyApplication.instance!!.checkIfHasNetwork())
            showAlerterRed(resources.getString(R.string.no_internet))
        else {
            viewModel.getProfile(activity!!, true)
            viewModel.mResponse.observe(this, this)
        }
    }


    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.rlShop -> {
                val intent = Intent(activity, MyShopActivity::class.java)
                intent.putExtra("shopName", shopName)
                intent.putExtra("shopCategory", shopCategory)
                intent.putExtra("shopABN", shopABN)
                intent.putExtra("shopAddress", shopAddress)
                intent.putExtra("shopBuildingNumber", shopBuildingNumber)
                intent.putExtra("shopStreetNumber", shopStreetNumber)
                intent.putExtra("shopCity", shopCity)
                intent.putExtra("shopState", shopState)
                intent.putExtra("openTime", openTime)
                intent.putExtra("closeTime", closeTime)
                intent.putExtra("shopCountry", shopCountry)
                intent.putExtra("shopPostCode", shopPostCode)
                intent.putExtra("shopImage", shopImage)
                intent.putParcelableArrayListExtra("vendorDeliveryOptions", vendorDeliveryOptionsList)
                intent.putParcelableArrayListExtra("vendorDeliveryCharges", vendorDeliveryChargesList)
                startActivity(intent)
            }
            R.id.btnEditProfile -> {
                val intent = Intent(activity, EditProfileActivity::class.java)
                intent.putExtra("name", tv_name.text.toString())
                intent.putExtra("email", tv_email.text.toString())
                intent.putExtra("phone", phone)
                intent.putExtra("countryCode", countryCode)
                intent.putExtra("image", userImage)
                intent.putExtra("userId", userId)
                startActivity(intent)
            }
        }
    }

    private fun setShopData(shopdetails: GetProfileResponse.Body) {
        shopName= shopdetails.shopName
        shopCategory= shopdetails.shopCategory
        shopABN= shopdetails.abn
        shopAddress= shopdetails.shopAddress
        shopBuildingNumber= shopdetails.buildingNumber
        shopStreetNumber= shopdetails.streetNumber
        shopCity= shopdetails.city
        shopCountry= shopdetails.country
        shopState = shopdetails.state
        shopPostCode= shopdetails.postalCode
        shopImage= shopdetails.shopLogo
        openTime= shopdetails.shopOpenTime
        closeTime= shopdetails.shopCloseTime
        vendorDeliveryChargesList= shopdetails.vendorDeliveryCharges
        vendorDeliveryOptionsList= shopdetails.vendorDeliveryOptions
    }

    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {
                if (it.data is GetProfileResponse) {
                    val getProfileResponse: GetProfileResponse = it.data
                    if (getProfileResponse.getCode() == Constants.success_code) {
                        showSuccessToast(getProfileResponse!!.getMessage()!!)
                        tv_name.setText(getProfileResponse.body.name)
                        tv_email.setText(getProfileResponse.body.email)
                        tv_phone.setText("+"+getProfileResponse.body.countryCode + "-" + getProfileResponse.body.phone)
                        Glide.with(activity!!).load(getProfileResponse.body.image).error(R.mipmap.no_image_placeholder).into(iv_profile_image)
                        userId= getProfileResponse.body.id.toString()
                        userImage= getProfileResponse.body.image.toString()
                        phone = getProfileResponse.body.phone.toString()
                        countryCode= getProfileResponse.body.countryCode.toString()

                        setShopData(getProfileResponse.body)

                    } else {
                        CommonMethods.AlertErrorMessage(activity, getProfileResponse.getMessage())
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