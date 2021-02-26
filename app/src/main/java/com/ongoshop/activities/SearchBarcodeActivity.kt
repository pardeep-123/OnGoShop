package com.ongoshop.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ongoshop.R
import com.ongoshop.base.BaseActivity
import com.ongoshop.manager.restApi.RestObservable
import com.ongoshop.manager.restApi.Status
import com.ongoshop.pojo.GetProductbarcodeResponse
import com.ongoshop.utils.others.CommonMethods
import com.ongoshop.utils.others.Constants
import com.ongoshop.utils.others.MyApplication
import com.ongoshop.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.activity_search_barcode.*

class SearchBarcodeActivity : BaseActivity(), Observer<RestObservable> {
    var mContext: SearchBarcodeActivity? = null
    var btnSearchBarcode: Button? = null
    var ivBack: ImageView? = null
    private val viewModel: HomeViewModel
            by lazy { ViewModelProviders.of(this).get(HomeViewModel::class.java) }


    override fun getContentId(): Int {
        return R.layout.activity_search_barcode
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        ivBack = findViewById(R.id.ivBack)
        btnSearchBarcode = findViewById(R.id.btnSearchBarcode)
        btnSearchBarcode = findViewById(R.id.btnSearchBarcode)
        ivBack!!.setOnClickListener {finish() }

        btnSearchBarcode!!.setOnClickListener{
            getProductByBarCodeApi()
        }
    }

    private fun getProductByBarCodeApi() {
        if (!MyApplication.instance!!.checkIfHasNetwork())
            showAlerterRed(resources.getString(R.string.no_internet))
        else if (mValidationClass.checkStringNull(et_bar_code_number.text.toString())){
            showAlerterRed(resources.getString(R.string.please_enter_bar_code))
        }else{
            val map = HashMap<String, String>()
            map.put("barcode", et_bar_code_number.text.toString())

            viewModel.getProductBarcodeAPI(this, true, map)
            viewModel.mResponse.observe(this, this)
        }
    }

    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {
                if (it.data is GetProductbarcodeResponse) {
                    val getProductbarcodeResponse: GetProductbarcodeResponse = it.data
                    if (getProductbarcodeResponse.code == Constants.success_code) {
                        showSuccessToast(mContext!!, getProductbarcodeResponse!!.getMessage()!!)
                        val intent = Intent(mContext, AddProductActivity::class.java)
                        intent.putExtra("from", "BarcodeScan")
                        intent.putExtra("categoryId", getProductbarcodeResponse!!.body.categoryId.toString())
                        intent.putExtra("brandName", getProductbarcodeResponse!!.body.brandName)
                        intent.putExtra("name", getProductbarcodeResponse!!.body.name)
                        intent.putExtra("image", getProductbarcodeResponse!!.body.image)
                        intent.putExtra("barcode", getProductbarcodeResponse!!.body.barcode)
                        intent.putExtra("isBarcodeItem", getProductbarcodeResponse!!.body.isBarcodeItem.toString())
                        intent.putExtra("mrp", getProductbarcodeResponse!!.body.mrp)
                        intent.putExtra("gtinNumber", getProductbarcodeResponse!!.body.gtinNumber)
                        intent.putExtra("countryOfOrigin", getProductbarcodeResponse!!.body.countryOfOrigin)
                        intent.putExtra("description", getProductbarcodeResponse!!.body.description)
                        intent.putExtra("isAvailable", getProductbarcodeResponse!!.body.isAvailable.toString())
                        intent.putExtra("weight", getProductbarcodeResponse!!.body.weight)
                        intent.putExtra("weightUnit", getProductbarcodeResponse!!.body.weightUnit.toString())
                        startActivity(intent)

                    } else {
                        CommonMethods.AlertErrorMessage(mContext, getProductbarcodeResponse.getMessage())
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