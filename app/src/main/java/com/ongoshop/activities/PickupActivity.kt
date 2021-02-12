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
import com.ongoshop.adapter.PickupAdapter
import com.ongoshop.base.BaseActivity

class PickupActivity : BaseActivity(), View.OnClickListener  {
    var ivBack: ImageView? = null
    var mContext: PickupActivity? = null
    var btnFinish: Button? = null
    var recyclerview: RecyclerView? = null
    var pickupAdapter: PickupAdapter? = null
    override fun getContentId(): Int {
        return R.layout.activity_pickup
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        ivBack = findViewById(R.id.ivBack)

        ivBack!!.setOnClickListener(View.OnClickListener { finish() })

        recyclerview = findViewById(R.id.recyclerview)
        pickupAdapter = PickupAdapter(mContext!!)
        recyclerview!!.setLayoutManager(LinearLayoutManager(mContext))
        recyclerview!!.setAdapter(pickupAdapter)
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.ivBack ->{
                onLeftIconClick()
            }
        }
    }

}