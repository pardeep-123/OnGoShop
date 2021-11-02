package com.ongoshop.activities


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.ongoshop.R
import com.ongoshop.base.BaseActivity
import com.ongoshop.manager.restApi.RestObservable
import com.ongoshop.manager.restApi.Status
import com.ongoshop.pojo.EditProfileAddShopResponsess
import com.ongoshop.utils.others.CommonMethods
import com.ongoshop.utils.others.Constants
import com.ongoshop.utils.others.MyApplication
import com.ongoshop.utils.others.SharedPrefUtil
import com.ongoshop.viewmodel.SettingsViewModel
import com.rilixtech.widget.countrycodepicker.Country
import com.rilixtech.widget.countrycodepicker.CountryCodePicker
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumFile
import com.yanzhenjie.album.api.widget.Widget
import kotlinx.android.synthetic.main.activity_create_account.*
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.activity_edit_profile.ccp
import kotlinx.android.synthetic.main.activity_edit_profile.et_email
import kotlinx.android.synthetic.main.activity_edit_profile.et_name
import kotlinx.android.synthetic.main.activity_edit_profile.et_phone
import okhttp3.RequestBody
import java.util.*
import kotlin.collections.ArrayList


class EditProfileActivity : BaseActivity(), View.OnClickListener, Observer<RestObservable> {

    private lateinit var mContext: EditProfileActivity
    private var userId=""
    private var defaultImage=""
    var countryCode = ""


    var mImagePath =""
    private var mAlbumFiles = ArrayList<AlbumFile>()
    private var fusedLocationClient: FusedLocationProviderClient? = null
    private val AUTOCOMPLETE_REQUEST_CODE = 1

    private var latitude = 0.0
    private var longitude = 0.0
    private var latLng: com.google.android.gms.maps.model.LatLng? = null
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
            tv_addres.setText(intent.getStringExtra("shopAddress"))
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
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        /**
         *@author Pardeep Sharma
         *  Created on 02 November 2021
         *  function for select the location from map
         */
        val apiKey = getString(R.string.api_key_map)
        Places.initialize(this, apiKey)
        //set On click listener on location EditText
        tv_addres.setOnClickListener {
            // Set the fields to specify which types of place data to
// return after the user has made a selection.
            val fields = listOf(
                Place.Field.ID,
                Place.Field.NAME,
                Place.Field.LAT_LNG,
                Place.Field.ADDRESS_COMPONENTS,
                Place.Field.ADDRESS
            )

// Start the autocomplete intent.
            val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                .build(this)
            startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE)
        }

        /**
         *  Ends here
         */
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
            // send latitude longitude and location name
            val partLatitude = mValidationClass.createPartFromString(latitude.toString())
            val partLongitude = mValidationClass.createPartFromString(longitude.toString())
            val partAddress = mValidationClass.createPartFromString(tv_addres.text.toString().trim())

            val map = HashMap<String, RequestBody>()
            map.put("deviceType", partdeviceType)
            map.put("deviceToken", partdeviceToken)
            map.put("name", partName)
            map.put("email", partEmail)
            map.put("phone", partPhone)
            map["countryCode"] = partCountryCode
            map["latitude"] = partLatitude
            map["longitude"] = partLongitude
            map["geoLocation"] = partAddress
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
                    defaultImage = mImagePath

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
                        showSuccessToast(mContext, editProfileResponse.getMessage()!!)
                       // SharedPrefUtil.getInstance().saveName(editProfileResponse.getBody()!!.name)
                      //  SharedPrefUtil.getInstance().saveImage(editProfileResponse.getBody()..image)
                      //  SharedPrefUtil.getInstance().saveImage(editProfileResponse.getBody()!!.image)
                      //  MyApplication.getnstance().setString(Constants.AuthKey, editProfileResponse.getBody()!!.token!!)
                        onBackPressed()

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            when (resultCode) {
                Activity.RESULT_OK -> {

                    data?.let {
                        val place = Autocomplete.getPlaceFromIntent(data)
                        Log.d(
                            "locationNew",
                            "Place: ${place.name}, ${place.id}, ${place.addressComponents}, ${place.address}"
                        )

                        tv_addres.setText(place.name.toString())

                        latLng = place.latLng
                        latitude = latLng!!.latitude
                        longitude = latLng!!.longitude
                    }
                }

                AutocompleteActivity.RESULT_ERROR -> {
                    data?.let {
                        val status = Autocomplete.getStatusFromIntent(data)
                        Log.i("TAG", status.statusMessage!!)
                    }
                }

                Activity.RESULT_CANCELED -> {
// The user canceled the operation.
                }
            }
        }
    }
}
