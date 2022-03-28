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
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.SpinnerAdapter
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ongoshop.R
import com.ongoshop.adapter.CategoryTypesAddShopAdapter
import com.ongoshop.adapter.DeliveryOptionsAdapter
import com.ongoshop.base.BaseActivity
import com.ongoshop.manager.restApi.RestObservable
import com.ongoshop.manager.restApi.Status
import com.ongoshop.pojo.CategoryListResponse
import com.ongoshop.pojo.SignupResponsess
import com.ongoshop.pojo.VendorDeliveryCharge
import com.ongoshop.pojo.VendorDeliveryOption
import com.ongoshop.utils.helperclasses.CategoryTypesClicklisetener
import com.ongoshop.utils.others.CommonMethods
import com.ongoshop.utils.others.Constants
import com.ongoshop.utils.others.SharedPrefUtil
import com.ongoshop.viewmodel.AuthViewModel
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumFile
import com.yanzhenjie.album.api.widget.Widget
import kotlinx.android.synthetic.main.activity_complete_profile.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class AddShopActivity : BaseActivity(), View.OnClickListener,  Observer<RestObservable>, CategoryTypesClicklisetener {

    lateinit var mContext: AddShopActivity
    private var mImagePath = ""
    private var categoryTypeId = ""
    private var mAlbumFiles = ArrayList<AlbumFile>()
    private var openTimeTimestamp: Long = 0
    private var closeTimeTimestamp: Long = 0
    lateinit var categoryTypesDialog: Dialog
    lateinit var rvCategoryTypes: RecyclerView
    lateinit var tvNoCategory: TextView
    lateinit var categoryTypesAdapter: CategoryTypesAddShopAdapter
    private var getTypesOfCategoryList: ArrayList<CategoryListResponse.Body> = ArrayList()
    private val viewModel: AuthViewModel
            by lazy { ViewModelProviders.of(this).get(AuthViewModel::class.java) }
    private var vendorDeliveryOptionsList: ArrayList<VendorDeliveryOption>? = ArrayList()
    private var vendorDeliveryChargesList: ArrayList<VendorDeliveryCharge>? = ArrayList()
    var statearrays = arrayOf("QLD", "NSW", "ACT", "VIC", "WA", "SA", "TAS", "NT")

    override fun getContentId(): Int {
        return R.layout.activity_complete_profile
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this

        ivBack.setOnClickListener(mContext)
        btnSubmit.setOnClickListener(mContext)
        ivcamera.setOnClickListener(mContext)
        tv_open_time.setOnClickListener(mContext)
        tv_close_time.setOnClickListener(mContext)
        tv_category_name.setOnClickListener(mContext)
        statespinner.adapter = ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,statearrays)


        getCategoryAPI()

        if (intent.extras !=null){
            vendorDeliveryOptionsList = intent.getParcelableArrayListExtra<VendorDeliveryOption>("vendorDeliveryOptions") as ArrayList<VendorDeliveryOption>
            vendorDeliveryChargesList = intent.getParcelableArrayListExtra<VendorDeliveryCharge>("vendorDeliveryCharges") as ArrayList<VendorDeliveryCharge>
        }
    }

    private fun getCategoryAPI() {
        if (!mValidationClass.isNetworkConnected) {
            showAlerterRed(resources.getString(R.string.no_internet))
        } else {
            val map = HashMap<String, String>()
            map["searchKeyword"] = ""
          //  viewModel.categoryListApi(this, true, map)
            viewModel.categoryListApi(this, true)
            viewModel.mResponse.observe(this, this)

        }
    }


    private fun categoryTypesDailogMethod() {
        categoryTypesDialog = Dialog(mContext, R.style.Theme_Dialog)
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

            categoryTypesAdapter = CategoryTypesAddShopAdapter(mContext, getTypesOfCategoryList, mContext, mContext)
            rvCategoryTypes.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
            rvCategoryTypes.adapter = categoryTypesAdapter

        }
        categoryTypesDialog.show()
    }


    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.ivBack -> {
                finishAffinity()
            }
            R.id.tv_open_time -> {
                timee("open")
            }
            R.id.tv_close_time -> {
                timee("close")
            }
            R.id.btnSubmit -> {
                if (isValid()) {
                    val intent = Intent(mContext, HomeDeliveryOptionsActivity::class.java)
                    intent.putExtra("shopName", et_shop_name.text.toString().trim())
                    intent.putExtra("categoryName", tv_category_name.text.toString().trim())
                    intent.putExtra("shop_category_id", categoryTypeId)
                    intent.putExtra("shopABN", et_shop_abn.text.toString().trim())
                    intent.putExtra("buildingNumber", et_shop_building_number.text.toString().trim())
                    intent.putExtra("streetNumber", et_shop_street_number.text.toString().trim())
                    intent.putExtra("city", et_shop_city.text.toString().trim())
                    intent.putExtra("state", statespinner.selectedItem.toString().trim())
                    intent.putExtra("country", et_shop_country.text.toString().trim())
                    intent.putExtra("postalCode", et_shop_postal_code.text.toString().trim())
                    intent.putExtra("openTime", tv_open_time.text.toString().trim())
                    intent.putExtra("closeTime", tv_close_time.text.toString().trim())
                    intent.putExtra("shopImage", mImagePath)
                    intent.putParcelableArrayListExtra("vendorDeliveryOptions", vendorDeliveryOptionsList)
                    Log.e("AddShopSize", vendorDeliveryOptionsList!!.size.toString())
                    intent.putParcelableArrayListExtra("vendorDeliveryCharges", vendorDeliveryChargesList)
                    Log.e("AddShopSizeCharges", vendorDeliveryChargesList!!.size.toString())
                    startActivity(intent)
                }
            }
            R.id.ivcamera -> {
                mAlbumFiles = ArrayList()
                selectAlbum()
            }
            R.id.tv_category_name -> {
                categoryTypesDailogMethod()
            }
        }
    }

    fun timee(type: String) {
        val cal = Calendar.getInstance()
        val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)

            //it's after current
            if (type == "open") {
                tv_open_time.text = SimpleDateFormat("hh:mm a").format(cal.time)
                SharedPrefUtil.getInstance().saveString(Constants.openTime,tv_open_time.text.toString())
                openTimeTimestamp = (CommonMethods.time_to_timestamp(tv_open_time.text.toString(), "hh:mm a"))
                Log.e("startTimeTimestamp", openTimeTimestamp.toString())

            } else {
                closeTimeTimestamp = (CommonMethods.time_to_timestamp(SimpleDateFormat("hh:mm a").format(cal.time), "hh:mm a"))
                if (closeTimeTimestamp >= openTimeTimestamp) {
                    tv_close_time.text = SimpleDateFormat("hh:mm a").format(cal.time)
                    SharedPrefUtil.getInstance().saveString(Constants.closeTime,tv_close_time.text.toString())
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
                        Log.e("imagePath", mAlbumFiles[i].path)
                        mImagePath = mAlbumFiles[i].path
                        Glide.with(mContext).load(mImagePath).into(ivImg)

                    }
                }
                .onCancel {}
                .start()
    }

    private fun isValid(): Boolean {
        var check = false
        if (!mValidationClass.isNetworkConnected)
            showAlerterRed(resources.getString(R.string.no_internet))
        else if (mValidationClass.checkStringNull(et_shop_name.text.toString().trim()))
            showAlerterRed(resources.getString(R.string.error_business_name))
        else if (mValidationClass.checkStringNull(tv_category_name.text.toString().trim()))
            showAlerterRed(resources.getString(R.string.error_category))
        else if (mValidationClass.checkStringNull(et_shop_abn.text.toString().trim()))
            showAlerterRed(resources.getString(R.string.error_abn))
       /* else if (mValidationClass.checkStringNull(et_shop_building_number.text.toString().trim()))
            showAlerterRed(resources.getString(R.string.error_building_number))*/
        else if (mValidationClass.checkStringNull(et_shop_street_number.text.toString().trim()))
            showAlerterRed(resources.getString(R.string.error_street_number))
        else if (mValidationClass.checkStringNull(et_shop_city.text.toString().trim()))
            showAlerterRed(resources.getString(R.string.error_city))
       /* else if (mValidationClass.checkStringNull(et_shop_state.text.toString().trim()))
            showAlerterRed(resources.getString(R.string.error_state))*/
        else if (mValidationClass.checkStringNull(et_shop_postal_code.text.toString().trim()))
            showAlerterRed(resources.getString(R.string.error_postal_code))
        else if (mValidationClass.checkStringNull(tv_open_time.text.toString().trim()))
            showAlerterRed(resources.getString(R.string.error_open_time))
        else if (mValidationClass.checkStringNull(tv_close_time.text.toString().trim()))
            showAlerterRed(resources.getString(R.string.error_close_time))
        else if (mValidationClass.checkStringNull(mImagePath))
            showAlerterRed(resources.getString(R.string.error_image))
        else
            check = true
        return check
    }

    override fun categoryTypeclick(pos: Int, typeId: String, name: String) {
        categoryTypeId = typeId
        tv_category_name.text = name
        categoryTypesDialog.dismiss()

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

}
