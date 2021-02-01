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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.ongoshop.R
import com.ongoshop.adapter.CategoryTypesAddShopAdapter
import com.ongoshop.adapter.CategoryTypesEditShopAdapter

import com.ongoshop.base.BaseActivity
import com.ongoshop.manager.restApi.RestObservable
import com.ongoshop.manager.restApi.Status
import com.ongoshop.pojo.CategoryListResponse
import com.ongoshop.pojo.VendorDeliveryCharge
import com.ongoshop.pojo.VendorDeliveryOption
import com.ongoshop.utils.helperclasses.CategoryTypesClicklisetener
import com.ongoshop.utils.others.CommonMethods
import com.ongoshop.utils.others.Constants
import com.ongoshop.viewmodel.AuthViewModel
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumFile
import com.yanzhenjie.album.api.widget.Widget


import kotlinx.android.synthetic.main.activity_my_shop_edit.*

import java.text.SimpleDateFormat

import java.util.*
import kotlin.collections.ArrayList


class MyShopEditActivity : BaseActivity(), View.OnClickListener, Observer<RestObservable>, CategoryTypesClicklisetener {

    lateinit var mContext: MyShopEditActivity

    private var shopName = ""
    private var shopCategory = ""
    private var shopAddress = ""
    private var shopABN = ""
    private var shopBuildingNumber = ""
    private var shopStreetNumber = ""
    private var shopCity=""
    private var shopState=""
    private var shopCountry=""
    private var shopPostCode = ""
    private var shopImage = ""
    private var openTime = ""
    private var closeTime = ""
    private var vendorDeliveryOptionsList: ArrayList<VendorDeliveryOption>? = ArrayList()
    private var vendorDeliveryChargesList: ArrayList<VendorDeliveryCharge>? = ArrayList()
    lateinit var categoryTypesDialog: Dialog

    private var mImagePath = ""
    private var categoryTypeId = ""
    private var mAlbumFiles = ArrayList<AlbumFile>()
    private var openTimeTimestamp: Long = 0
    private var closeTimeTimestamp: Long = 0
    lateinit var rvCategoryTypes: RecyclerView
    lateinit var tvNoCategory: TextView
    lateinit var categoryTypesAdapter: CategoryTypesEditShopAdapter
    private var getTypesOfCategoryList: ArrayList<CategoryListResponse.Body> = ArrayList()
    private val viewModel: AuthViewModel
            by lazy { ViewModelProviders.of(this).get(AuthViewModel::class.java) }

    override fun getContentId(): Int {
        return R.layout.activity_my_shop_edit
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this


        ivBack.setOnClickListener(mContext)
        btnSubmit.setOnClickListener(mContext)
        tv_category_name.setOnClickListener(mContext)
        tv_open_time.setOnClickListener(mContext)
        ivAdd.setOnClickListener(mContext)
        tv_close_time.setOnClickListener(mContext)


        if (intent != null){
            shopName= intent.getStringExtra("shopName")!!
            shopCategory= intent.getStringExtra("shopCategory")!!
            shopABN= intent.getStringExtra("shopABN")!!
          //  shopAddress= intent.getStringExtra("shopAddress")!!
            shopBuildingNumber= intent.getStringExtra("shopBuildingNumber")!!
            shopStreetNumber= intent.getStringExtra("shopStreetNumber")!!
            shopCity= intent.getStringExtra("shopCity")!!
            shopState= intent.getStringExtra("shopState")!!
            shopCountry= intent.getStringExtra("shopCountry")!!
            shopPostCode= intent.getStringExtra("shopPostCode")!!
            shopImage= intent.getStringExtra("shopImage")!!
            openTime= intent.getStringExtra("openTime")!!
            closeTime= intent.getStringExtra("closeTime")!!

            vendorDeliveryOptionsList = intent.getParcelableArrayListExtra<VendorDeliveryOption>("vendorDeliveryOptions") as ArrayList<VendorDeliveryOption>
            vendorDeliveryChargesList = intent.getParcelableArrayListExtra<VendorDeliveryCharge>("vendorDeliveryCharges") as ArrayList<VendorDeliveryCharge>

            et_shop_name.setText(shopName)
            tv_category_name.setText(shopCategory)
            et_shop_abn.setText(shopABN)
            et_shop_building_number.setText(shopBuildingNumber)
            et_shop_street_number.setText(shopStreetNumber)
            et_shop_city.setText(shopCity)
            et_shop_state.setText(shopState)
            et_shop_country.setText(shopCountry)
            et_shop_postal_code.setText(shopPostCode)
            tv_open_time.setText(openTime)
            tv_close_time.setText(closeTime)

            Glide.with(mContext).load(shopImage).error(R.drawable.ic_image_placeholder).into(iv_shop_image)

            openTimeTimestamp = (CommonMethods.time_to_timestamp(tv_open_time.text.toString(), "hh:mm a"))
            closeTimeTimestamp = (CommonMethods.time_to_timestamp(tv_close_time.text.toString(), "hh:mm a"))

        }
    }

