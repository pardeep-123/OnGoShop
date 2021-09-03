package com.ongoshop.activities

import android.content.Intent
import android.hardware.camera2.CameraManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ongoshop.R
import com.ongoshop.adapter.ProductAdapter
import com.ongoshop.base.BaseActivity
import com.ongoshop.clickListeners.ProductClick
import com.ongoshop.manager.restApi.RestObservable
import com.ongoshop.manager.restApi.Status
import com.ongoshop.pojo.ChangeAvailabilityResponse
import com.ongoshop.pojo.ProductListingResponse
import com.ongoshop.utils.others.CommonMethods
import com.ongoshop.utils.others.Constants
import com.ongoshop.utils.others.MyApplication
import com.ongoshop.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.activity_new_orders.*
import kotlinx.android.synthetic.main.activity_product.*

class ProductActivity : BaseActivity(), View.OnClickListener, ProductClick, Observer<RestObservable> {
    var ivBack: ImageView? = null
    var mContext: ProductActivity? = null
    private var btnAddProducts: Button? = null
    var rvProductList: RecyclerView? = null
    var productAdapter: ProductAdapter? = null
    private var productList: ArrayList<ProductListingResponse.Body?>? = ArrayList()
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
            productId = intent.getStringExtra("categoryId")!!
            tvTitle.text = intent.getStringExtra("categoryName")!!

        }

        et_search_my_products.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
            ) {
            }

            override fun onTextChanged(
                    s: CharSequence,
                    start: Int,
                    before: Int,
                    count: Int
            ) {
            }

            override fun afterTextChanged(s: Editable) {
                try {
                    if (productList != null) {
                        productAdapter!!.filter(s.toString().trim(), tv_no_product)
                    }

                } catch (e: Exception) {
                }
            }
        })

    }

    override fun onResume() {
        super.onResume()
        productListAPI()
    }

    private fun productListAPI() {
        if (!MyApplication.instance!!.checkIfHasNetwork())
            showAlerterRed(resources.getString(R.string.no_internet))
        else {
            val map = HashMap<String, String>()
            //  map.put("searchKeyword", "")
            map.put("categoryId", productId)

            viewModel.getProductListingAPI(mContext!!, true, map)
            viewModel.mResponse.observe(this, this)
        }
    }

    private fun setProductListAdapter(productList: ArrayList<ProductListingResponse.Body?>) {
        productAdapter = ProductAdapter(mContext!!, productList, this)
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
                if (it.data is ProductListingResponse) {
                    val productListingResponse: ProductListingResponse = it.data
                    if (productListingResponse.getCode() == Constants.success_code) {
                        showSuccessToast(mContext!!, productListingResponse!!.getMessage()!!)
                        productList!!.clear()
                        productList!!.addAll(productListingResponse!!.getBody()!!)

                        if (productListingResponse.getBody()!!.size == 0) {
                            rvProductList!!.visibility = View.GONE
                            tv_no_product!!.visibility = View.VISIBLE
                        } else {
                            rvProductList!!.visibility = View.VISIBLE
                            tv_no_product!!.visibility = View.GONE
                            setProductListAdapter(productList!!)
                        }

                    } else {
                        CommonMethods.AlertErrorMessage(
                                mContext,
                                productListingResponse.getMessage()
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


