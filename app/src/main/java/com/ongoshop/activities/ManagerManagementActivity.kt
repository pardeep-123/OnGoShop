package com.ongoshop.activities

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.ongoshop.R
import com.ongoshop.base.BaseActivity
import com.ongoshop.fragments.EmployeeFragment
import com.ongoshop.fragments.ManagerManagementFragment

class ManagerManagementActivity : BaseActivity(), View.OnClickListener {

    var mContext: ManagerManagementActivity? = null
    var v: View? = null
    var ivBack: ImageView? = null
    var btnEmployee: Button? = null
    var btnManager: Button? = null
    override fun getContentId(): Int {
        return R.layout.activity_manager_managment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        ivBack = findViewById(R.id.ivBack)
        btnEmployee = findViewById(R.id.btnEmployee)
        btnManager = findViewById(R.id.btnManager)

        changeFragment(EmployeeFragment())

        btnEmployee!!.setOnClickListener(mContext)
        ivBack!!.setOnClickListener(mContext)
        btnManager!!.setOnClickListener(mContext)
    }

    private fun changeFragment(fragment: Fragment?) {
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.container, fragment!!).commit()
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.ivBack ->{
                onLeftIconClick()
            }
        R.id.btnEmployee ->{
            changeFragment(EmployeeFragment())
            btnEmployee!!.setTextColor(resources.getColor(R.color.white))
            btnManager!!.setTextColor(resources.getColor(R.color.grey))
            }
        R.id.btnManager ->{
            changeFragment(ManagerManagementFragment())
            btnManager!!.setTextColor(ContextCompat.getColor(applicationContext, R.color.white))
            btnEmployee!!.setTextColor(resources.getColor(R.color.grey))
            }
        }
    }
}