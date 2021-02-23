package com.ongoshop.activities

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.ongoshop.R
import com.ongoshop.base.BaseActivity
import com.ongoshop.manager.restApi.RestObservable
import com.ongoshop.manager.restApi.Status
import com.ongoshop.pojo.*
import com.ongoshop.utils.others.CommonMethods
import com.ongoshop.utils.others.Constants
import com.ongoshop.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.activity_add_card.*
import kotlinx.android.synthetic.main.activity_product_detail.*
import java.util.HashMap

class ProductDetailActivity : BaseActivity(), View.OnClickListener, Observer<RestObservable> {
    var mContext: ProductDetailActivity? = null
    var btnEdit: Button? = null
    var btnDelete: Button? = null
    var ivBack: ImageView? = null
    private  var productId=""
    var dialog: Dialog? = null
    private val viewModel: HomeViewModel
            by lazy { ViewModelProviders.of(this).get(HomeViewModel::class.java) }


    override fun getContentId(): Int {
        return R.layout.activity_product_detail
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mContext = this
        ivBack = findViewById(R.id.ivBack)
        btnDelete = findViewById(R.id.btnDelete)
        btnEdit = findViewById(R.id.btnEdit)

        btnDelete!!.setOnClickListener(mContext)
        ivBack!!.setOnClickListener(mContext)
        btnEdit!!.setOnClickListener(mContext)

        if (intent.extras != null) {
            Glide.with(mContext!!).load(intent.getStringExtra("productImage")).
            error(R.mipmap.no_image_placeholder).into(iv_product_image)
            productId = intent.getStringExtra("productId")!!
            tv_product_name.text = intent.getStringExtra("productName")
            tv_product_code.text = intent.getStringExtra("barcode")
            tv_product_country_origin.text = intent.getStringExtra("countryOfOrigin")
            tv_product_price.text = intent.getStringExtra("mrp")
            tv_weight_unit.text = intent.getStringExtra("weight")
            tv_brand.text = intent.getStringExtra("brand")
            tv_product_desc.text = intent.getStringExtra("description")

        }
    }




    private fun showDialog() {
        dialog = Dialog(mContext!!)
        dialog!!.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog!!.setContentView(R.layout.aleert_product_detail)
        dialog!!.setCancelable(true)
        val btnYes: Button
        val btnNo: Button
        btnYes = dialog!!.findViewById(R.id.btnYes)
        btnYes.setOnClickListener {
            deleteProductAPIMethod()
            dialog!!.dismiss()
        }
        btnNo = dialog!!.findViewById(R.id.btnNo)
        btnNo.setOnClickListener { dialog!!.dismiss() }
        dialog!!.show()
    }

    private fun editDialog() {
        dialog = Dialog(mContext!!)
        dialog!!.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog!!.setContentView(R.layout.alert_edit_productdetail)
        dialog!!.setCancelable(true)
        val ivCross: ImageView
        ivCross = dialog!!.findViewById(R.id.ivCross)
        ivCross.setOnClickListener { dialog!!.dismiss() }
        val btnSubmit = dialog!!.findViewById<Button>(R.id.btnSubmit)
        val etEditPrice = dialog!!.findViewById<EditText>(R.id.et_edit_price)

        etEditPrice.setText(tv_product_price.text.toString())

        btnSubmit.setOnClickListener {
            dialog!!.dismiss()

            if (!mValidationClass!!.isNetworkConnected()) {
                showAlerterRed(resources.getString(R.string.no_internet))
            } else {
                if (etEditPrice.text.toString().isEmpty()) {

                } else {
                    val map = HashMap<String, String>()
                    map.put("id", productId)
                    map.put("mrp", etEditPrice.text.toString().trim())

                    viewModel.editPriceAPI(this, true, map)
                    viewModel.mResponse.observe(this, this)
                }
            }

         //   edit2Dialog()
        }
        dialog!!.show()
    }

    private fun edit2Dialog(minimumSellingPrice: String?) {
        dialog = Dialog(mContext!!)
        dialog!!.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog!!.setContentView(R.layout.aleet_edit_productdetail2)
        dialog!!.setCancelable(true)
        val btnOk = dialog!!.findViewById<Button>(R.id.btnOk)
        btnOk.setOnClickListener { dialog!!.dismiss()
            tv_product_price.setText(minimumSellingPrice)
        }
        dialog!!.show()
    }

    private fun deleteProductAPIMethod() {
        if (!mValidationClass!!.isNetworkConnected()) {
            showAlerterRed(resources.getString(R.string.no_internet))
        } else {
            viewModel.deleteProductAPI(mContext!!, true, productId!!)
            viewModel.mResponse.observe(this, this)
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.ivBack -> {
                onLeftIconClick()
            }
            R.id.btnDelete -> {
                showDialog()
            }
            R.id.btnEdit -> {
                editDialog()
            }
        }
    }

    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {
                if (it.data is DeleteProductResponse) {
                    val deleteProductResponse: DeleteProductResponse = it.data
                    if (deleteProductResponse.getCode() == Constants.success_code) {
                        showSuccessToast(mContext!!, deleteProductResponse!!.message)
                        var intent = Intent(mContext, HomeActivity::class.java)
                        startActivity(intent)
                        finishAffinity()

                    } else {
                        CommonMethods.AlertErrorMessage(mContext, deleteProductResponse.getMessage())
                    }
                }

                if (it.data is EditProductPriceResponse) {
                    val editProductPriceResponse: EditProductPriceResponse = it.data
                    if (editProductPriceResponse.getCode() == Constants.success_code) {
                        showSuccessToast(mContext!!, editProductPriceResponse.getMessage()!!)
                        edit2Dialog(editProductPriceResponse.getBody()!!.minimumSellingPrice)

                    } else {
                        CommonMethods.AlertErrorMessage(mContext, editProductPriceResponse.getMessage())
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