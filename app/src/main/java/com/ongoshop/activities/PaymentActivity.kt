package com.ongoshop.activities

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import com.ongoshop.R
import com.ongoshop.base.BaseActivity
import kotlinx.android.synthetic.main.activity_payment.*

class PaymentActivity : BaseActivity(), View.OnClickListener {
    var mContext: PaymentActivity? = null
    var btnPay: Button? = null
    var dialog: Dialog? = null
    var ll_card: LinearLayout? = null

    override fun getContentId(): Int {
        return R.layout.activity_payment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        btnPay = findViewById(R.id.btnPay)
        ll_card = findViewById(R.id.ll_card)

        ivBack.setOnClickListener(mContext)
        btnPay!!.setOnClickListener(mContext)
        ll_card!!.setOnClickListener(mContext)

    }

    private fun showDailog() {
        dialog = Dialog(mContext!!)
        dialog!!.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog!!.setContentView(R.layout.alert_payment)
        dialog!!.setCancelable(true)
        val btnDone = dialog!!.findViewById<Button>(R.id.btnDone)
        btnDone.setOnClickListener {
            mContext!!.startActivity(Intent(mContext, HomeActivity::class.java))
            dialog!!.dismiss()
        }
        dialog!!.show()
    }
    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.ivBack ->{
                onLeftIconClick()
            }
         R.id.btnPay ->{
                showDailog()
            }
         R.id.ll_card ->{
             val intent = Intent(mContext, AddCardActivity::class.java)
             startActivity(intent)
            }
        }
    }

}