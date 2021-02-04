package com.ongoshop.pojo

import android.os.Parcelable

import kotlinx.android.parcel.Parcelize

@Parcelize
class VendorDeliveryOption (val id: Int, val vendorId: Int, val sortOrder: Int, var day: String, var deliveryTimeFrom: String,
                            var deliveryTimeTo: String, var noDelivery: Int, val created: Int, val updated: Int, val createdAt: String,
                            val updatedAt: String  /*var isTimeSelected: String*/) : Parcelable
