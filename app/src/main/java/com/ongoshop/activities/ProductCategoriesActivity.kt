package com.ongoshop.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.ongoshop.R
import com.ongoshop.base.BaseActivity

class ProductCategoriesActivity : BaseActivity(), View.OnClickListener {
    var mContext: ProductCategoriesActivity? = null
    var btnSelection: Button? = null
    var ivBack: ImageView? = null
    override fun getContentId(): Int {
        return R.layout.activity_product_categories
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        ivBack = findViewById(R.id.ivBack)
        btnSelection = findViewById(R.id.btnSelection)

        ivBack!!.setOnClickListener(mContext)
        btnSelection!!.setOnClickListener(mContext)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.ivBack -> {
                onLeftIconClick()
            }
            R.id.btnSelection -> {
                val i = Intent(mContext, AddCategoryActivity::class.java)
                startActivity(i)
            }
        }
    }

}