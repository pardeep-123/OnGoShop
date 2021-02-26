package com.ongoshop.activities

import android.app.Dialog
import android.content.Intent

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.InstanceIdResult

import com.ongoshop.R
import com.ongoshop.base.BaseActivity
import com.ongoshop.manager.restApi.RestObservable
import com.ongoshop.manager.restApi.Status
import com.ongoshop.pojo.LoginResponse


import com.ongoshop.utils.others.Constants
import com.ongoshop.utils.others.MyApplication
import com.ongoshop.utils.others.SharedPrefUtil
import com.ongoshop.viewmodel.AuthViewModel
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : BaseActivity(), View.OnClickListener, Observer<RestObservable> {
    private val viewModel: AuthViewModel
            by lazy { ViewModelProviders.of(this).get(AuthViewModel::class.java) }

    lateinit var mContext: LoginActivity
    var newToken = ""
    lateinit var emailVerificationDialog: Dialog

    override fun getContentId(): Int {
        return R.layout.activity_login
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this


        tvForgotpassword.setOnClickListener(mContext)
        tvCreateAccount.setOnClickListener(mContext)
        btnSignin.setOnClickListener(mContext)

    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.tvForgotpassword -> {
                val intent = Intent(mContext, ForgotPasswordActivity::class.java)
                startActivity(intent)
            }
            R.id.tvCreateAccount -> {
                val intent = Intent(mContext, SignupActivity::class.java)
                startActivity(intent)
            }
            R.id.btnSignin -> {
                FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener(
                        this
                ) { instanceIdResult: InstanceIdResult ->
                    newToken = instanceIdResult.token
                    Log.e("newToken", newToken)
                    SharedPrefUtil.getInstance().saveDeviceToken(newToken)
                }

                Log.e("getDeviceToken__", SharedPrefUtil.getInstance().deviceToken)

                if (SharedPrefUtil.getInstance().deviceToken.equals("") || SharedPrefUtil.getInstance().deviceToken == null) {
                    Log.e("getDeviceToken__", SharedPrefUtil.getInstance().deviceToken)
                    showErrorToast(mContext, "Device Token Not Found")
                } else {
                    LoginApi()
                }

            }
        }
    }

    private fun isValid(): Boolean {
        var check = false
        if (!mValidationClass.isNetworkConnected)
            showAlerterRed(resources.getString(R.string.no_internet))
        else if (mValidationClass.checkStringNull(edEmail.text.toString()))
            showAlerterRed(resources.getString(R.string.error_email))
        else if (!mValidationClass.isValidEmail(edEmail.text.toString()))
            showAlerterRed(resources.getString(R.string.error_validemail))
        else if (mValidationClass.checkStringNull(edPass.text.toString()))
            showAlerterRed(resources.getString(R.string.error_password))
        else
            check = true
        return check
    }

    private fun LoginApi() {
        if (isValid()) {
            val map = HashMap<String, String>()
            map.put("email", edEmail.text.toString().trim())
            map.put("password", edPass.text.toString())
            map.put("deviceType", Constants.Device_Type)
            map.put("deviceToken", SharedPrefUtil.getInstance().deviceToken)

            viewModel.loginApi(this, true, map)
            viewModel.mResponse.observe(this, this)
        }
    }


    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {
                if (it.data is LoginResponse) {
                    val registerResponse: LoginResponse = it.data
                    if (registerResponse.getCode() == Constants.success_code) {

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
                        SharedPrefUtil.getInstance().saveDeviceToken(registerResponse.getBody()!!.deviceToken)

                       /* if (registerResponse.body!!.verified == 0) {
                            val intent = Intent(mContext, VerificationCodeActivity::class.java)
                            intent.putParcelableArrayListExtra("vendorDeliveryOptions", registerResponse.body.vendorDeliveryOptions)
                            intent.putParcelableArrayListExtra("vendorDeliveryCharges", registerResponse.body.vendorDeliveryCharges)
                            startActivity(intent)
                            finishAffinity()
                        } else if (registerResponse.getBody()!!.isShopAdded == 0) {
                            val intent = Intent(mContext, AddShopActivity::class.java)
                            intent.putParcelableArrayListExtra("vendorDeliveryOptions", registerResponse.body.vendorDeliveryOptions)
                            Log.e("LoginSize", registerResponse.body.vendorDeliveryOptions.size.toString())

                            intent.putParcelableArrayListExtra("vendorDeliveryCharges", registerResponse.body.vendorDeliveryCharges)
                            startActivity(intent)
                            finishAffinity()
                        } else {
*/
                            SharedPrefUtil.getInstance().isLogin = true

                            val intent = Intent(mContext, HomeActivity::class.java)
                            startActivity(intent)
                            finishAffinity()
                       /* }*/

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
