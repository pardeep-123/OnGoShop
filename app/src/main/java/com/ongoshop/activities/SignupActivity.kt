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
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.InstanceIdResult
import com.google.gson.GsonBuilder
import com.ongoshop.R
import com.ongoshop.base.BaseActivity
import com.ongoshop.manager.restApi.RestObservable
import com.ongoshop.manager.restApi.Status
import com.ongoshop.pojo.SignupResponsess
import com.ongoshop.utils.others.Constants
import com.ongoshop.utils.others.MyApplication
import com.ongoshop.utils.others.SharedPrefUtil
import com.ongoshop.viewmodel.AuthViewModel
import com.rilixtech.widget.countrycodepicker.Country
import com.rilixtech.widget.countrycodepicker.CountryCodePicker
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumFile
import com.yanzhenjie.album.api.widget.Widget
import kotlinx.android.synthetic.main.activity_create_account.*
import okhttp3.RequestBody
import java.io.File
import java.util.HashMap


class SignupActivity : BaseActivity(), View.OnClickListener, Observer<RestObservable> {

    private val viewModel: AuthViewModel
            by lazy { ViewModelProviders.of(this).get(AuthViewModel::class.java) }
    var mImagePath = ""
    private var mAlbumFiles = ArrayList<AlbumFile>()


    lateinit var mContext: SignupActivity
    var countryCode = "91"
    //  var countryCodeName = "US"

    var isClick = ""

    private var fusedLocationClient: FusedLocationProviderClient? = null
    private val AUTOCOMPLETE_REQUEST_CODE = 1

    private var latitude = 0.0
    private var longitude = 0.0
    private var latLng: com.google.android.gms.maps.model.LatLng? = null

    override fun getContentId(): Int {
        return R.layout.activity_create_account
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this


        rlAdd.setOnClickListener(mContext)
        ivBacksignup.setOnClickListener(this)
        ivOn.setOnClickListener(mContext)
        ivOff.setOnClickListener(mContext)
        tvTerms.setOnClickListener(mContext)
        tvSignin.setOnClickListener(mContext)
        btnVerification.setOnClickListener(mContext)


        ccp.setOnCountryChangeListener {
            ccp.selectedCountryCode
            ccp.showFlag(false)
            ccp.enableHint(true)
            ccp.setKeyboardAutoPopOnSearch(false)
            countryCode = ccp.selectedCountryCode
        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        /**
         *@author Pardeep Sharma
         *  Created on 24 August 2021
         *  function for select the location from map
         */
        val apiKey = getString(R.string.api_key_map)
        Places.initialize(this, apiKey)
        //set On click listener on location EditText
        et_address.setOnClickListener {
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
                        Glide.with(mContext).load(mImagePath).into(iv_profile)

                    }
                }
                .onCancel {}
                .start()
    }


