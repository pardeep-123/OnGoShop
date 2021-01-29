package com.ongoshop.activities


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.InstanceIdResult
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
    var IsCheckedTerms = false
    override fun getContentId(): Int {
        return R.layout.activity_create_account
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this


        rlAdd.setOnClickListener(mContext)
        ivBack.setOnClickListener(mContext)
        ivOn.setOnClickListener(mContext)
        ivOff.setOnClickListener(mContext)
        tvTerms.setOnClickListener(mContext)
        tvSignin.setOnClickListener(mContext)
        btnVerification.setOnClickListener(mContext)


        ccp.setOnCountryChangeListener(object : CountryCodePicker.OnCountryChangeListener {
            override fun onCountrySelected(selectedCountry: Country?) {
                ccp.getSelectedCountryCode()
                ccp.showFlag(false)
                ccp.enableHint(true)
                ccp.setKeyboardAutoPopOnSearch(false)
                countryCode = ccp.getSelectedCountryCode()

            }
        })

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
                    var newToken = instanceIdResult.token
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
        else if (!et_cPass.text.toString().equals(edPass.text.toString(), ignoreCase = true))
            showAlerterRed(resources.getString(R.string.error_password_not_matched))
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

}
