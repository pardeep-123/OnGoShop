package com.ongoshop.activities

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ongoshop.R
import com.ongoshop.adapter.DetailsAdapter
import com.ongoshop.base.BaseActivity

class DetailActivity : BaseActivity(), View.OnClickListener {

    var ivBack: ImageView? = null
    var mContext: DetailActivity? = null
    var btnFinish: Button? = null
    var dialog: Dialog? = null
    var recyclerview: RecyclerView? = null
    var detailsAdapter: DetailsAdapter? = null

    override fun getContentId(): Int {
        return R.layout.activity_detail
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        ivBack = findViewById(R.id.ivBack)
        btnFinish = findViewById(R.id.btnFinish)
        recyclerview = findViewById(R.id.recyclerview)

        ivBack!!.setOnClickListener(mContext)
        btnFinish!!.setOnClickListener(mContext)

        detailsAdapter = DetailsAdapter(mContext!!)
        recyclerview!!.layoutManager = LinearLayoutManager(mContext)
        recyclerview!!.adapter = detailsAdapter
    }

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

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.ivBack ->{
              onLeftIconClick()
            }
            R.id.btnFinish ->{
                showDailog()
            }
        }
    }
}