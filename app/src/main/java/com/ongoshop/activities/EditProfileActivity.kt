package com.ongoshop.activities

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.text.InputFilter
import android.util.Log
import android.view.Gravity

import android.view.View

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.bumptech.glide.Glide
import com.rilixtech.widget.countrycodepicker.Country
import com.rilixtech.widget.countrycodepicker.CountryCodePicker
import com.ongoshop.R

import com.ongoshop.base.BaseActivity
import com.ongoshop.manager.restApi.RestObservable
import com.ongoshop.manager.restApi.Status
import com.ongoshop.pojo.EditProfileAddShopResponsess

import com.ongoshop.utils.others.CommonMethods
import com.ongoshop.utils.others.Constants
import com.ongoshop.utils.others.SharedPrefUtil

import com.ongoshop.pojo.EditProfileResponse
import com.ongoshop.utils.others.MyApplication
import com.ongoshop.viewmodel.SettingsViewModel

import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumFile
import com.yanzhenjie.album.api.widget.Widget


import kotlinx.android.synthetic.main.activity_edit_profile.*

import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.util.HashMap


class EditProfileActivity : BaseActivity(), View.OnClickListener, Observer<RestObservable> {

    private lateinit var mContext: EditProfileActivity
    private var userId=""
    private var defaultImage=""
    var countryCode = ""


    var mImagePath =""
    private var mAlbumFiles = ArrayList<AlbumFile>()

    private val viewModel: SettingsViewModel
            by lazy { ViewModelProviders.of(this).get(SettingsViewModel::class.java) }

    override fun getContentId(): Int {
        return R.layout.activity_edit_profile
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    fun init() {
        mContext = this

        ivBack.setOnClickListener(mContext)
        btnUpdate.setOnClickListener(mContext)
        ivAdd.setOnClickListener(mContext)

        if (intent !=null){
            userId= intent.getStringExtra("userId")!!
            countryCode= intent.getStringExtra("countryCode")!!



            try {
                if (countryCode.equals("1") && intent.getStringExtra("country").equals("United States")){
                    ccp.defaultCountryCode
                }else {
                    ccp.setCountryForPhoneCode(countryCode.toInt())
                }
            } catch (e: Exception) {

            }

            et_name.setText(intent.getStringExtra("name"))
            et_email.setText(intent.getStringExtra("email"))
            et_phone.setText(intent.getStringExtra("phone"))
            if (!intent.getStringExtra("image")!!.isEmpty()) {
                defaultImage = intent.getStringExtra("image").toString()
                Glide.with(mContext).load(defaultImage).error(R.mipmap.no_image_placeholder).into(ivProfile)
            }

        }


        ccp.setOnCountryChangeListener(object : CountryCodePicker.OnCountryChangeListener {
            override  fun onCountrySelected(selectedCountry: Country?) {
                ccp.getSelectedCountryCode()
                ccp.enableHint(true)
                ccp.setKeyboardAutoPopOnSearch(false)
                countryCode =  ccp.getSelectedCountryCode()
             }
        })

    }


    private fun editProfileApi() {
        if (isValid()) {
           // val bodyimage = mValidationClass.prepareFilePart("image", File(mImagePath))
            val partdeviceType = mValidationClass.createPartFromString(Constants.Device_Type)
            val partdeviceToken = mValidationClass.createPartFromString(SharedPrefUtil.getInstance().deviceToken)
            val partName = mValidationClass.createPartFromString(et_name.text.toString().trim())
            val partEmail = mValidationClass.createPartFromString(et_email.text.toString().trim())
            val partPhone = mValidationClass.createPartFromString(et_phone.text.toString().trim())
            val partCountryCode = mValidationClass.createPartFromString(countryCode)

            val map = HashMap<String, RequestBody>()
            map.put("deviceType", partdeviceType)
            map.put("deviceToken", partdeviceToken)
            map.put("name", partName)
            map.put("email", partEmail)
            map.put("phone", partPhone)
            map.put("countryCode", partCountryCode)

            viewModel.editProfile(this, true, map, mImagePath,mValidationClass)
            viewModel.mResponse.observe(this, this)

        }
    }


    private fun isValid(): Boolean {
        var check = false
        if (!mValidationClass.isNetworkConnected)
            showAlerterRed(resources.getString(R.string.no_internet))
        else if (mValidationClass.checkStringNull(et_name.text.toString().trim()))
            showAlerterRed(resources.getString(R.string.error_name))
        else if (mValidationClass.checkStringNull(defaultImage))
            showAlerterRed(resources.getString(R.string.error_image))
        else if (mValidationClass.checkStringNull(countryCode))
            showAlerterRed(resources.getString(R.string.error_country_code))
        else if (mValidationClass.checkStringNull(et_phone.text.toString().trim()))
            showAlerterRed(resources.getString(R.string.error_phone_number))
        else if (!mValidationClass.validatePhoneNumber(et_phone.text.toString().trim()))
            showAlerterRed(resources.getString(R.string.error_invalid_phone_number))
        else if (mValidationClass.checkStringNull(et_email.text.toString().trim()))
            showAlerterRed(resources.getString(R.string.error_email))
        else if (!mValidationClass.isValidEmail(et_email.text.toString().trim()))
            showAlerterRed(resources.getString(R.string.error_validemail))
        else
            check = true
        return check
    }


    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.ivBack -> {
                onLeftIconClick()
            }
            R.id.ivAdd -> {
                mAlbumFiles = ArrayList()
                selectAlbum()
            }
            R.id.btnUpdate -> {
                hideKeyboard(btnUpdate)
                editProfileApi()
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
                for ( i in 0 until  mAlbumFiles.size) {
                    Log.e("imagePath",mAlbumFiles.get(i).path)
                    mImagePath=mAlbumFiles.get(i).path
                    Glide.with(mContext).load(mImagePath).into(ivProfile)

                }
            }
            .onCancel {}
            .start()
    }


    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {
                if (it.data is EditProfileAddShopResponsess) {
                    val editProfileResponse: EditProfileAddShopResponsess = it.data
                    if (editProfileResponse.getCode()!!.equals(Constants.success_code)) {
                        showSuccessToast(mContext, editProfileResponse!!.getMessage()!!)
                        SharedPrefUtil.getInstance().saveName(editProfileResponse.getBody()!!.name)
                        SharedPrefUtil.getInstance().saveImage(editProfileResponse.getBody()!!.image)
                        SharedPrefUtil.getInstance().saveImage(editProfileResponse.getBody()!!.image)
                        MyApplication.getnstance().setString(Constants.AuthKey, editProfileResponse.getBody()!!.token!!)
                        finish()

                    } else {
                        CommonMethods.AlertErrorMessage(mContext, editProfileResponse.getMessage())
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
