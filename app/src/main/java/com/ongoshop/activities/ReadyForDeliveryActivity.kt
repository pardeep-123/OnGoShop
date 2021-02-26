package com.ongoshop.activities

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ongoshop.R
import com.ongoshop.adapter.ReadyforDeliveryAdapter
import com.ongoshop.base.BaseActivity
import com.ongoshop.manager.restApi.RestObservable
import com.ongoshop.manager.restApi.Status
import com.ongoshop.pojo.DeliveryAndPickupOrderListResponse
import com.ongoshop.pojo.OrderListResponse
import com.ongoshop.utils.others.CommonMethods
import com.ongoshop.utils.others.Constants
import com.ongoshop.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.activity_delivery.*
import java.util.HashMap

class ReadyForDeliveryActivity : BaseActivity(), View.OnClickListener, Observer<RestObservable> {
    var ivBack: ImageView? = null
    var mContext: ReadyForDeliveryActivity? = null
    var btnFinish: Button? = null
    var recyclerview: RecyclerView? = null
    var deliveryAdapter: ReadyforDeliveryAdapter? = null
    private val viewModel: HomeViewModel
            by lazy { ViewModelProviders.of(this).get(HomeViewModel::class.java) }


    override fun getContentId(): Int {
        return R.layout.activity_delivery
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delivery)
        mContext = this
        ivBack = findViewById(R.id.ivBack)
        recyclerview = findViewById(R.id.rv_delivery_orders)

        ivBack!!.setOnClickListener(mContext)

        deliveryOrdersAPI()

    }


    private fun deliveryOrdersAPI(){
        if (!mValidationClass.isNetworkConnected) {
            showAlerterRed(resources.getString(R.string.no_internet))
        } else {
            val map = HashMap<String, String>()
            map.put("isSelfpickup", "0")
            map.put("orderStatus", intent.getStringExtra("readyforDelivery")!!)

            viewModel.isSelfpickupOrdersAPI(this, true, map)
            viewModel.mResponse.observe(this, this)
        }

    }

    private fun setDeiveryOrderAdapter(deliveryOrderList: ArrayList<DeliveryAndPickupOrderListResponse.Body>?) {
        deliveryAdapter = ReadyforDeliveryAdapter(mContext!!, deliveryOrderList!!)
        recyclerview!!.layoutManager = LinearLayoutManager(mContext)
        recyclerview!!.adapter = deliveryAdapter
    }



    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {
                if (it.data is DeliveryAndPickupOrderListResponse) {
                    val deliveryOrderListResponse: DeliveryAndPickupOrderListResponse = it.data
                    if (deliveryOrderListResponse!!.getCode() == Constants.success_code) {
                      //  showSuccessToast(mContext!!, deliveryOrderListResponse!!.getMessage()!!)

                        if (deliveryOrderListResponse.getBody()!!.size == 0) {
                            recyclerview!!.visibility = View.GONE
                            tv_no_orders!!.visibility = View.VISIBLE
                        } else {
                            recyclerview!!.visibility = View.VISIBLE
                            tv_no_orders!!.visibility = View.GONE
                            setDeiveryOrderAdapter(deliveryOrderListResponse!!.getBody()!!)
                        }

                    } else {
                        CommonMethods.AlertErrorMessage(mContext, deliveryOrderListResponse.getMessage())
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

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.ivBack -> {
                onLeftIconClick()
            }
        }

    }

}