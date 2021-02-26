package com.ongoshop.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ongoshop.R
import com.ongoshop.adapter.NewOrderAdapter
import com.ongoshop.adapter.TodaysNewOrderAdapter
import com.ongoshop.base.BaseActivity
import com.ongoshop.manager.restApi.RestObservable
import com.ongoshop.manager.restApi.Status
import com.ongoshop.pojo.OrderListResponse
import com.ongoshop.utils.others.CommonMethods
import com.ongoshop.utils.others.Constants
import com.ongoshop.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.activity_new_orders.*
import java.util.HashMap

class NewOrdersActivity : BaseActivity(), View.OnClickListener, Observer<RestObservable> {

    var ivBack: ImageView? = null
    var ivNoti: ImageView? = null
    var mContext: NewOrdersActivity? = null
    var rvFutureOrders: RecyclerView? = null
    var rvTodaysOrder: RecyclerView? = null
    var newOrderAdapter: NewOrderAdapter? = null
    var todaysNewOrderAdapter: TodaysNewOrderAdapter? = null

    private val viewModel: HomeViewModel
            by lazy { ViewModelProviders.of(this).get(HomeViewModel::class.java) }

    override fun getContentId(): Int {
        return R.layout.activity_new_orders
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mContext = this
        ivBack = findViewById(R.id.ivBack)
        ivNoti = findViewById(R.id.ivNoti)
        rvTodaysOrder = findViewById(R.id.rv_new_orders)
        rvFutureOrders = findViewById(R.id.rv_later_new_orders)

        ivBack!!.setOnClickListener(mContext)
        ivNoti!!.setOnClickListener(mContext)

        if (intent.extras != null) {
            if (!mValidationClass.isNetworkConnected) {
                showAlerterRed(resources.getString(R.string.no_internet))
            } else {
                val map = HashMap<String, String>()
                map.put("status", intent.getStringExtra("Orders")!!)

                viewModel.orderListApi(this, true, map)
                viewModel.mResponse.observe(this, this)
            }
        }

    }

    private fun setNewOrderAdapter(newOrderList: ArrayList<OrderListResponse.FutureDates>?) {
        newOrderAdapter = NewOrderAdapter(mContext!!, newOrderList!!, intent.getStringExtra("Orders"))
        rvFutureOrders!!.setLayoutManager(LinearLayoutManager(mContext))
        rvFutureOrders!!.adapter = newOrderAdapter
    }

    private fun setTodaysNewOrderAdapter(todaysNewOrderList: ArrayList<OrderListResponse.Pastdate>?) {
        todaysNewOrderAdapter = TodaysNewOrderAdapter(mContext!!, todaysNewOrderList!!, intent.getStringExtra("Orders"))
        rvTodaysOrder!!.setLayoutManager(LinearLayoutManager(mContext))
        rvTodaysOrder!!.adapter = todaysNewOrderAdapter
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.ivBack -> {
                onLeftIconClick()
            }
            R.id.ivNoti -> {
                val i = Intent(mContext, NotificationActivity::class.java)
                startActivity(i)
            }
        }
    }

    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {
                if (it.data is OrderListResponse) {
                    val orderListResponse: OrderListResponse = it.data
                    if (orderListResponse!!.getCode() == Constants.success_code) {
                        showSuccessToast(mContext!!, orderListResponse!!.getMessage()!!)

                        if (orderListResponse.getBody()!!.futureDates!!.size == 0) {
                            rvFutureOrders!!.visibility = View.GONE
                            tv_no_later_neworders!!.visibility = View.VISIBLE
                        } else {
                            rvFutureOrders!!.visibility = View.VISIBLE
                            tv_no_later_neworders!!.visibility = View.GONE
                            setNewOrderAdapter(orderListResponse!!.getBody()!!.futureDates!!)
                        }

                        if (orderListResponse.getBody()!!.pastdates!!.size == 0) {
                            rvTodaysOrder!!.visibility = View.GONE
                            tv_no_neworders!!.visibility = View.VISIBLE
                        } else {
                            rvTodaysOrder!!.visibility = View.VISIBLE
                            tv_no_neworders!!.visibility = View.GONE
                            setTodaysNewOrderAdapter(orderListResponse!!.getBody()!!.pastdates!!)
                        }


                    } else {
                        CommonMethods.AlertErrorMessage(
                                mContext,
                                orderListResponse.getMessage()
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