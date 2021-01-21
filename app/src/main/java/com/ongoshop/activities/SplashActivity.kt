package com.ongoshop.activities

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Base64
import android.util.Log
import android.util.SparseIntArray
import android.view.View
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.iid.FirebaseInstanceId
import com.ongoshop.activities.HomeActivity
import com.ongoshop.R
import com.ongoshop.base.BaseActivity
import com.ongoshop.utils.others.SharedPrefUtil
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


class SplashActivity : BaseActivity() {

    lateinit var handler: Handler
    lateinit var mContext: SplashActivity

    private var mErrorString: SparseIntArray? = null
    private val REQUEST_PERMISSIONS = 20
    internal var sharedPreferences: SharedPreferences? = null
    internal var editor: SharedPreferences.Editor? = null
    internal var isFirstTime = ""


    override fun getContentId(): Int {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this

        mErrorString = SparseIntArray()

        val currentapiVersion = Build.VERSION.SDK_INT
        // if current version is M o sar greater than M
        if (currentapiVersion >= Build.VERSION_CODES.M) {
            val array = arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            requestAppPermissions(array, R.string.permission, REQUEST_PERMISSIONS)
        } else {
            onPermissionsGranted(REQUEST_PERMISSIONS)
        }


    }

    fun requestAppPermissions(
        requestedPermissions: Array<String>,
        stringId: Int,
        requestCode: Int
    ) {
        mErrorString!!.put(requestCode, stringId)
        var permissionCheck = PackageManager.PERMISSION_GRANTED
        var shouldShowRequestPermissionRationale = false
        for (permission in requestedPermissions) {
            permissionCheck =
                permissionCheck + ContextCompat.checkSelfPermission(mContext, permission)
            shouldShowRequestPermissionRationale =
                shouldShowRequestPermissionRationale || ActivityCompat.shouldShowRequestPermissionRationale(
                    mContext,
                    permission
                )
        }
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale) {
                val snack = Snackbar.make(
                    findViewById(android.R.id.content),
                    stringId,
                    Snackbar.LENGTH_INDEFINITE
                )
                val view = snack.getView()
                val tv: TextView = view.findViewById(R.id.snackbar_text)
                tv.setTextColor(Color.WHITE)
                snack.setAction(
                    "GRANT",
                    View.OnClickListener {
                        ActivityCompat.requestPermissions(
                            mContext,
                            requestedPermissions,
                            requestCode
                        )
                    }).show()
            } else {
                ActivityCompat.requestPermissions(mContext, requestedPermissions, requestCode)
            }
        } else {
            onPermissionsGranted(requestCode)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        var permissionCheck = PackageManager.PERMISSION_GRANTED
        for (permission in grantResults) {
            permissionCheck = permissionCheck + permission
        }
        if (grantResults.size > 0 && permissionCheck == PackageManager.PERMISSION_GRANTED) {
            onPermissionsGranted(requestCode)
        } else {
            onPermissionsGranted(requestCode)
        }
    }

    // if permissions granted succesfully
    private fun onPermissionsGranted(requestcode: Int) {
        gotoDashBoardOrNot()
    }

    private fun gotoDashBoardOrNot() {
        handler = Handler()

        val r = Runnable {

            if (SharedPrefUtil.getInstance().isLogin == null) {

                val intent = Intent(mContext, LoginActivity::class.java)
                startActivity(intent)
                finishAffinity()

            } else if (SharedPrefUtil.getInstance().isLogin == true) {
                val intent = Intent(mContext, HomeActivity::class.java)
                startActivity(intent)
                finishAffinity()
            } else {
                val intent = Intent(mContext, LoginActivity::class.java)
                    startActivity(intent)
                    finishAffinity()

            }
           Log.e("isLogin", SharedPrefUtil.getInstance().isLogin.toString())
        }

        handler.postDelayed(r, 2000)

        printKeyHash(mContext)



        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    //Log.w(TAG, "getInstanceId failed", task.exception)
                    return@OnCompleteListener
                }

                // Get new Instance ID token
                var token = task.result?.token

                if (!token!!.isEmpty()) {

           //         sendRegistrationToServer(token!!)
                }
                Log.e("token_sssplashss", token)

                // Log and toast
                // Log.d(TAG, msg)
                // Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
            })

    }

    fun printKeyHash(context: Activity): String? {
        val packageInfo: PackageInfo
        var key: String? = null
        try { //getting application package name, as defined in manifest
            val packageName = context.applicationContext.packageName
            //Retriving package info
            packageInfo = context.packageManager.getPackageInfo(
                packageName,
                PackageManager.GET_SIGNATURES
            )
            Log.e("Package gjfghjfghName=", context.applicationContext.packageName)
            for (signature in packageInfo.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                key = String(Base64.encode(md.digest(), 0))
                Log.e("key_hash", key)
            }
        } catch (e1: PackageManager.NameNotFoundException) {
            Log.e("Name not found", e1.toString())
        } catch (e: NoSuchAlgorithmException) {
            Log.e("No such an algorithm", e.toString())
        } catch (e: Exception) {
            Log.e("Exception", e.toString())
        }
        return key
    }

}
