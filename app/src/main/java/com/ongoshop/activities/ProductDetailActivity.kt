package com.ongoshop.activities

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.ongoshop.R
import com.ongoshop.base.BaseActivity

class ProductDetailActivity : BaseActivity(), View.OnClickListener {
    var mContext: ProductDetailActivity? = null
    var btnEdit: Button? = null
    var btnDelete: Button? = null
    var ivBack: ImageView? = null
    var dialog: Dialog? = null
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
    }

    private fun showDialog() {
        dialog = Dialog(mContext!!)
        dialog!!.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog!!.setContentView(R.layout.aleert_product_detail)
        dialog!!.setCancelable(true)
        val btnYes: Button
        val btnNo: Button
        btnYes = dialog!!.findViewById(R.id.btnYes)
        btnYes.setOnClickListener { dialog!!.dismiss() }
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
        btnSubmit.setOnClickListener {
            dialog!!.dismiss()
            edit2Dialog()
        }
        dialog!!.show()
    }

    private fun edit2Dialog() {
        dialog = Dialog(mContext!!)
        dialog!!.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog!!.setContentView(R.layout.aleet_edit_productdetail2)
        dialog!!.setCancelable(true)
        val btnOk = dialog!!.findViewById<Button>(R.id.btnOk)
        btnOk.setOnClickListener { dialog!!.dismiss() }
        dialog!!.show()
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

}