package com.ongoshop.activities

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
import com.ongoshop.adapter.ProductAdapter
import com.ongoshop.base.BaseActivity

class ProductActivity : BaseActivity(), View.OnClickListener {
    var ivBack: ImageView? = null
    var mContext: ProductActivity? = null
    var btnAddProducts: Button? = null
    var recyclerview: RecyclerView? = null
    var productAdapter: ProductAdapter? = null
    override fun getContentId(): Int {
        return R.layout.activity_product
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        ivBack = findViewById(R.id.ivBack)
        btnAddProducts = findViewById(R.id.btnAddProducts)
        recyclerview = findViewById(R.id.recyclerview)

        btnAddProducts!!.setOnClickListener(mContext)
        ivBack!!.setOnClickListener(this)

        productAdapter = ProductAdapter(mContext!!)
        recyclerview!!.setLayoutManager(LinearLayoutManager(mContext))
        recyclerview!!.setAdapter(productAdapter)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.ivBack -> {
                onLeftIconClick()
            }
            R.id.btnAddProducts -> {
                val i = Intent(mContext, AddProductActivity::class.java)
                startActivity(i)
            }
        }
    }

}