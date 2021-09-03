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
    private var isDeliver =""
    private var deliveriesPerDay =""
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
    ): View {
        v = inflater.inflate(R.layout.fragment_profile, container, false)

        rlShop = v.findViewById(R.id.rlShop)
        btnEditProfile = v.findViewById(R.id.btnEditProfile)


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
            viewModel.getProfile(requireActivity(), true)
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
                intent.putExtra("homeDelivery", isDeliver)
                intent.putExtra("deliveriesPerDay", deliveriesPerDay)
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
        shopName= shopdetails.vendorDetail.shopName
        shopCategory= shopdetails.vendorDetail.shopCategory
        shopABN= shopdetails.vendorDetail.abn
        shopAddress= shopdetails.vendorDetail.shopAddress
        shopBuildingNumber= shopdetails.vendorDetail.buildingNumber
        shopStreetNumber= shopdetails.vendorDetail.streetNumber
        shopCity= shopdetails.vendorDetail.city
        shopCountry= shopdetails.vendorDetail.country
        shopState = shopdetails.vendorDetail.state
        shopPostCode= shopdetails.vendorDetail.postalCode
        shopImage= shopdetails.vendorDetail.shopLogo
        openTime= shopdetails.vendorDetail.shopOpenTime
        closeTime= shopdetails.vendorDetail.shopCloseTime
        isDeliver= shopdetails.vendorDetail.homeDelivery.toString()
        deliveriesPerDay= shopdetails.vendorDetail.deliveriesPerDay.toString()
        vendorDeliveryChargesList= shopdetails.vendorDeliveryCharges
        vendorDeliveryOptionsList= shopdetails.vendorDeliveryOptions
    }

    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {
                if (it.data is GetProfileResponse) {
                    val getProfileResponse: GetProfileResponse = it.data
                    if (getProfileResponse.code == Constants.success_code) {
                       // showSuccessToast(getProfileResponse.message!!)
                      //  tv_name.text = getProfileResponse.body.name
                        tv_name.text = getProfileResponse.body.vendorDetail.name
                        tv_email.text = getProfileResponse.body.email
                        tv_addres.text = getProfileResponse.body.geoLocation
                        tv_phone.text = "+"+getProfileResponse.body.countryCode + "-" + getProfileResponse.body.phone
                        Glide.with(requireActivity()).load(getProfileResponse.body.vendorDetail.image).error(R.mipmap.no_image_placeholder).into(iv_profile_image)
                        userId= getProfileResponse.body.id.toString()
                        if (getProfileResponse.body.vendorDetail.image!="")
                        userImage= getProfileResponse.body.vendorDetail.image
                        phone = getProfileResponse.body.phone.toString()
                        countryCode= getProfileResponse.body.countryCode.toString()

                        setShopData(getProfileResponse.body)

                    } else {
                        CommonMethods.AlertErrorMessage(activity, getProfileResponse.message)
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