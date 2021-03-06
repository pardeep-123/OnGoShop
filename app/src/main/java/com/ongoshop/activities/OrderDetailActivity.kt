package com.ongoshop.activities

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ongoshop.R
import com.ongoshop.adapter.OrderDetailAdapter
import com.ongoshop.adapter.OrderItemsListAdapter
import com.ongoshop.base.BaseActivity
import com.ongoshop.manager.restApi.RestObservable
import com.ongoshop.manager.restApi.Status
import com.ongoshop.pojo.AcceptOrderResponse
import com.ongoshop.pojo.FinishPackingResponse
import com.ongoshop.pojo.OrderItemsListResponse
import com.ongoshop.utils.others.CommonMethods
import com.ongoshop.utils.others.Constants
import com.ongoshop.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.activity_detail.*
import java.util.HashMap

class OrderDetailActivity : BaseActivity(), View.OnClickListener, Observer<RestObservable> {
    var ivBack: ImageView? = null
    var mContext: OrderDetailActivity? = null
    var btnFinish: Button? = null
    var dialog: Dialog? = null
    var orderDetailAdapter: OrderDetailAdapter? = null
    var orderId: String? = null

    var recyclerview: RecyclerView? = null
    private val viewModel: HomeViewModel
            by lazy { ViewModelProviders.of(this).get(HomeViewModel::class.java) }


    override fun getContentId(): Int {
        return R.layout.activity_order_detail
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        ivBack = findViewById(R.id.ivBack)
        btnFinish = findViewById(R.id.btnFinish)
        recyclerview = findViewById(R.id.recyclerview)


        if (intent.extras != null) {
            orderId = intent.getStringExtra("orderId")

            tv_username.setText(intent.getStringExtra("username"))
            tv_order_number.setText("Order Number :" + intent.getStringExtra("orderNo"))
            tv_timings.setText(intent.getStringExtra("deliverySlot"))

            orderDetailsAPI()
        }


        ivBack!!.setOnClickListener(mContext)
        btnFinish!!.setOnClickListener(mContext)

    }



    fun orderDetailsAPI() {
        if (!mValidationClass.isNetworkConnected) {
            showAlerterRed(resources.getString(R.string.no_internet))
        } else {
            val map = HashMap<String, String>()
            map.put("id", orderId!!)

            viewModel.ordersItemsAPI(this, true, map)
            viewModel.mResponse.observe(this, this)
        }
    }

    fun finishPackingAPI() {
        if (!mValidationClass.isNetworkConnected) {
            showAlerterRed(resources.getString(R.string.no_internet))
        } else {
            val map = HashMap<String, String>()
            map.put("id", orderId!!)

            viewModel.finishPackingAPI(this, true, map)
            viewModel.mResponse.observe(this, this)
        }
    }


    fun setCartDetailsAdapter(orderItemsList: ArrayList<OrderItemsListResponse.Body?>) {
        orderDetailAdapter = OrderDetailAdapter(mContext!!, orderItemsList)
        recyclerview!!.setLayoutManager(LinearLayoutManager(mContext))
        recyclerview!!.setAdapter(orderDetailAdapter)

    }


    fun showDialog() {
        dialog = Dialog(mContext!!)
        dialog!!.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog!!.setContentView(R.layout.alert_detail)
        dialog!!.setCancelable(false)
        dialog!!.setCanceledOnTouchOutside(false)
        val btnOkk = dialog!!.findViewById<Button>(R.id.btnOkk)
        btnOkk.setOnClickListener {
            dialog!!.dismiss()
            finishAffinity()
            mContext!!.startActivity(Intent(mContext, HomeActivity::class.java))
        }
        dialog!!.show()
    }

    fun detailDailog() {
        dialog = Dialog(mContext!!)
        dialog!!.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog!!.setContentView(R.layout.alert_detail2)
        dialog!!.setCancelable(true)
        val btnOk = dialog!!.findViewById<Button>(R.id.btnOk)
        btnOk.setOnClickListener {
            mContext!!.startActivity(Intent(mContext, HomeActivity::class.java))
            dialog!!.dismiss()
        }
        dialog!!.show()
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.ivBack -> {
                onLeftIconClick()
            }
            R.id.btnFinish -> {
                finishPackingAPI()
            }
        }
    }

    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {
                if (it.data is OrderItemsListResponse) {
                    val orderItemsListResponse: OrderItemsListResponse = it.data
                    if (orderItemsListResponse!!.getCode() == Constants.success_code) {
                        showSuccessToast(mContext!!, orderItemsListResponse!!.getMessage()!!)

                        if (orderItemsListResponse.getBody()!!.size == 0) {
                            recyclerview!!.visibility = View.GONE
                            tv_no_items!!.visibility = View.VISIBLE
                            btnFinish!!.visibility = View.GONE
                        } else {
                            recyclerview!!.visibility = View.VISIBLE
                            tv_no_items!!.visibility = View.GONE
                            setCartDetailsAdapter(orderItemsListResponse!!.getBody()!!)
                            btnFinish!!.visibility = View.VISIBLE

                        }

                        tv_total_items.setText(getString(R.string.total_items) + orderItemsListResponse.getBody()!!.size.toString())


                    } else {
                        CommonMethods.AlertErrorMessage(
                                mContext,
                                orderItemsListResponse.getMessage()
                        )
                    }
                }

                if (it.data is FinishPackingResponse) {
                    val finishPackingResponse: FinishPackingResponse = it.data
                    if (finishPackingResponse!!.getCode() == Constants.success_code) {
                        //showSuccessToast(mContext!!, acceptOrderResponse!!.getMessage()!!)
                        showDialog()
                    } else {
                        CommonMethods.AlertErrorMessage(mContext, finishPackingResponse.getMessage()
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