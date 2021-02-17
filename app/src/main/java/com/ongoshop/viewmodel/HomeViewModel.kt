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

class HomeViewModel :ViewModel() {
    private val TAG = HomeViewModel::class.java.name
    val restApiInterface = MyApplication.getnstance().provideAuthservice()
    var mResponse: MutableLiveData<RestObservable> = MutableLiveData()




    fun getProfile(activity: Activity, showLoader:Boolean) {
        restApiInterface.getProfile()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { mResponse.value = RestObservable.loading(activity,showLoader) }
            .subscribe(
                { mResponse.value = RestObservable.success(it) },
                { mResponse.value = RestObservable.error(activity,it) }
            )
    }

 fun getProductListingAPI(activity: Activity, showLoader:Boolean, map:HashMap<String, String>) {
        restApiInterface.getProductListing(map)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { mResponse.value = RestObservable.loading(activity,showLoader) }
            .subscribe(
                { mResponse.value = RestObservable.success(it) },
                { mResponse.value = RestObservable.error(activity,it) }
            )
    }


/*
    fun notificationListApi(activity: Activity, showLoader:Boolean) {
        restApiInterface.notificationsList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { mResponse.value = RestObservable.loading(activity,showLoader) }
            .subscribe(
                { mResponse.value = RestObservable.success(it) },
                { mResponse.value = RestObservable.error(activity,it) }
            )
    }
*/


    fun allCardsAPI(activity: Activity, showLoader:Boolean) {
        restApiInterface.allCards()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { mResponse.value = RestObservable.loading(activity,showLoader) }
                .subscribe(
                        { mResponse.value = RestObservable.success(it) },
                        { mResponse.value = RestObservable.error(activity,it) }
                )
    }

    fun addCardAPI(activity: Activity, showLoader:Boolean, map: HashMap<String, String>) {
        restApiInterface.addCard(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { mResponse.value = RestObservable.loading(activity,showLoader) }
                .subscribe(
                        { mResponse.value = RestObservable.success(it) },
                        { mResponse.value = RestObservable.error(activity,it) }
                )
    }

    fun deleteCardAPI(activity: Activity, showLoader:Boolean, map: HashMap<String, String>) {
        restApiInterface.deleteCard(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { mResponse.value = RestObservable.loading(activity,showLoader) }
                .subscribe(
                        { mResponse.value = RestObservable.success(it) },
                        { mResponse.value = RestObservable.error(activity,it) }
                )
    }

    fun updateCardAPI(activity: Activity, showLoader:Boolean, map: HashMap<String, String>) {
        restApiInterface.updateCard(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { mResponse.value = RestObservable.loading(activity,showLoader) }
                .subscribe(
                        { mResponse.value = RestObservable.success(it) },
                        { mResponse.value = RestObservable.error(activity,it) }
                )
    }

    fun setDefaultCardAPI(activity: Activity, showLoader:Boolean, map: HashMap<String, String>) {
        restApiInterface.setDefaultCard(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { mResponse.value = RestObservable.loading(activity,showLoader) }
                .subscribe(
                        { mResponse.value = RestObservable.success(it) },
                        { mResponse.value = RestObservable.error(activity,it) }
                )
    }



}