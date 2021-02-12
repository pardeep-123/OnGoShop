package com.ongoshop.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.ongoshop.R
import com.ongoshop.base.BaseActivity

class SubCategoriesActivity : BaseActivity() {
    var ivBack: ImageView? = null
    var mContext: Context? = null
    var ll_data: LinearLayout? = null
    override fun getContentId(): Int {
       return R.layout.activity_sub_categories
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        ivBack = findViewById(R.id.ivBack)
        ll_data = findViewById(R.id.ll_data)
        ll_data!!.setOnClickListener(View.OnClickListener {
            val i = Intent(this@SubCategoriesActivity, ProductActivity::class.java)
            startActivity(i)
        })
    }
}