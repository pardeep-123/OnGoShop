package com.ongoshop.pojo

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import kotlinx.android.parcel.Parcelize

@Parcelize
class VendorDeliveryCharge(
    val id: Int,
    val vendorId: Int,
    var minDistance: Int,
    var maxDistance: Int,
    var price: Int = 0,
    val noDelivery: Int,
    var freeDelivery: Int,
    val created: Int,
    val updated: Int,
    val createdAt: String,
    val updatedAt: String
) : Parcelable

@Parcelize
class VendorDetail(
    val userId: Int,
    val approvalStatus: Int,
    val homeDelivery: Int,
    val deliveriesPerDay: Int,
    val name: String,
    val approvalStatusReason: String,
    val image: String,
    val phone: String,
    val shopLogo: String,
    val shopCategory: String,
    val shop_category_id: String,
    val abn: String,
    val buildingNumber: String,
    val streetNumber: String,
    val city: String,
    val state: String,
    val country: String,
    val postalCode: String,
    val shopOpenTime: String,
    val shopCloseTime: String,
    val latitude: String,
    val longitude: String,
    val geoLocation: String,
    val shopAddress: String,
    val shopDescription: String,
    val shopName: String
) : Parcelable