package com.ongoshop.viewmodel

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ongoshop.manager.restApi.RestObservable
import com.ongoshop.pojo.EditProfileResponse
import com.ongoshop.utils.others.MyApplication
import com.ongoshop.utils.others.ValidationsClass
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class MyAccountViewModel :ViewModel() {

    private val TAG = MyAccountViewModel::class.java.name
    val restApiInterface = MyApplication.getnstance().provideAuthservice()
    var mResponse: MutableLiveData<RestObservable> = MutableLiveData()


    fun changePassword(activity: Activity, showLoader:Boolean,
                 map: HashMap<String, String>
    ) {
        restApiInterface.changePassword(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { mResponse.value = RestObservable.loading(activity,showLoader) }
                .subscribe(
                        { mResponse.value = RestObservable.success(it) },
                        { mResponse.value = RestObservable.error(activity,it) }
                )
    }

    fun logoutApi(activity: Activity, showLoader:Boolean, map: HashMap<String, String>) {
        restApiInterface.getLogout(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { mResponse.value = RestObservable.loading(activity,showLoader) }
                .subscribe(
                        { mResponse.value = RestObservable.success(it) },
                        { mResponse.value = RestObservable.error(activity,it) }
                )
    }


    fun editProfile(
            activity: Activity, showLoader: Boolean,
            map: HashMap<String, RequestBody>,
            mImage: String,
            mValidationClass: ValidationsClass
    ) {
        lateinit var profileImageFileBody: MultipartBody.Part
        var updateProfile: Observable<EditProfileResponse>? = null
        if (!mValidationClass.checkStringNull(mImage)) {
            val file = File(mImage)
            profileImageFileBody = mValidationClass.prepareFilePart("image", file)
            updateProfile =  restApiInterface.editProfile(map,profileImageFileBody)
        }else
        {
            updateProfile =  restApiInterface.updateProfileWithoutImage(map)
        }
        updateProfile!!.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { mResponse.value = RestObservable.loading(activity, showLoader) }
                .subscribe(
                        { mResponse.value = RestObservable.success(it) },
                        { mResponse.value = RestObservable.error(activity, it) }
                )
    }

    fun getTermsConditionsAPI(activity: Activity, showLoader:Boolean

    ) {
        restApiInterface.termsCondition()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { mResponse.value = RestObservable.loading(activity,showLoader) }
                .subscribe(
                        { mResponse.value = RestObservable.success(it) },
                        { mResponse.value = RestObservable.error(activity,it) }
                )
    }

    fun getPrivacyPolicyAPI(activity: Activity, showLoader:Boolean

    ) {
        restApiInterface.privacyPolicy()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { mResponse.value = RestObservable.loading(activity,showLoader) }
                .subscribe(
                        { mResponse.value = RestObservable.success(it) },
                        { mResponse.value = RestObservable.error(activity,it) }
                )
    }

    fun getAboutUsAPI(activity: Activity, showLoader:Boolean

    ) {
        restApiInterface.aboutUs()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { mResponse.value = RestObservable.loading(activity,showLoader) }
                .subscribe(
                        { mResponse.value = RestObservable.success(it) },
                        { mResponse.value = RestObservable.error(activity,it) }
                )
    }


}