package com.ongoshop.activities

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ongoshop.R
import com.ongoshop.adapter.NotificationAdapter
import com.ongoshop.base.BaseActivity
import com.ongoshop.manager.restApi.RestObservable
import com.ongoshop.manager.restApi.Status
import com.ongoshop.pojo.NotificationsListResponse
import com.ongoshop.pojo.PastOrderListResponse
import com.ongoshop.utils.others.CommonMethods
import com.ongoshop.utils.others.Constants
import com.ongoshop.utils.others.MyApplication
import com.ongoshop.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.activity_notification.*

class NotificationActivity : BaseActivity(), View.OnClickListener, Observer<RestObservable> {
    var ivBack: ImageView? = null
    var mContext: NotificationActivity? = null
    var recyclerview: RecyclerView? = null
    var notificationAdapter: NotificationAdapter? = null
    private val viewModel: HomeViewModel
            by lazy { ViewModelProviders.of(this).get(HomeViewModel::class.java) }


    override fun getContentId(): Int {
        return R.layout.activity_notification
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this

        ivBack = findViewById(R.id.ivBack)
        recyclerview = findViewById(R.id.rv_notification)

        ivBack!!.setOnClickListener(mContext)


    }

    override fun onResume() {
        super.onResume()
        notificationListAPI()
    }

    fun notificationListAPI() {
        if (!MyApplication.hasNetwork()) {
            showAlerterRed(resources.getString(R.string.no_internet))
        } else {
            viewModel.notificationListAPI(this, true)
            viewModel.mResponse.observe(this, this)
        }
    }


    fun setNotificationAdapter(notificationsList: ArrayList<NotificationsListResponse.Body?>?) {
        notificationAdapter = NotificationAdapter(mContext!!, notificationsList!!)
        recyclerview!!.setLayoutManager(LinearLayoutManager(mContext))
        recyclerview!!.setAdapter(notificationAdapter)
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.ivBack ->{
                onLeftIconClick()
            }
        }
    }

    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {
                if (it.data is NotificationsListResponse) {
                    val notificationsListResponse: NotificationsListResponse = it.data
                    if (notificationsListResponse!!.getCode() == Constants.success_code) {
                        showSuccessToast(mContext!!, notificationsListResponse!!.getMessage()!!)

                        if (notificationsListResponse.getBody()!!.size == 0) {
                            recyclerview!!.visibility = View.GONE
                            tv_no_notifications!!.visibility = View.VISIBLE
                        } else {
                            recyclerview!!.visibility = View.VISIBLE
                            tv_no_notifications!!.visibility = View.GONE
                            setNotificationAdapter(notificationsListResponse!!.getBody())
                        }

                    } else {
                        CommonMethods.AlertErrorMessage(mContext,  notificationsListResponse.getMessage())
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