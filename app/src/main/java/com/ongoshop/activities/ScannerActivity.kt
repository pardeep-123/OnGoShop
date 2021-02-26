package com.ongoshop.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.ongoshop.R
import com.ongoshop.base.BaseActivity
import me.dm7.barcodescanner.zbar.Result
import me.dm7.barcodescanner.zbar.ZBarScannerView
import me.dm7.barcodescanner.zbar.ZBarScannerView.ResultHandler


class ScannerActivity : BaseActivity(), ResultHandler {
    var mContext: ScannerActivity? = null

    private var mScannerView: ZBarScannerView? = null


    override fun getContentId(): Int {
        return R.layout.activity_scan_qr_code
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_barcode_scanner)
        mContext = this

        mScannerView = ZBarScannerView(this) // Programmatically initialize the scanner view

        setContentView(mScannerView)

        mScannerView!!.setResultHandler(this) // Register ourselves as a handler for scan results.

        mScannerView!!.startCamera() // Start camera on resume


    }


    override fun handleResult(rawResult: Result?) {
        Log.d("TAG123", "" + rawResult!!.contents) // Prints scan results
        Log.v("TAG321", "" + rawResult!!.barcodeFormat.name) // Prints the scan format (qrcode, pdf417 etc.)

        showAlerterGreen(rawResult!!.contents)
        // If you would like to resume scanning, call this method below:
        //mScannerView.resumeCameraPreview(this);


        // If you would like to resume scanning, call this method below:
        //mScannerView.resumeCameraPreview(this);
        if (rawResult!!.contents != null) {
            val returnIntent = Intent()
            returnIntent.putExtra("barcodeNumber", rawResult!!.contents.toString())
            setResult(Activity.RESULT_OK, returnIntent)
            finish()

        } else {
            showErrorToast(mContext!!, "Could not read the bar. Please enter barcode manually.")
            val i = Intent(mContext, SearchBarcodeActivity::class.java)
            startActivity(i)
            finish()
        }
    }




    override fun onDestroy() {
        super.onDestroy()
        mScannerView!!.stopCamera()
    }

    override fun onPause() {
        super.onPause()
        mScannerView!!.stopCamera()
    }


}