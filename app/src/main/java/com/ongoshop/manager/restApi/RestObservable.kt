package com.ongoshop.manager.restApi

import android.annotation.TargetApi
import android.app.Activity
import android.app.ProgressDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import com.sdsmdg.tastytoast.TastyToast
import com.ongoshop.activities.LoginActivity
import com.ongoshop.utils.dialog.ProgressHUD
import com.ongoshop.utils.others.Constants
import com.ongoshop.utils.others.MyApplication
import com.ongoshop.utils.others.SharedPrefUtil
import com.ongoshop.manager.restApi.ServiceGenerator
import com.ongoshop.manager.restApi.Status


import io.reactivex.annotations.NonNull
import okhttp3.ResponseBody
import java.io.IOException


class RestObservable(
        val status: Status,
        val data: Any?,
        val error: Any?
) {


    companion object {
        var mProgressDialog: ProgressHUD? = null
        var mprgressDialog: ProgressDialog? = null
        var IsCheckedRememberMe = false
        var mEmail = ""
        var mPassword = ""
        var mSocialType = ""

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        fun loading(activity: Activity, showLoader: Boolean): RestObservable {
            if (showLoader) {
                /*    if (mDialog == null)
                        mDialog = DialogClass(activity)
                    if (mprgressDialog == null)
                        mprgressDialog = mDialog!!.getProgressDialog(activity,"","")
                    else
                        mprgressDialog!!.show()*/
                mProgressDialog = ProgressHUD.create(activity)
                mProgressDialog!!.show()

            }

            Log.e("REST", "Loading")
            return RestObservable(Status.LOADING, null, null)
        }

        fun success(@NonNull data: Any): RestObservable {
            if (mProgressDialog != null)
                mProgressDialog!!.dismiss()
//            if (mprgressDialog != null)
//                mprgressDialog!!.dismiss()
            Log.e("REST", "Success")
            return RestObservable(Status.SUCCESS, data, null)
        }

        fun error(activity: Activity, @NonNull error: Throwable): RestObservable {
            Log.e("REST", "Error")
            if (mProgressDialog != null && mProgressDialog!!.isShowing)
                mProgressDialog!!.dismiss()
//            if (mprgressDialog != null)
//                mprgressDialog!!.dismiss()
            try {
                // We had non-200 http error
                if (error is HttpException) {
                    val httpException = error as HttpException
                    val response = httpException.response()
                    Log.i(TAG, error.message() + " / " + error.javaClass)
                    return RestObservable(
                        Status.ERROR,
                        null,
                        callErrorMethod(activity, response.errorBody())
                    )
                }
                // A network error happened
                if (error is IOException) {
                    Log.i(TAG, error.message + " / " + error.javaClass)
                    Log.e("Errror123", error.message!!)
                    Log.e("StatusERROR", Status.ERROR.toString())
                    Log.e("StatusJAVA", error.javaClass.toString())


//                    return RestObservable(Status.ERROR, null, error)
                    return RestObservable(Status.ERROR, null, "Server not responding")
                }

                Log.i(TAG, error.message + " / " + error.javaClass)
            } catch (e: Exception) {
                Log.i(TAG, e.message!!)
                return RestObservable(Status.ERROR, null, error)
            }

            return RestObservable(Status.ERROR, null, error)
        }

        fun callErrorMethod(activity: Activity, responseBody: ResponseBody?): String {

            val converter = ServiceGenerator.getRetrofit()
                .responseBodyConverter<RestError>(
                    RestError::class.java,
                    arrayOfNulls<Annotation>(0)
                )
            try {
                val errorResponse = converter.convert(responseBody!!)
                val error_message = errorResponse!!.message
                if (errorResponse.code == Constants.errorCode) {
                    if (errorResponse.message!!.contains("Authorization is required.")) {
                        MyApplication.getnstance()
                            .clearData()
                        MyApplication.instance!!.clearData()
                        SharedPrefUtil.getInstance().clear()
                        SharedPrefUtil.getInstance().isLogin = false
                        val intent = Intent(activity, LoginActivity::class.java)
                        activity.startActivity(intent)
                        activity.finishAffinity()

                        TastyToast.makeText(
                            activity,
                            "Session Expired !Login Again ",
                            TastyToast.LENGTH_LONG,
                            TastyToast.WARNING
                        )
                    }

                    else if (errorResponse.message!!.contains("User Authentication Failed")) {
                        MyApplication.getnstance()
                            .clearData()
                        MyApplication.instance!!.clearData()
                        SharedPrefUtil.getInstance().clear()
                        SharedPrefUtil.getInstance().isLogin = false

                        val intent = Intent(activity, LoginActivity::class.java)
                        activity.startActivity(intent)
                        activity.finishAffinity()

                        TastyToast.makeText(
                            activity,
                            "Session Expired !Login Again ",
                            TastyToast.LENGTH_LONG,
                            TastyToast.WARNING
                        )
                    }
                    else if (errorResponse.message!!.contains("Not Found")) {

                        TastyToast.makeText(
                            activity,
                            "No more user found",
                            TastyToast.LENGTH_LONG,
                            TastyToast.ERROR
                        )
                    }

                    else  if (errorResponse.code == Constants.errorCode) {
                        if (errorResponse.message!!.contains("User Authentication failed.")) {
                            MyApplication.getnstance()
                                .clearData()
                            val intent = Intent(activity, LoginActivity::class.java)
                            activity.startActivity(intent)
                            activity.finishAffinity()
                            MyApplication.instance!!.clearData()
                            SharedPrefUtil.getInstance().clear()
                            SharedPrefUtil.getInstance().isLogin = false

                            TastyToast.makeText(
                                activity,
                                "Session Expired !Login Again ",
                                TastyToast.LENGTH_LONG,
                                TastyToast.WARNING
                            )
                        }

                    }
                }
                return error_message!!
            } catch (e: IOException) {
                return "Server not responding"
//                return e.toString()
            }

        }
    }
}
