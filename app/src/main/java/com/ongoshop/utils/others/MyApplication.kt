package com.ongoshop.utils.others

import android.app.AlertDialog
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.location.LocationManager
import android.net.ConnectivityManager
import android.preference.PreferenceManager
import android.provider.Settings
import com.ongoshop.activities.LoginActivity
import com.ongoshop.R
import com.ongoshop.manager.restApi.RestApiInterface
import com.ongoshop.manager.restApi.ServiceGenerator
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumConfig
import java.util.*

/*
import com.google.android.libraries.places.api.Places;
*/
class MyApplication : Application() {
    var preferences: SharedPreferences? = null
    var prefToken: SharedPreferences? = null
    var editor: SharedPreferences.Editor? = null
    var editorToken: SharedPreferences.Editor? = null
    override fun onCreate() {
        super.onCreate()
        instance = this
        initializePreferences()
        initializePreferencesToken()
        Album.initialize(
            AlbumConfig.newBuilder(this)
                .setAlbumLoader(MediaLoader())
                .setLocale(Locale.getDefault())
                .build()
        )
    }

    var restApiInterface: RestApiInterface? = null
    fun provideAuthservice(): RestApiInterface {
        if (restApiInterface == null) {
            restApiInterface = ServiceGenerator.createService(RestApiInterface::class.java)
            return restApiInterface!!
        } else {
            return restApiInterface!!
        }

    }


    fun checkIfHasNetwork(): Boolean {
        val cm =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    override fun onTerminate() {
        super.onTerminate()
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
    }

    // initialize shared preferences
    private fun initializePreferences() {
        preferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        editor = preferences!!.edit()
    }

    // initialize shared preferences for token
    private fun initializePreferencesToken() {
        prefToken = getSharedPreferences(
            PREF_TOKEN,
            Context.MODE_PRIVATE
        )
        editorToken = prefToken!!.edit()
    }

    fun setString(key: String?, value: String?) {
        editor!!.putString(key, value)
        editor!!.commit()
    }

    fun getString(key: String?): String? {
        return preferences!!.getString(key, "")
    }

    fun setInt(key: String?, value: Int) {
        editor!!.putInt(key, value)
        editor!!.commit()
    }

    fun getInt(key: String?): Int {
        return preferences!!.getInt(key, 0)
    }

    fun clearData() {
      editor!!.clear().commit()
    }

    fun setDeviceToken(key: String?, value: String?) {
        editorToken!!.putString(key, value)
        editorToken!!.commit()
    }

    fun getDeviceToken(key: String?, def_value: String?): String? {
        return prefToken!!.getString(key, def_value)
    }

    fun tokenExpired() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    val isGpsOn: Boolean
        get() {
            var isGpsOn = false
            val locationManager =
                applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            isGpsOn = if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                false
            } else {
                true
            }
            return isGpsOn
        }

    fun showGPSDisabledAlertToUser(context: Context?) {
        val alertDialogBuilder =
            AlertDialog.Builder(context)
        alertDialogBuilder.setMessage(resources.getString(R.string.gps_disable))
            .setCancelable(false)
            .setPositiveButton(
                resources.getString(R.string.turn_on)
            ) { dialog, id ->
                val callGPSSettingIntent =
                    Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                callGPSSettingIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(callGPSSettingIntent)
                dialog.cancel()
            }

//        alertDialogBuilder.setNegativeButton(getResources().getString(R.string.cancel),
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        dialog.cancel();
//                    }
//                });
        val alert = alertDialogBuilder.create()
        alert.setOnShowListener {
            alert.getButton(AlertDialog.BUTTON_POSITIVE)
                .setTextColor(Color.parseColor("#000000"))
        }
        alert.show()
    }

    companion object {
        val TAG = MyApplication::class.java.simpleName
        private val PREF_NAME = "MyPref"
        var instance: MyApplication? = null
            private set
        const val PREF_TOKEN = "TruTraits"
        fun hasNetwork(): Boolean {
            return instance!!.checkIfHasNetwork()
        }
        fun getnstance(): MyApplication {
            return instance!!
        }
    }
}