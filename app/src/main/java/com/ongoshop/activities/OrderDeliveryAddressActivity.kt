package com.ongoshop.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ongoshop.R
import com.ongoshop.base.BaseActivity
import com.ongoshop.manager.restApi.RestObservable
import com.ongoshop.manager.restApi.Status
import com.ongoshop.pojo.AcceptOrderResponse
import com.ongoshop.pojo.OrderItemsListResponse
import com.ongoshop.pojo.ShippedOrderResponse
import com.ongoshop.utils.others.CommonMethods
import com.ongoshop.utils.others.Constants
import com.ongoshop.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.activity_delivery2.*
import kotlinx.android.synthetic.main.activity_delivery2.tv_order_number
import kotlinx.android.synthetic.main.activity_delivery2.tv_username
import kotlinx.android.synthetic.main.activity_detail.*
import java.util.HashMap

class OrderDeliveryAddressActivity : BaseActivity(), View.OnClickListener, Observer<RestObservable> {
    var ivBack: ImageView? = null
    var mContext: OrderDeliveryAddressActivity? = null
    var btnDelivery: Button? = null
    var orderId: String? = null
    var btnDelivery2: Button? = null
    private val viewModel: HomeViewModel
            by lazy { ViewModelProviders.of(this).get(HomeViewModel::class.java) }




    override fun getContentId(): Int {
        return R.layout.activity_delivery2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delivery2)
        mContext = this
        ivBack = findViewById(R.id.ivBack)
        btnDelivery = findViewById(R.id.btnDelivery)

        ivBack!!.setOnClickListener(mContext!!)
        btnDelivery!!.setOnClickListener(mContext!!)
        if (intent.extras !=null){
            orderId = intent.getStringExtra("orderId")!!
           // orderId = "1"
            tv_username.text = intent.getStringExtra("username")
            tv_date.text = CommonMethods.parseDateToddMMyyyy(intent.getStringExtra("createdAt"), "dd-MM-yyyy")
            tv_order_number.text = "Order Number: "+intent.getStringExtra("orderNo")
            tv_delivery_address.text = intent.getStringExtra("userAddress")
            tv_user_phone.text = intent.getStringExtra("phone")
        }

        /* btnDelivery2=findViewById(R.id.btnDelivery2);
        btnDelivery2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewActivity();
            }
        });*/

        btnDelivery!!.setTag(1)
        btnDelivery!!.text = getString(R.string.board_for_delivery)


/*
        btnDelivery!!.setOnClickListener(View.OnClickListener { v ->
            if (btnDelivery!!.text.toString() == getString(R.string.complete_delivery)) {
                btnDelivery!!.text = getString(R.string.complete_delivery)
                val intent = Intent(mContext, HomeActivity::class.java)
                startActivity(intent)
                //openNewActivity();
                v.tag = 1 //pause
            } else {
                val status = v.tag as Int
                if (status == 1) {
                    btnDelivery!!.text = getString(R.string.board_for_delivery)
                    v.tag = 0 //pause
                } else {
                    btnDelivery!!.text = getString(R.string.complete_delivery)

                    //openNewActivity();
                    v.tag = 1 //pause
                }
            }
        })
*/
    }

    fun orderShippedAPI() {
        if (!mValidationClass.isNetworkConnected) {
            showAlerterRed(resources.getString(R.string.no_internet))
        } else {
            val map = HashMap<String, String>()
            map.put("id", orderId!!)
            viewModel.shippedOrderAPI(this, true, map)
            viewModel.mResponse.observe(this, this)
        }
    }


    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.ivBack ->{
                onLeftIconClick()
            }
         R.id.btnDelivery ->{
             orderShippedAPI()
            }
        }
    }

    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {
                if (it.data is ShippedOrderResponse) {
                    val shippedOrderResponse: ShippedOrderResponse = it.data
                    if (shippedOrderResponse!!.getCode() == Constants.success_code) {
                        showSuccessToast(mContext!!, shippedOrderResponse!!.getMessage()!!)
                        finishAffinity()
                        val intent = Intent(mContext, HomeActivity::class.java)
                        startActivity(intent)
                    } else {
                        CommonMethods.AlertErrorMessage(
                                mContext,
                                shippedOrderResponse.getMessage()
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