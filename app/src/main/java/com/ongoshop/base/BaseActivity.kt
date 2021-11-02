package com.ongoshop.base


import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.ongoshop.R
import com.ongoshop.utils.others.CustomProgressBar
import com.ongoshop.utils.others.ValidationsClass
import com.sdsmdg.tastytoast.TastyToast


abstract class BaseActivity : AppCompatActivity() {
    lateinit var self: Context
    protected abstract fun getContentId(): Int
    lateinit var mProgress: ProgressDialog
    lateinit var mIncludeHeader: View
    lateinit var mIvBack: ImageView
    lateinit var mTvHeader: TextView
    lateinit var mCustomProgress: CustomProgressBar;
    lateinit var mValidationClass: ValidationsClass
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        mCustomProgress = CustomProgressBar(this)
        if (getContentId() != 0) {
            setContentView(getContentId())
            //  initiate()
        }
        self = this
        mValidationClass = ValidationsClass.getInstance()
    }



/*
    private fun initiate() {
        mIncludeHeader = findViewById(R.id.include_header)
        mIvBack = mIncludeHeader.findViewById(R.id.iv_back)
        mTvHeader = mIncludeHeader.findViewById(R.id.tv_header)

        mIvBack.setOnClickListener { onLeftIconClick() }
    }
*/

    fun showProgressDialog(){
        mCustomProgress.showHideProgressBar(true)
    }
    fun hideProgressDialog(){
        mCustomProgress.hideProgressBar()
    }

    fun showSuccessToast(activity: Activity, message: String) {
        TastyToast.makeText(activity, message, TastyToast.LENGTH_SHORT, TastyToast.SUCCESS)

    }

    fun showErrorToast(activity: Activity, message: String) {
        TastyToast.makeText(activity, message, TastyToast.LENGTH_SHORT, TastyToast.ERROR)
    }
    fun onLeftIconClick() {
        onBackPressed()
        overridePendingTransition(R.anim.zoom_enter, R.anim.slide_to_right)
//        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    fun setHeader(title: String) {
        mTvHeader.text = title
    }


    // show the common progress which is used in all application


/*
    fun gotoActivity(intent: Intent) {
        intent.putExtra("classFrom", self.javaClass.getSimpleName())
        startActivity(intent)
        overridePendingTransition(R.anim.slide_from_right, R.anim.zoom_exit)
    }

    fun gotoActivityWithResult(cla: Class<*>, code: Int) {
        val intent = Intent(this, cla)
        intent.putExtra("classFrom", self.javaClass.getSimpleName())
        startActivityForResult(intent, code)
    }

    fun gotoActivityClearTask(cla: Class<*>) {
        val intent = Intent(this, cla)
        intent.putExtra("classFrom", self.javaClass.getSimpleName())
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
*/

    fun gotoActivity(cla: Class<*>, bundle: Bundle) {
        val intent = Intent(this, cla)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    fun gotoActivityWithResult(cla: Class<*>, bundle: Bundle, code: Int) {
        val intent = Intent(this, cla)
        intent.putExtras(bundle)
        startActivityForResult(intent, code)
    }

    // hide keyboard
    protected fun hideKeyboard(view: View?) {
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun showAlerterGreen(msg: String) {
        mValidationClass.AlerterGreen(this, msg)
    }

    fun showAlerterRed(msg: String) {
        mValidationClass.Alerter(this, msg)
    }

    fun modelToString(response: Any):String
    {
        return Gson().toJson(response)
    }

    fun stringToModel(response: String, modelName: Class<*>):Any
    {
        return Gson().fromJson(response,modelName)
    }

}
