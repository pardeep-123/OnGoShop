package com.ongoshop.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.ongoshop.R
import com.ongoshop.base.BaseActivity

class AddCategoryActivity : BaseActivity(), View.OnClickListener {
    var btnItem: Button? = null
    var mContext: AddCategoryActivity? = null
    var edSelect: EditText? = null
    var ivBack: ImageView? = null
    override fun getContentId(): Int {
        return R.layout.activity_add_category
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mContext = this
        btnItem = findViewById(R.id.btnItem)
        ivBack = findViewById(R.id.ivBack)
        edSelect = findViewById(R.id.edSelect)

        edSelect!!.setOnClickListener(mContext)
        ivBack!!.setOnClickListener(mContext)
        btnItem!!.setOnClickListener(mContext)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.ivBack -> {
                onLeftIconClick()
            }
            R.id.btnItem -> {
                val i = Intent(mContext, AddProductActivity::class.java)
                startActivity(i)
            }
            R.id.edSelect -> {
                val i = Intent(mContext, ProductCategoriesActivity::class.java)
                startActivity(i)
            }
        }
    }
}