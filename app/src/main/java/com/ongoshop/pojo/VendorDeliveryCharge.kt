package com.ongoshop.pojo

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import kotlinx.android.parcel.Parcelize

@Parcelize
class VendorDeliveryCharge (val id: Int, val vendorId: Int, var minDistance: Int, var maxDistance: Int, var price: String,
                            val noDelivery: Int, var freeDelivery: Int, val created: Int, val updated: Int, val createdAt: String,
                            val updatedAt: String) : Parcelable

