package com.ongoshop.activities

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.pdf.PdfDocument
import android.os.Bundle
import android.text.InputFilter
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.ongoshop.R
import com.ongoshop.base.BaseActivity
import com.ongoshop.manager.restApi.RestObservable
import com.ongoshop.manager.restApi.Status
import com.ongoshop.pojo.AddProductResponse
import com.ongoshop.pojo.CheckBarCodeAvailabilityResponse
import com.ongoshop.utils.others.CommonMethods
import com.ongoshop.utils.others.Constants
import com.ongoshop.utils.others.DecimalDigitsInputFilter
import com.ongoshop.viewmodel.HomeViewModel
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumFile
import com.yanzhenjie.album.api.widget.Widget
import kotlinx.android.synthetic.main.activity_add_product.*
import kotlinx.android.synthetic.main.activity_add_product.ivBack
import kotlinx.android.synthetic.main.activity_complete_profile.*
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.activity_product_detail.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.util.*
import kotlin.collections.ArrayList


class AddProductActivity : BaseActivity(), View.OnClickListener, Observer<RestObservable> {
    var btnProceed: Button? = null
    var mContext: AddProductActivity? = null
    var ivOn: ImageView? = null
    var ivOff: ImageView? = null
    var ll_chackbox: LinearLayout? = null
    var temp = 2
    private var isBarcode = "1"
    private var weightUnit = ""
    var ivImg: ImageView? = null
    var ivcamera: ImageView? = null
    var categoryId: String? = null
    private lateinit var weightUnitDialog: Dialog
    private var isCheckAvailability = false

    private val viewModel: HomeViewModel
            by lazy { ViewModelProviders.of(this).get(HomeViewModel::class.java) }

    var mImagePath = ""
    private var mAlbumFiles = ArrayList<AlbumFile>()

    override fun getContentId(): Int {
        return R.layout.activity_add_product
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this

        ivImg = findViewById(R.id.ivImg)
        ivcamera = findViewById(R.id.ivcamera)
        ivOn = findViewById(R.id.ivOn)
        ivOff = findViewById(R.id.ivOff)
        ll_chackbox = findViewById(R.id.ll_chackbox)
        btnProceed = findViewById(R.id.btnProceed)

        ivOn!!.setOnClickListener(mContext)
        ivOff!!.setOnClickListener(mContext)
        ivcamera!!.setOnClickListener(mContext)
        btnProceed!!.setOnClickListener(mContext)
        ivBack.setOnClickListener(mContext)
        tv_measurement_unit.setOnClickListener(mContext)
        tv_check_availability.setOnClickListener(mContext)

        if (intent.extras != null) {
            if (intent.getStringExtra("from").equals("BarcodeScan")){

                for (i in 0 until ll.childCount)
                {
                    if (ll.getChildAt(i) is EditText){
                        var edt:EditText =  ll.getChildAt(i) as EditText
                        edt.isEnabled = false
                    }

                }
                et_bar_code.isEnabled = false
                ivOff?.isEnabled = false
                et_price?.isEnabled = true
                et_gtin_number?.isEnabled = true
                et_origin_country?.isEnabled = true
                isBarcode = "0"
                categoryId = intent.getStringExtra("categoryId")
                et_product_name.setText(intent.getStringExtra("name"))
                et_brand_Name.setText(intent.getStringExtra("brandName"))
                et_bar_code.setText(intent.getStringExtra("barcode"))
                et_price.setText(intent.getStringExtra("mrp"))
                et_gtin_number.setText(intent.getStringExtra("gtinNumber"))
                et_origin_country.setText(intent.getStringExtra("countryOfOrigin"))
                et_description.setText(intent.getStringExtra("description"))
                et_weight.setText(intent.getStringExtra("weight"))
                et_origin_country.setText(intent.getStringExtra("countryOfOrigin"))
                isBarcode= intent.getStringExtra("isBarcodeItem")!!
                if (intent.getStringExtra("weightUnit").equals("0")){
                    weightUnit = "0"
                    tv_measurement_unit.setText(resources.getString(R.string.pounds))
                }else {
                    weightUnit = "1"
                    tv_measurement_unit.setText(resources.getString(R.string.kilograms))

                }

                Glide.with(mContext!!).load(intent.getStringExtra("image")).error(R.drawable.ic_image_placeholder).into(ivImg!!)


            }else {
                categoryId = intent.getStringExtra("categoryId")
            }

        }

        et_price.filters = arrayOf<InputFilter>(DecimalDigitsInputFilter(5, 2))

    }