    fun timee(type: String) {
        val cal = Calendar.getInstance()
        val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)

            //it's after current
            if (type.equals("open")) {
                tv_open_time.text = SimpleDateFormat("hh:mm a").format(cal.time)
                openTimeTimestamp = (CommonMethods.time_to_timestamp(tv_open_time.text.toString(), "hh:mm a"))
                Log.e("startTimeTimestamp", openTimeTimestamp.toString())

            }
            else {
                closeTimeTimestamp = (CommonMethods.time_to_timestamp(SimpleDateFormat("hh:mm a").format(cal.time), "hh:mm a"))
                if (closeTimeTimestamp >= openTimeTimestamp) {
                    tv_close_time.text = SimpleDateFormat("hh:mm a").format(cal.time)
                    Log.e("endTimeTimestamp", closeTimeTimestamp.toString())

                } else {
                    //it's before current'
                    CommonMethods.AlertErrorMessage(mContext, "Invalid Time")
                }

            }

        }
        TimePickerDialog(mContext, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
    }

    private fun selectAlbum() {
        Album.image(this)
                .singleChoice()
                .columnCount(4)
                .camera(true)
                .widget(
                        Widget.newDarkBuilder(this)
                                .title(getString(R.string.app_name))
                                .build()
                )
                .onResult { result ->
                    //1 image 2 video
                    mAlbumFiles = result
                    for (i in 0 until mAlbumFiles.size) {
                        Log.e("imagePath", mAlbumFiles.get(i).path)
                        mImagePath = mAlbumFiles.get(i).path
                        Glide.with(mContext).load(mImagePath).into(iv_shop_image)

                    }
                }
                .onCancel {}
                .start()
    }


    private fun categoryTypesDailogMethod() {
        categoryTypesDialog = Dialog(mContext!!, R.style.Theme_Dialog)
        categoryTypesDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        categoryTypesDialog.setContentView(R.layout.category_types_alert)
        rvCategoryTypes = categoryTypesDialog.findViewById(R.id.rv_category_types)
        tvNoCategory = categoryTypesDialog.findViewById(R.id.tv_no_category_types)
        categoryTypesDialog.window!!.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
        )
        categoryTypesDialog.setCancelable(true)
        categoryTypesDialog.setCanceledOnTouchOutside(true)
        categoryTypesDialog.window!!.setGravity(Gravity.CENTER)

        categoryTypesDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        if (getTypesOfCategoryList.size == 0) {
            tvNoCategory.visibility = View.VISIBLE
            rvCategoryTypes.visibility = View.GONE
        } else {
            tvNoCategory.visibility = View.GONE
            rvCategoryTypes.visibility = View.VISIBLE

            categoryTypesAdapter = CategoryTypesEditShopAdapter(mContext, getTypesOfCategoryList, mContext, mContext)
            rvCategoryTypes.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
            rvCategoryTypes.adapter = categoryTypesAdapter

        }
        categoryTypesDialog.show()
    }

    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {
                if (it.data is CategoryListResponse) {
                    val categoryListResponse: CategoryListResponse = it.data
                    if (categoryListResponse.getCode() == Constants.success_code) {
                        getTypesOfCategoryList.clear()
                        getTypesOfCategoryList.addAll(categoryListResponse.body)
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

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.ivBack -> {
                onLeftIconClick()
            }
            R.id.btnUpdate -> {
                val intent = Intent(mContext, MyShopEditActivity::class.java)
                intent.putExtra("shopName", shopName)
                intent.putExtra("shopCategory", shopCategory)
                intent.putExtra("shopABN", shopABN)
                intent.putExtra("shopAddress", shopAddress)
                intent.putExtra("shopBuildingNumber", shopBuildingNumber)
                intent.putExtra("shopStreetNumber", shopStreetNumber)
                intent.putExtra("shopPostCode", shopPostCode)
                intent.putExtra("shopImage", shopImage)
                startActivity(intent)
            }

            R.id.tv_open_time -> {
                timee("open")
            }
            R.id.tv_close_time -> {
                timee("close")
            }
            R.id.ivAdd -> {
                mAlbumFiles = ArrayList()
                selectAlbum()
            }
            R.id.tv_category_name -> {
                categoryTypesDailogMethod()
            }
        }

}

    override fun categoryTypeclick(pos: Int, typeId: String, name: String) {
        categoryTypeId = typeId
        tv_category_name.setText(name)
        categoryTypesDialog.dismiss()    }
}
