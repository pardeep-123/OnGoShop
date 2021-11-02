package com.ongoshop.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ongoshop.R
import com.ongoshop.base.BaseActivity
import com.ongoshop.manager.restApi.RestObservable
import com.ongoshop.manager.restApi.Status
import com.ongoshop.pojo.ForgotPasswordResponse
import com.ongoshop.utils.others.CommonMethods
import com.ongoshop.utils.others.Constants
import com.ongoshop.utils.others.Constants.Companion.SecurityKeyValue
import com.ongoshop.utils.others.MyApplication
import com.ongoshop.viewmodel.AuthViewModel
import kotlinx.android.synthetic.main.activity_forgot_password.*


class ForgotPasswordActivity : BaseActivity(), View.OnClickListener, Observer<RestObservable> {

    private val viewModel: AuthViewModel
            by lazy { ViewModelProviders.of(this).get(AuthViewModel::class.java) }
    lateinit var mContext: ForgotPasswordActivity
    override fun getContentId(): Int {
        return R.layout.activity_forgot_password
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this

        btnSend.setOnClickListener(mContext)
        ivBack.setOnClickListener(mContext)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnSend -> {
                forgotPasswordApi()
                hideKeyboard(btnSend)

            }
            R.id.ivBack -> {
                onLeftIconClick()
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
        else
            check = true
        return check
    }

    private fun forgotPasswordApi() {
        if (isValid()) {
            val map = HashMap<String, String>()
            map.put("email", edEmail.text.toString().trim())
            map.put(Constants.SecurityKey, SecurityKeyValue)
//            map.put("device_token", token!!)

            viewModel.forgotPasswordApi(this, true, map)
            viewModel.mResponse.observe(this, this)
        }
    }

    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {
                if (it.data is ForgotPasswordResponse) {
                    val forgotPasswordResponse: ForgotPasswordResponse = it.data
                    if (forgotPasswordResponse.getCode()!!.equals("200")) {
                        showSuccessToast(mContext, forgotPasswordResponse.getMessage().toString())

                        var intent = Intent(mContext, LoginActivity::class.java)
                        startActivity(intent)
                        finishAffinity()
                    } else {
                      showAlerterRed(forgotPasswordResponse.getMessage().toString())
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
