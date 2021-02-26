package com.ongoshop.activities

import android.app.Dialog
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
import com.ongoshop.adapter.OrderItemsListAdapter
import com.ongoshop.base.BaseActivity
import com.ongoshop.manager.restApi.RestObservable
import com.ongoshop.manager.restApi.Status
import com.ongoshop.pojo.AcceptOrderResponse
import com.ongoshop.pojo.OrderItemsListResponse
import com.ongoshop.utils.others.CommonMethods
import com.ongoshop.utils.others.Constants
import com.ongoshop.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.activity_detail.*
import java.util.HashMap

class DetailActivity : BaseActivity(), View.OnClickListener, Observer<RestObservable> {

    var ivBack: ImageView? = null
    var mContext: DetailActivity? = null
    var btnFinish: Button? = null
    var dialog: Dialog? = null
    var orderId: String? = null
    var recyclerview: RecyclerView? = null
    var orderItemsListAdapter: OrderItemsListAdapter? = null

    private val viewModel: HomeViewModel
            by lazy { ViewModelProviders.of(this).get(HomeViewModel::class.java) }

    override fun getContentId(): Int {
        return R.layout.activity_detail
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        ivBack = findViewById(R.id.ivBack)
        recyclerview = findViewById(R.id.rv_cart)

        ivBack!!.setOnClickListener(mContext)
        btn_accept!!.setOnClickListener(mContext)

        if (intent.extras != null) {
            orderId = intent.getStringExtra("orderId")

            tv_username.setText(intent.getStringExtra("username"))
            tv_order_number.setText("Order Number :" + intent.getStringExtra("orderNo"))
            tv_timings.setText(intent.getStringExtra("deliverySlot"))
            if (intent.getStringExtra("from").equals("Past")) {
                btn_accept.visibility = View.GONE
            } else {
                btn_accept.visibility = View.VISIBLE
            }
            orderDetailsAPI()
        }
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

    fun orderAcceptAPI() {
        if (!mValidationClass.isNetworkConnected) {
            showAlerterRed(resources.getString(R.string.no_internet))
        } else {
            val map = HashMap<String, String>()
            map.put("id", orderId!!)

            viewModel.acceptOrderAPI(this, true, map)
            viewModel.mResponse.observe(this, this)
        }
    }

    fun setCartDetailsAdapter(orderItemsList: ArrayList<OrderItemsListResponse.Body?>) {
        orderItemsListAdapter = OrderItemsListAdapter(mContext!!, orderItemsList)
        recyclerview!!.layoutManager = LinearLayoutManager(mContext)
        recyclerview!!.adapter = orderItemsListAdapter
    }

    private fun showDialog() {
        dialog = Dialog(mContext!!)
        dialog!!.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog!!.setContentView(R.layout.accept_alert_dialog)
        dialog!!.setCancelable(false)
        dialog!!.setCanceledOnTouchOutside(false)
        val btnOkk = dialog!!.findViewById<Button>(R.id.btn_order_accepted_ok)
        btnOkk.setOnClickListener {
            dialog!!.dismiss()
            finishAffinity()
            mContext!!.startActivity(Intent(mContext, HomeActivity::class.java))

        }
        dialog!!.show()
    }


/*
    private fun showDailog() {
        dialog = Dialog(mContext!!)
        dialog!!.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog!!.setContentView(R.layout.alert_detail)
        dialog!!.setCancelable(true)
        val btnOkk = dialog!!.findViewById<Button>(R.id.btnOkk)
        btnOkk.setOnClickListener {
            dialog!!.dismiss()
            detailDailog()
        }
        dialog!!.show()
    }
*/

/*
    private fun detailDailog() {
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
*/

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.ivBack -> {
                onLeftIconClick()
            }
            R.id.btn_accept -> {
                orderAcceptAPI()
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
                        }

                        tv_total_items.setText(getString(R.string.total_items) + orderItemsListResponse.getBody()!!.size.toString())


                    } else {
                        CommonMethods.AlertErrorMessage(
                                mContext,
                                orderItemsListResponse.getMessage()
                        )
                    }
                }

                if (it.data is AcceptOrderResponse) {
                    val acceptOrderResponse: AcceptOrderResponse = it.data
                    if (acceptOrderResponse!!.getCode() == Constants.success_code) {
                        //showSuccessToast(mContext!!, acceptOrderResponse!!.getMessage()!!)
                        showDialog()
                    } else {
                        CommonMethods.AlertErrorMessage(
                                mContext,
                                acceptOrderResponse.getMessage()
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