package com.ongoshop.viewmodel

import android.app.Activity
import android.util.Log
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

class HomeViewModel : ViewModel() {

    private val TAG = HomeViewModel::class.java.name
    val restApiInterface = MyApplication.getnstance().provideAuthservice()
    var mResponse: MutableLiveData<RestObservable> = MutableLiveData()


    fun getProfile(activity: Activity, showLoader: Boolean) {
        restApiInterface.getProfile()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { mResponse.value = RestObservable.loading(activity, showLoader) }
                .subscribe(
                        { mResponse.value = RestObservable.success(it) },
                        { mResponse.value = RestObservable.error(activity, it) }
                )
    }

    fun notificationListAPI(activity: Activity, showLoader: Boolean) {
        restApiInterface.notificationList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { mResponse.value = RestObservable.loading(activity, showLoader) }
                .subscribe(
                        { mResponse.value = RestObservable.success(it) },
                        { mResponse.value = RestObservable.error(activity, it) }
                )
    }

    fun getProductListingAPI(activity: Activity, showLoader: Boolean, map: HashMap<String, String>) {
        restApiInterface.getProductListing(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { mResponse.value = RestObservable.loading(activity, showLoader) }
                .subscribe(
                        { mResponse.value = RestObservable.success(it) },
                        { mResponse.value = RestObservable.error(activity, it) }
                )
    }

    fun addProductApi(activity: Activity, showLoader: Boolean, map: java.util.HashMap<String, RequestBody>,
                      mImage: MultipartBody.Part
    ) {
        restApiInterface.addProduct(map, mImage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { mResponse.value = RestObservable.loading(activity, showLoader) }
                .subscribe(
                        { mResponse.value = RestObservable.success(it) },
                        { mResponse.value = RestObservable.error(activity, it) }
                )
    }

    fun checkBarcodeApi(activity: Activity, showLoader: Boolean, map: HashMap<String, String>) {
        restApiInterface.checkBarcode(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { mResponse.value = RestObservable.loading(activity, showLoader) }
                .subscribe(
                        { mResponse.value = RestObservable.success(it) },
                        { mResponse.value = RestObservable.error(activity, it) }
                )
    }

    fun orderListApi(activity: Activity, showLoader: Boolean, map: HashMap<String, String>) {
        restApiInterface.ordersList(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { mResponse.value = RestObservable.loading(activity, showLoader) }
                .subscribe(
                        { mResponse.value = RestObservable.success(it) },
                        { mResponse.value = RestObservable.error(activity, it) }
                )
    }

    fun pastOrderListApi(activity: Activity, showLoader: Boolean, map: HashMap<String, String>) {
        restApiInterface.pastOrdersList(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { mResponse.value = RestObservable.loading(activity, showLoader) }
                .subscribe(
                        { mResponse.value = RestObservable.success(it) },
                        { mResponse.value = RestObservable.error(activity, it) }
                )
    }

    fun getProductBarcodeAPI(activity: Activity, showLoader: Boolean, map: HashMap<String, String>) {
        restApiInterface.getProductbarcode(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { mResponse.value = RestObservable.loading(activity, showLoader) }
                .subscribe(
                        { mResponse.value = RestObservable.success(it) },
                        { mResponse.value = RestObservable.error(activity, it) }
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


    fun allCardsAPI(activity: Activity, showLoader: Boolean) {
        restApiInterface.allCards()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { mResponse.value = RestObservable.loading(activity, showLoader) }
                .subscribe(
                        { mResponse.value = RestObservable.success(it) },
                        { mResponse.value = RestObservable.error(activity, it) }
                )
    }

    fun addCardAPI(activity: Activity, showLoader: Boolean, map: HashMap<String, String>) {
        restApiInterface.addCard(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { mResponse.value = RestObservable.loading(activity, showLoader) }
                .subscribe(
                        { mResponse.value = RestObservable.success(it) },
                        { mResponse.value = RestObservable.error(activity, it) }
                )
    }

    fun ordersItemsAPI(activity: Activity, showLoader: Boolean, map: HashMap<String, String>) {
        restApiInterface.ordersItems(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { mResponse.value = RestObservable.loading(activity, showLoader) }
                .subscribe(
                        { mResponse.value = RestObservable.success(it) },
                        { mResponse.value = RestObservable.error(activity, it) }
                )
    }

    fun acceptOrderAPI(activity: Activity, showLoader: Boolean, map: HashMap<String, String>) {
        restApiInterface.acceptorder(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { mResponse.value = RestObservable.loading(activity, showLoader) }
                .subscribe(
                        { mResponse.value = RestObservable.success(it) },
                        { mResponse.value = RestObservable.error(activity, it) }
                )
    }

       fun shippedOrderAPI(activity: Activity, showLoader: Boolean, map: HashMap<String, String>) {
        restApiInterface.shippedOrder(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { mResponse.value = RestObservable.loading(activity, showLoader) }
                .subscribe(
                        { mResponse.value = RestObservable.success(it) },
                        { mResponse.value = RestObservable.error(activity, it) }
                )
    }

    fun finishPackingAPI(activity: Activity, showLoader: Boolean, map: HashMap<String, String>) {
        restApiInterface.finishPacking(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { mResponse.value = RestObservable.loading(activity, showLoader) }
                .subscribe(
                        { mResponse.value = RestObservable.success(it) },
                        { mResponse.value = RestObservable.error(activity, it) }
                )
    }

    fun isSelfpickupOrdersAPI(activity: Activity, showLoader: Boolean, map: HashMap<String, String>) {
        restApiInterface.isSelfpickupOrders(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { mResponse.value = RestObservable.loading(activity, showLoader) }
                .subscribe(
                        { mResponse.value = RestObservable.success(it) },
                        { mResponse.value = RestObservable.error(activity, it) }
                )
    }

    fun getMyProductListAPI(activity: Activity, showLoader: Boolean) {
        restApiInterface.getProductList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { mResponse.value = RestObservable.loading(activity, showLoader) }
                .subscribe(
                        { mResponse.value = RestObservable.success(it) },
                        { mResponse.value = RestObservable.error(activity, it) }
                )
    }


    fun deleteCardAPI(activity: Activity, showLoader: Boolean, id: String) {
        restApiInterface.deleteCard(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { mResponse.value = RestObservable.loading(activity, showLoader) }
                .subscribe(
                        { mResponse.value = RestObservable.success(it) },
                        { mResponse.value = RestObservable.error(activity, it) }
                )
    }

    fun deleteProductAPI(activity: Activity, showLoader: Boolean, id: String) {
        restApiInterface.deleteProduct(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { mResponse.value = RestObservable.loading(activity, showLoader) }
                .subscribe(

                        { mResponse.value = RestObservable.success(it) },
                        { mResponse.value = RestObservable.error(activity, it) }
                )
    }

    fun updateCardAPI(activity: Activity, showLoader: Boolean, map: HashMap<String, String>) {
        restApiInterface.updateCard(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { mResponse.value = RestObservable.loading(activity, showLoader) }
                .subscribe(
                        { mResponse.value = RestObservable.success(it) },
                        { mResponse.value = RestObservable.error(activity, it) }
                )
    }

    fun changeAvailabilityAPI(activity: Activity, showLoader: Boolean, map: HashMap<String, String>) {
        restApiInterface.changeAvailability(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { mResponse.value = RestObservable.loading(activity, showLoader) }
                .subscribe(
                        { mResponse.value = RestObservable.success(it) },
                        { mResponse.value = RestObservable.error(activity, it) }
                )
    }

    fun editPriceAPI(activity: Activity, showLoader: Boolean, map: HashMap<String, String>) {
        restApiInterface.editPrice(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { mResponse.value = RestObservable.loading(activity, showLoader) }
                .subscribe(
                        { mResponse.value = RestObservable.success(it) },
                        { mResponse.value = RestObservable.error(activity, it) }
                )
    }

    fun setDefaultCardAPI(activity: Activity, showLoader: Boolean, map: HashMap<String, String>) {
        restApiInterface.setDefaultCard(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { mResponse.value = RestObservable.loading(activity, showLoader) }
                .subscribe(
                        { mResponse.value = RestObservable.success(it) },
                        { mResponse.value = RestObservable.error(activity, it) }
                )
    }


}