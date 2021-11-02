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
import com.ongoshop.adapter.ReadyForPickupAdapter
import com.ongoshop.base.BaseActivity
import com.ongoshop.manager.restApi.RestObservable
import com.ongoshop.manager.restApi.Status
import com.ongoshop.pojo.DeliveryAndPickupOrderListResponse
import com.ongoshop.utils.others.CommonMethods
import com.ongoshop.utils.others.Constants
import com.ongoshop.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.activity_pickup.*
import java.util.HashMap

class ReadyForPickupActivity : BaseActivity(), View.OnClickListener, Observer<RestObservable> {
    var ivBack: ImageView? = null
    var mContext: ReadyForPickupActivity? = null
    var btnFinish: Button? = null
    var recyclerview: RecyclerView? = null
    var pickupAdapter: ReadyForPickupAdapter? = null
    private val viewModel: HomeViewModel
            by lazy { ViewModelProviders.of(this).get(HomeViewModel::class.java) }


    override fun getContentId(): Int {
        return R.layout.activity_pickup
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        ivBack = findViewById(R.id.ivBack)

        ivBack!!.setOnClickListener(View.OnClickListener { finish() })
        recyclerview = findViewById(R.id.rv_pickup_orders)

        pickupOrdersAPI()

    }

    private fun pickupOrdersAPI(){
        if (!mValidationClass.isNetworkConnected) {
            showAlerterRed(resources.getString(R.string.no_internet))
        } else {
            val map = HashMap<String, String>()
            map.put("isSelfpickup", "1")
            map.put("orderStatus", intent.getStringExtra("readyforPickup")!!)

            viewModel.isSelfpickupOrdersAPI(this, true, map)
            viewModel.mResponse.observe(this, this)
        }

    }

    private fun setPickupOrderAdapter(deliveryOrderList: ArrayList<DeliveryAndPickupOrderListResponse.Body>?) {
        pickupAdapter = ReadyForPickupAdapter(mContext!!, deliveryOrderList!!)
        recyclerview!!.layoutManager = LinearLayoutManager(mContext)
        recyclerview!!.adapter = pickupAdapter
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
                            setPickupOrderAdapter(deliveryOrderListResponse!!.getBody()!!)
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
        when(v!!.id){
            R.id.ivBack ->{
                onLeftIconClick()
            }
        }
    }

}