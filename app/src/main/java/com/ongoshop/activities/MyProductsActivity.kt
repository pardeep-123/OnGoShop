package com.ongoshop.activities

import android.content.Intent
import android.hardware.camera2.CameraManager
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ongoshop.R
import com.ongoshop.adapter.MyProductsAdapter
import com.ongoshop.adapter.ProductAdapter
import com.ongoshop.base.BaseActivity
import com.ongoshop.clickListeners.ProductClick
import com.ongoshop.manager.restApi.RestObservable
import com.ongoshop.manager.restApi.Status
import com.ongoshop.pojo.ChangeAvailabilityResponse
import com.ongoshop.pojo.MyProductListingResponse
import com.ongoshop.pojo.ProductListingResponse
import com.ongoshop.utils.others.CommonMethods
import com.ongoshop.utils.others.Constants
import com.ongoshop.utils.others.MyApplication
import com.ongoshop.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.activity_new_orders.*
import kotlinx.android.synthetic.main.activity_product.*

class MyProductsActivity : BaseActivity(), View.OnClickListener, ProductClick, Observer<RestObservable> {
    var ivBack: ImageView? = null
    var mContext: MyProductsActivity? = null
    private var btnAddProducts: Button? = null
    var rvProductList: RecyclerView? = null
    var productAdapter: MyProductsAdapter? = null
    private var myProductList: ArrayList<MyProductListingResponse.Body?>? = ArrayList()
    private var productId = ""
    private val viewModel: HomeViewModel
            by lazy { ViewModelProviders.of(this).get(HomeViewModel::class.java) }


    override fun getContentId(): Int {
        return R.layout.activity_product
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        ivBack = findViewById(R.id.ivBack)
        btnAddProducts = findViewById(R.id.btnAddProducts)
        rvProductList = findViewById(R.id.rv_product_list)

        btnAddProducts!!.setOnClickListener(mContext)
        ivBack!!.setOnClickListener(this)

        if (intent.extras != null) {
            if (intent.getStringExtra("from").equals("Home")){
                btnAddProducts!!.visibility = View.GONE
            }
        }
    }

    override fun onResume() {
        super.onResume()
        productListAPI()
    }

    private fun productListAPI() {
        if (!MyApplication.instance!!.checkIfHasNetwork())
            showAlerterRed(resources.getString(R.string.no_internet))
        else {
            viewModel.getMyProductListAPI(mContext!!, true)
            viewModel.mResponse.observe(this, this)
        }
    }

    private fun setProductListAdapter(productList: ArrayList<MyProductListingResponse.Body?>) {
        productAdapter = MyProductsAdapter(mContext!!, productList, this)
        rvProductList!!.setLayoutManager(LinearLayoutManager(mContext))
        rvProductList!!.setAdapter(productAdapter)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.ivBack -> {
                onLeftIconClick()
            }
            R.id.btnAddProducts -> {
                val i = Intent(mContext, AddProductActivity::class.java)
                i.putExtra("categoryId", productId)
                startActivity(i)
            }
        }
    }

    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {
                if (it.data is MyProductListingResponse) {
                    val myProductListingResponse: MyProductListingResponse = it.data
                    if (myProductListingResponse.getCode() == Constants.success_code) {
                        showSuccessToast(mContext!!, myProductListingResponse!!.getMessage()!!)
                        myProductList!!.clear()
                        myProductList!!.addAll(myProductListingResponse!!.getBody()!!)

                        if (myProductListingResponse.getBody()!!.size == 0) {
                            rvProductList!!.visibility = View.GONE
                            tv_no_product!!.visibility = View.VISIBLE
                        } else {
                            rvProductList!!.visibility = View.VISIBLE
                            tv_no_product!!.visibility = View.GONE
                            setProductListAdapter(myProductList!!)
                        }

                    } else {
                        CommonMethods.AlertErrorMessage(
                                mContext,
                                myProductListingResponse.getMessage()
                        )
                    }
                }

                if (it.data is ChangeAvailabilityResponse) {
                    val changeAvailabilityResponse: ChangeAvailabilityResponse = it.data
                    if (changeAvailabilityResponse.getCode() == Constants.success_code) {
                        showSuccessToast(mContext!!, changeAvailabilityResponse!!.getMessage()!!)

                    } else {
                        CommonMethods.AlertErrorMessage(mContext, changeAvailabilityResponse.getMessage())
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

    override fun productClickk(position: Int, id: String, isProductAvailable: String) {
        if (!MyApplication.instance!!.checkIfHasNetwork())
            showAlerterRed(resources.getString(R.string.no_internet))
        else {
            val map = HashMap<String, String>()
            map.put("isAvailable", isProductAvailable)
            map.put("productId", id)

            viewModel.changeAvailabilityAPI(mContext!!, true, map)
            viewModel.mResponse.observe(this, this)
        }
    }

}


