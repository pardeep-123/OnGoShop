package com.ongoshop.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ongoshop.R
import com.ongoshop.adapter.SubscriptionAdapter
import com.ongoshop.base.BaseActivity
import com.ongoshop.clickListeners.SubcriptionClick
import com.ongoshop.manager.restApi.RestObservable
import com.ongoshop.manager.restApi.Status
import com.ongoshop.pojo.SubscriptionListResponse
import com.ongoshop.utils.others.CommonMethods
import com.ongoshop.utils.others.Constants
import com.ongoshop.utils.others.MyApplication
import com.ongoshop.viewmodel.SettingsViewModel
import kotlinx.android.synthetic.main.activity_subscription.*

class SubscriptionActivity : BaseActivity(), Observer<RestObservable>, SubcriptionClick {
    var mContext: SubscriptionActivity? = null
    var subscriptionAdapter: SubscriptionAdapter? = null
    var btnSubscription: Button? = null
    var ivBack: ImageView? = null

    private val viewModel: SettingsViewModel
            by lazy { ViewModelProviders.of(this).get(SettingsViewModel::class.java) }

    override fun getContentId(): Int {
        return R.layout.activity_subscription
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        ivBack = findViewById(R.id.ivBack)
        btnSubscription = findViewById(R.id.btnSubscription)

        ivBack!!.setOnClickListener { onLeftIconClick() }

        btnSubscription!!.setOnClickListener {
            val intent = Intent(mContext, PaymentActivity::class.java)
            startActivity(intent)
        }
        getSubscriptionsApi()
    }

    private fun getSubscriptionsApi() {
        if (!MyApplication.instance!!.checkIfHasNetwork())
            showAlerterRed(resources.getString(R.string.no_internet))
        else {
            viewModel.getSubscriptionsAPI(mContext!!, true)
            viewModel.mResponse.observe(this, this)
        }
    }

    fun setSubscriptionAdapter(subscriptionList: ArrayList<SubscriptionListResponse.Body?>?) {
        subscriptionAdapter = SubscriptionAdapter(mContext!!, subscriptionList!!, this)
        rv_subscriptions.layoutManager = LinearLayoutManager(mContext, RecyclerView.VERTICAL, false)
        rv_subscriptions.adapter = subscriptionAdapter

    }

    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {
                if (it.data is SubscriptionListResponse) {
                    val subscriptionListResponse: SubscriptionListResponse = it.data
                    if (subscriptionListResponse.getCode() == Constants.success_code) {
                        showSuccessToast(mContext!!, subscriptionListResponse.getMessage()!!)

                        if (subscriptionListResponse.getBody()!!.size == 0) {
                            rv_subscriptions!!.visibility = View.GONE
                            tv_no_subscription!!.visibility = View.VISIBLE

                        } else {
                            rv_subscriptions!!.visibility = View.VISIBLE
                            tv_no_subscription!!.visibility = View.GONE
                            setSubscriptionAdapter(subscriptionListResponse.getBody())
                        }
                    } else {
                        CommonMethods.AlertErrorMessage(mContext, subscriptionListResponse.getMessage())
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

    override fun subcriptionClick(pos: Int, id: String) {

    }

}