    private fun isValid(): Boolean {
        var check = false
        if (!mValidationClass.isNetworkConnected)
            showAlerterRed(resources.getString(R.string.no_internet))
       /* else if (mValidationClass.checkStringNull(mImagePath))
            showAlerterRed(resources.getString(R.string.error_image))*/
        else if (mValidationClass.checkStringNull(et_brand_Name.text.toString().trim()))
            showAlerterRed(resources.getString(R.string.error_brand_name))
        else if (mValidationClass.checkStringNull(et_price.text.toString().trim()))
            showAlerterRed(resources.getString(R.string.error_price))
        else if (mValidationClass.checkStringNull(et_product_name.text.toString().trim()))
            showAlerterRed(resources.getString(R.string.error_product_name))
        else if (mValidationClass.checkStringNull(et_gtin_number.text.toString().trim()))
            showAlerterRed(resources.getString(R.string.error_gtin_number))
        else if (mValidationClass.checkStringNull(et_origin_country.text.toString().trim()))
            showAlerterRed(resources.getString(R.string.error_country_origin))
        else if (mValidationClass.checkStringNull(et_weight.text.toString().trim()))
            showAlerterRed(resources.getString(R.string.error_product_weight))
        else if (mValidationClass.checkStringNull(tv_measurement_unit.text.toString().trim()))
            showAlerterRed(resources.getString(R.string.error_measurement_unit))
        else if (mValidationClass.checkStringNull(et_description.text.toString().trim()))
            showAlerterRed(resources.getString(R.string.error_product_description))
        else
            check = true
        return check
    }


    private fun weightUnitDailogMethod() {
        weightUnitDialog = Dialog(mContext!!, R.style.Theme_Dialog)
        weightUnitDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        weightUnitDialog.setContentView(R.layout.weight_unit_types_alert)
        val tvPound = weightUnitDialog.findViewById(R.id.tv_pound) as TextView
        val tvKilo = weightUnitDialog.findViewById(R.id.tv_kilo) as TextView

        weightUnitDialog.window!!.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
        )

        tvPound.setOnClickListener {
            weightUnit = "0"
            weightUnitDialog.dismiss()
            tv_measurement_unit.setText(tvPound.text.toString())
        }

        tvKilo.setOnClickListener {
            weightUnit = "1"
            weightUnitDialog.dismiss()
            tv_measurement_unit.setText(tvKilo.text.toString())
        }

        weightUnitDialog.setCancelable(true)
        weightUnitDialog.setCanceledOnTouchOutside(true)
        weightUnitDialog.window!!.setGravity(Gravity.CENTER)

        weightUnitDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        weightUnitDialog.show()
    }


    fun checkBarCodeAPI() {
        if (!mValidationClass.isNetworkConnected)
            showAlerterRed(resources.getString(R.string.no_internet))
        else {
            val map = HashMap<String, String>()
            map.put("barcode", et_bar_code.text.toString().trim())

            viewModel.checkBarcodeApi(this, true, map)
            viewModel.mResponse.observe(this, this)
        }
    }


    fun addProductAPI() {
        if (isValid()) {
            if (!mValidationClass.isNetworkConnected) {
                showAlerterRed(resources.getString(R.string.no_internet))
            } else {

                var bodyimage:MultipartBody.Part?=null
              if (mImagePath.isNotEmpty())
                     bodyimage = mValidationClass.prepareFilePart("image", File(mImagePath))


                val partCategoryId = mValidationClass.createPartFromString(categoryId)
                val partProductName = mValidationClass.createPartFromString(et_product_name.text.toString().trim())
                val partProductDescription = mValidationClass.createPartFromString(et_description.text.toString().trim())
                val partCountryOfOrigin = mValidationClass.createPartFromString(et_origin_country.text.toString().trim())
                val partGTINNumber = mValidationClass.createPartFromString(et_gtin_number.text.toString().trim())
                val partBrandName = mValidationClass.createPartFromString(et_brand_Name.text.toString().trim())
                val partMRP = mValidationClass.createPartFromString(et_price.text.toString().trim())
                val partisBarcode = mValidationClass.createPartFromString(isBarcode)
                val partWeightUnit = mValidationClass.createPartFromString(weightUnit)
                val partIsAvailable = mValidationClass.createPartFromString("1")
                val partWideght = mValidationClass.createPartFromString(et_weight.text.toString().trim())
                val partBarCodeNumber = mValidationClass.createPartFromString(et_bar_code.text.toString().trim())

                val map = HashMap<String, RequestBody>()
                map["categoryId"] = partCategoryId
                map["name"] = partProductName
                map["description"] = partProductDescription
                map["countryOfOrigin"] = partCountryOfOrigin
                map["gtinNumber"] = partGTINNumber
                map["brandName"] = partBrandName
                map["mrp"] = partMRP
                map["isBarcodeItem"] = partisBarcode
                map["weightUnit"] = partWeightUnit
                map["isAvailable"] = partIsAvailable
                map["weight"] = partWideght
                map["barcode"] = partBarCodeNumber

                if (mImagePath.isNotEmpty())
                viewModel.addProductApi(this, true, map, bodyimage!!)
                else
                    viewModel.addProductApi(this, true, map)
                viewModel.mResponse.observe(this, this)

            }
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.ivBack -> {
                onLeftIconClick()
            }
            R.id.ivcamera -> {
                mAlbumFiles = ArrayList()
                selectAlbum()
            }
            R.id.ivOff -> {
                ivOff!!.visibility = View.GONE
                ivOn!!.visibility = View.VISIBLE
                isBarcode = "0"
                ll_bar_code.visibility = View.GONE
                nobarcodeitemttxtview.visibility = View.GONE
            }
            R.id.ivOn -> {
                ivOff!!.visibility = View.VISIBLE
                ivOn!!.visibility = View.GONE
                isBarcode = "1"
                ll_bar_code.visibility = View.VISIBLE
                nobarcodeitemttxtview.visibility = View.VISIBLE
            }
            R.id.btnProceed -> {
                if (isBarcode.equals("1")) {
                    if (isCheckAvailability) {
                        if (mValidationClass.checkStringNull(et_bar_code.text.toString().trim())) {
                            showAlerterRed(resources.getString(R.string.please_enter_bar_code))
                        } else {
                            addProductAPI()
                        }
                    } else {
                        showAlerterRed(resources.getString(R.string.please_check_barcode_availablity))
                    }
                } else {
                    addProductAPI()
                }
            }
            R.id.tv_measurement_unit -> {
                weightUnitDailogMethod()
            }
            R.id.tv_check_availability -> {

                checkBarCodeAPI()
            }
        }
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
                        Glide.with(mContext!!).load(mImagePath).into(ivImg!!)

                    }
                }
                .onCancel {}
                .start()
    }


    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {
                if (it.data is AddProductResponse) {
                    val addProductResponse: AddProductResponse = it.data
                    if (addProductResponse.getCode() == Constants.success_code) {
                        showSuccessToast(mContext!!, addProductResponse!!.getMessage()!!)
                        val i = Intent(mContext, HomeActivity::class.java)
                        i.putExtra("type", "my")
                        startActivity(i)
                        //finishAffinity()

                    } else {
                        CommonMethods.AlertErrorMessage(mContext, addProductResponse.getMessage())
                    }
                }
                if (it.data is CheckBarCodeAvailabilityResponse) {
                    val checkBarCodeAvailabilityResponse: CheckBarCodeAvailabilityResponse = it.data
                    if (checkBarCodeAvailabilityResponse.getCode() == Constants.success_code) {
                        isCheckAvailability = true
                        showSuccessToast(mContext!!, checkBarCodeAvailabilityResponse!!.getMessage()!!)

                    } else {
                        CommonMethods.AlertErrorMessage(mContext, checkBarCodeAvailabilityResponse.getMessage())
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