    private fun signUpApi() {
        if (isValid()) {
            val bodyimage = mValidationClass.prepareFilePart("image", File(mImagePath))
            val partRole = mValidationClass.createPartFromString(Constants.TYPE_USER)
            val partEmail = mValidationClass.createPartFromString(et_email.text.toString().trim())
            val partName = mValidationClass.createPartFromString(et_name.text.toString().trim())
            val partCountryCode = mValidationClass.createPartFromString(countryCode)
            val partPassword = mValidationClass.createPartFromString(edPass.text.toString().trim())
            val partPhoneNumber = mValidationClass.createPartFromString(et_phone.text.toString().trim())
            val partLatitude = mValidationClass.createPartFromString(latitude.toString())
            val partLongitude = mValidationClass.createPartFromString(longitude.toString())
            val partAddress = mValidationClass.createPartFromString(et_address.text.toString().trim())
            /*     val is_accept = mValidationClass.createPartFromString(isClick)
            */
            val partToken = mValidationClass.createPartFromString(SharedPrefUtil.getInstance().deviceToken)
            val partType = mValidationClass.createPartFromString(Constants.Device_Type)

            val map = HashMap<String, RequestBody>()
            map.put("name", partName)
            map.put("role", partRole)
            map.put("email", partEmail)
            map.put("countryCode", partCountryCode)
            map.put("phone", partPhoneNumber)
            map.put("password", partPassword)
            map.put("latitude", partLatitude)
            map.put("longitude", partLongitude)
            map.put("geoLocation", partAddress)
            map.put("deviceType", partType)
            map.put("deviceToken", partToken)
            /*  map.put("is_accept", is_accept)
*/

            // map.put("social_id", partSocialId)
            // map.put("social_type", partSocialType)

            viewModel.signUpApi(this, true, map, bodyimage)
            viewModel.mResponse.observe(this, this)


        }

    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnVerification -> {
                FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener(
                        this
                ) { instanceIdResult: InstanceIdResult ->
                    val newToken = instanceIdResult.token
                    Log.e("newToken", newToken)
                    SharedPrefUtil.getInstance().saveDeviceToken(newToken)
                }

                Log.e("getDeviceToken__", SharedPrefUtil.getInstance().deviceToken)

                if (SharedPrefUtil.getInstance().deviceToken.equals("") || SharedPrefUtil.getInstance().deviceToken == null) {
                    Log.e("getDeviceToken__", SharedPrefUtil.getInstance().deviceToken)
                    showErrorToast(mContext, "Device Token Not Found")
                } else {
                    signUpApi()
                    hideKeyboard(btnVerification)
                }

            }
            R.id.tvSignin -> {
                val intent = Intent(mContext, LoginActivity::class.java)
                startActivity(intent)
                finishAffinity()
            }
            R.id.tvTerms -> {
                val intent = Intent(mContext, TermsConditionsActivity::class.java)
                startActivity(intent)
            }
            R.id.rlAdd -> {
                mAlbumFiles = ArrayList()
                selectAlbum()
            }

            R.id.ivOn -> {
                ivOff.visibility = View.VISIBLE
                ivOn.visibility = View.GONE
                isClick = ""
            }

            R.id.ivBacksignup->{
                onBackPressed()
            }
            R.id.ivOff -> {
                ivOff.visibility = View.GONE
                ivOn.visibility = View.VISIBLE
                isClick = "1"

            }
        }
    }



    private fun isValid(): Boolean {
        var check = false
        if (!mValidationClass.isNetworkConnected)
            showAlerterRed(resources.getString(R.string.no_internet))
        else if (mValidationClass.checkStringNull(mImagePath))
            showAlerterRed(resources.getString(R.string.error_image))
        else if (mValidationClass.checkStringNull(et_name.text.toString().trim()))
            showAlerterRed(resources.getString(R.string.error_name))
        else if (mValidationClass.checkStringNull(et_email.text.toString().trim()))
            showAlerterRed(resources.getString(R.string.error_email))
        else if (!mValidationClass.isValidEmail(et_email.text.toString().trim()))
            showAlerterRed(resources.getString(R.string.error_validemail))
        else if (mValidationClass.checkStringNull(countryCode))
            showAlerterRed(resources.getString(R.string.error_country_code))
        else if (mValidationClass.checkStringNull(et_phone.text.toString().trim()))
            showAlerterRed(resources.getString(R.string.error_phone_number))
        else if (!mValidationClass.validatePhoneNumber(et_phone.text.toString().trim()))
            showAlerterRed(resources.getString(R.string.error_invalid_phone_number))
        else if (mValidationClass.checkStringNull(edPass.text.toString().trim()))
            showAlerterRed(resources.getString(R.string.error_password))
        else if (edPass.text.toString().length < 6)
            showAlerterRed(resources.getString(R.string.error_password_length))
        else if (mValidationClass.checkStringNull(et_cPass.text.toString().trim()))
            showAlerterRed(resources.getString(R.string.error_cpassword))
        else if (et_cPass.text.toString().length < 6)
            showAlerterRed(resources.getString(R.string.error_cpassword_length))
        else if (!et_cPass.text.toString().equals(edPass.text.toString()))
            showAlerterRed(resources.getString(R.string.error_password_not_matched))
        else if (mValidationClass.checkStringNull(et_address.text.toString()))
            showAlerterRed(resources.getString(R.string.choose_location))
        else if (isClick.equals("") || isClick.isEmpty())
            showAlerterRed(resources.getString(R.string.error_terms_conditions))
        else
            check = true
        return check
    }

    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {
                if (it.data is SignupResponsess) {
                    val registerResponse: SignupResponsess = it.data
                    if (registerResponse.code == Constants.success_code) {
                        //showToast(registerResponse.getMessage()!!)

                        MyApplication.getnstance()
                                .setString(
                                        Constants.AuthKey,
                                        registerResponse.getBody()!!.token!!
                                )
                        MyApplication.instance!!.setString(
                                Constants.UserData,
                                modelToString(registerResponse.getBody()!!)
                        )
                        SharedPrefUtil.getInstance().saveAuthToken(registerResponse.getBody()!!.token)
                        SharedPrefUtil.getInstance().saveImage(registerResponse.getBody()!!.image)
                        SharedPrefUtil.getInstance().saveUserId(registerResponse.getBody()!!.id.toString())
                        SharedPrefUtil.getInstance().saveEmail(registerResponse.getBody()!!.email)
                        SharedPrefUtil.getInstance().saveName(registerResponse.getBody()!!.name)

                        val gson = GsonBuilder().create()

                        SharedPrefUtil.getInstance().registerrespo(gson.toJson(registerResponse.body))
                        Log.e("fdf",SharedPrefUtil.getInstance().getregisterr())
                        val intent = Intent(mContext, VerificationCodeActivity::class.java)
                        intent.putParcelableArrayListExtra("vendorDeliveryOptions", registerResponse.body.vendorDeliveryOptions)
                        Log.e("SignupSize", registerResponse.body.vendorDeliveryOptions.size.toString())
                        intent.putParcelableArrayListExtra("vendorDeliveryCharges", registerResponse.body.vendorDeliveryCharges)
                        startActivity(intent)
                        finishAffinity()

                    }

                    else{
                        showAlerterRed(registerResponse.code as String)

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

                        et_address.setText(place.address.toString())

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
