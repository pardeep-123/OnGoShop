package com.ongoshop.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
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
import me.dm7.barcodescanner.zbar.Result
import me.dm7.barcodescanner.zbar.ZBarScannerView.ResultHandler


class BarcodeScannerActivity : BaseActivity(), View.OnClickListener, Observer<RestObservable> {
    var mContext: BarcodeScannerActivity? = null
    var ivBack: ImageView? = null
    var ivImg: ImageView? = null
    var btnSearch: Button? = null
    var btnItem: Button? = null
    var llBarcode: RelativeLayout? = null
    var LAUNCH_SECOND_ACTIVITY = 1

    private var barcodeNumber = ""
    private val viewModel: HomeViewModel
            by lazy { ViewModelProviders.of(this).get(HomeViewModel::class.java) }

    override fun getContentId(): Int {
        return R.layout.activity_barcode_scanner
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_barcode_scanner)
        mContext = this
        ivBack = findViewById(R.id.ivBack)
        ivBack!!.setOnClickListener(mContext)
        btnItem = findViewById(R.id.btnItem)
        ivImg = findViewById(R.id.ivImg)
        llBarcode = findViewById(R.id.llBarcode)
        llBarcode!!.setOnClickListener(mContext)

        btnSearch = findViewById(R.id.btnSearch)
        btnSearch!!.setOnClickListener(mContext)
        btnItem!!.setOnClickListener(mContext)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == LAUNCH_SECOND_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                barcodeNumber = data!!.getStringExtra("barcodeNumber")!!
                getProductByBarCodeApi()

            }
        }
        if (resultCode == Activity.RESULT_CANCELED) {
            //Write your code if there's no result
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.ivBack -> {
                finish()
            }
            R.id.btnSearch -> {
                val i = Intent(mContext, ScannerActivity::class.java)
                startActivityForResult(i, LAUNCH_SECOND_ACTIVITY)
            }
            R.id.btnItem -> {
                val i = Intent(mContext, SearchBarcodeActivity::class.java)
                startActivity(i)
            }
            R.id.llBarcode -> {

            }
        }
    }


    private fun getProductByBarCodeApi() {
        if (!MyApplication.instance!!.checkIfHasNetwork())
            showAlerterRed(resources.getString(R.string.no_internet))
        else {
            val map = HashMap<String, String>()
            map.put("barcode", barcodeNumber)

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