package com.ongoshop.viewmodel

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ongoshop.manager.restApi.RestObservable
import com.ongoshop.pojo.EditProfileAddShopResponsess
import com.ongoshop.utils.others.MyApplication
import com.ongoshop.utils.others.ValidationsClass
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.util.*

class AuthViewModel :ViewModel() {
    private val TAG = AuthViewModel::class.java.name
    val restApiInterface = MyApplication.getnstance().provideAuthservice()
    var mResponse: MutableLiveData<RestObservable> = MutableLiveData()

    fun signUpApi(
            activity: Activity, showLoader: Boolean,
            map: HashMap<String, RequestBody>,
            mImage: MultipartBody.Part
    )
    {
        restApiInterface.signUp(map,mImage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { mResponse.value = RestObservable.loading(activity, showLoader) }
                .subscribe(
                        { mResponse.value = RestObservable.success(it) },
                        { mResponse.value = RestObservable.error(activity, it) }
                )
    }

    fun loginApi(activity: Activity, showLoader:Boolean,
                 map: HashMap<String, String>
    ) {
        restApiInterface.login(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { mResponse.value = RestObservable.loading(activity,showLoader) }
                .subscribe(
                        { mResponse.value = RestObservable.success(it) },
                        { mResponse.value = RestObservable.error(activity,it) }
                )
    }

    fun verifyOTPApi(activity: Activity, showLoader:Boolean,
                 map: HashMap<String, String>
    ) {
        restApiInterface.verifyOtp(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { mResponse.value = RestObservable.loading(activity,showLoader) }
                .subscribe(
                        { mResponse.value = RestObservable.success(it) },
                        { mResponse.value = RestObservable.error(activity,it) }
                )
    }
    fun resendOtpApi(activity: Activity, showLoader:Boolean,
                 map: HashMap<String, String>
    ) {
        restApiInterface.resendOtp(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { mResponse.value = RestObservable.loading(activity,showLoader) }
                .subscribe(
                        { mResponse.value = RestObservable.success(it) },
                        { mResponse.value = RestObservable.error(activity,it) }
                )
    }


    fun forgotPasswordApi(activity: Activity, showLoader:Boolean,
                 map: HashMap<String, String>
    ) {
        restApiInterface.forgotPassword(map)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { mResponse.value = RestObservable.loading(activity,showLoader) }
            .subscribe(
                { mResponse.value = RestObservable.success(it) },
                { mResponse.value = RestObservable.error(activity,it) }
            )
    }


    fun categoryListApi(activity: Activity, showLoader:Boolean
//                 map: HashMap<String, String>
    ) {
       // restApiInterface.getCategoryList(map)
        restApiInterface.getCategoryList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { mResponse.value = RestObservable.loading(activity,showLoader) }
            .subscribe(
                { mResponse.value = RestObservable.success(it) },
                { mResponse.value = RestObservable.error(activity,it) }
            )
    }

    fun subCategoryListApi(activity: Activity, showLoader:Boolean,
                 map: HashMap<String, String>
    ) {
        restApiInterface.getSubCategoryList(map)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { mResponse.value = RestObservable.loading(activity,showLoader) }
            .subscribe(
                { mResponse.value = RestObservable.success(it) },
                { mResponse.value = RestObservable.error(activity,it) }
            )
    }

    // for check
    fun newsubCategoryListApi(activity: Activity, showLoader:Boolean

    ) {
        restApiInterface.getSubCategoryList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { mResponse.value = RestObservable.loading(activity,showLoader) }
            .subscribe(
                { mResponse.value = RestObservable.success(it) },
                { mResponse.value = RestObservable.error(activity,it) }
            )
    }

    fun addShopAndDeliveryDetailsApi(activity: Activity, showLoader: Boolean, map: HashMap<String, RequestBody>,
            mImage: MultipartBody.Part
    ) {
        restApiInterface.addShopAndDeliveryDetails(map, mImage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { mResponse.value = RestObservable.loading(activity, showLoader) }
                .subscribe(
                        { mResponse.value = RestObservable.success(it) },
                        { mResponse.value = RestObservable.error(activity, it) }
                )
    }

    fun editShopDelivery(
            activity: Activity, showLoader: Boolean,
            map: HashMap<String, RequestBody>,
            mImage: String,
            mValidationClass: ValidationsClass
    ) {
        lateinit var profileImageFileBody: MultipartBody.Part
        var updateProfile: Observable<EditProfileAddShopResponsess>? = null
        if (!mValidationClass.checkStringNull(mImage)) {
            val file = File(mImage)
            profileImageFileBody = mValidationClass.prepareFilePart("shopLogo", file)
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

}