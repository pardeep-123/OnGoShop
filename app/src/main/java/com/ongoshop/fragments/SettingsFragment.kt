package com.ongoshop.fragments

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.RelativeLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ongoshop.R
import com.ongoshop.activities.*
import com.ongoshop.base.BaseFragment
import com.ongoshop.manager.restApi.RestObservable
import com.ongoshop.manager.restApi.Status
import com.ongoshop.pojo.LogoutResponse
import com.ongoshop.utils.others.CommonMethods
import com.ongoshop.utils.others.Constants
import com.ongoshop.utils.others.MyApplication
import com.ongoshop.utils.others.SharedPrefUtil
import com.ongoshop.viewmodel.SettingsViewModel
import kotlinx.android.synthetic.main.alert_logout.*


class SettingsFragment : BaseFragment(), View.OnClickListener, Observer<RestObservable> {

    private val viewModel: SettingsViewModel
            by lazy { ViewModelProviders.of(this).get(SettingsViewModel::class.java) }
    private lateinit var logoutDialog: Dialog

    lateinit var v: View

    lateinit var rlChange: RelativeLayout
    lateinit var rlTerms: RelativeLayout
    lateinit var rlLogout: RelativeLayout
    lateinit var rlSales: RelativeLayout
    lateinit var rlStaff: RelativeLayout
    lateinit var rlSubscription: RelativeLayout



    companion object {

        fun newInstance(): SettingsFragment {
            return SettingsFragment()
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_settings, container, false)

        rlChange = v.findViewById<RelativeLayout>(R.id.rlChange)
        rlTerms = v.findViewById<RelativeLayout>(R.id.rlTerms)
        rlLogout = v.findViewById<RelativeLayout>(R.id.rlLogout)
        rlSales = v.findViewById<RelativeLayout>(R.id.rlSales)
        rlStaff = v.findViewById<RelativeLayout>(R.id.rlStaff)
        rlSubscription = v.findViewById<RelativeLayout>(R.id.rlSubscription)


        rlChange.setOnClickListener(this)
        rlTerms.setOnClickListener(this)
        rlLogout.setOnClickListener(this)
        rlStaff.setOnClickListener(this)
        rlSales.setOnClickListener(this)
        rlSubscription.setOnClickListener(this)


        return v
    }


    private fun logoutApi() {
        if (!MyApplication.instance!!.checkIfHasNetwork())
            showAlerterRed(resources.getString(R.string.no_internet))
        else {
            val map = HashMap<String, String>()
            map.put("deviceType", Constants.Device_Type)
            map.put("deviceToken", SharedPrefUtil.getInstance().deviceToken)

            viewModel.logoutApi(activity!!, true, map)
            viewModel.mResponse.observe(this, this)
        }
    }


    fun logoutDialogMethod() {
        logoutDialog = Dialog(activity!!, R.style.Theme_Dialog)
        logoutDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        logoutDialog.setContentView(R.layout.alert_logout)

        logoutDialog.window!!.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
        )
        logoutDialog.setCancelable(false)
        logoutDialog.setCanceledOnTouchOutside(false)
        logoutDialog.window!!.setGravity(Gravity.CENTER)


        //  logoutDialog.tv_fb_alert.setText("Facebook have not been installed. Please click on this "+ link+ " and enter the details manually")

        logoutDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        logoutDialog.btnNo.setOnClickListener {
            logoutDialog.dismiss()
        }
        logoutDialog.btnYes.setOnClickListener {
            logoutDialog.dismiss()
            logoutApi()
        }

        logoutDialog.show()

    }


    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.rlSubscription -> {
                val intent = Intent(activity, SubscriptionActivity::class.java)
                startActivity(intent)
            }
            R.id.rlSales -> {
                val intent = Intent(activity, SalesDataActivity::class.java)
                startActivity(intent)
            }
            R.id.rlStaff -> {
                val intent = Intent(activity, ManagerManagmentActivity::class.java)
                startActivity(intent)
            }
            R.id.rlTerms -> {
                val intent = Intent(activity, TermsConditionsActivity::class.java)
                startActivity(intent)
            }
            R.id.rlChange -> {
                val intent = Intent(activity, ChangePasswordActivity::class.java)
                startActivity(intent)
            }
            R.id.rlLogout -> {
                logoutDialogMethod()
            }

        }
    }

    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {
                if (it.data is LogoutResponse) {
                    val logoutResponse: LogoutResponse = it.data
                    if (logoutResponse.getCode() == Constants.success_code) {
                        showSuccessToast(logoutResponse!!.getMessage()!!)
                        MyApplication.instance!!.clearData()
                        SharedPrefUtil.getInstance().clear()
                        SharedPrefUtil.getInstance().isLogin = false
                        //   CommonMethods.failureMethod(mContext,"You are already logged in other device");
                        val intent = Intent(activity, LoginActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                        activity!!.finishAffinity()


                    } else {
                        CommonMethods.AlertErrorMessage(
                                activity,
                                logoutResponse.getMessage()
                        )
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