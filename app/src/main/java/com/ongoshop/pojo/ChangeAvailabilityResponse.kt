package com.ongoshop.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ChangeAvailabilityResponse {

    @SerializedName("success")
    @Expose
    private var success: Boolean? = null

    @SerializedName("code")
    @Expose
    private var code: Int? = null

    @SerializedName("message")
    @Expose
    private var message: String? = null

    @SerializedName("body")
    @Expose
    private var body: Body? = null

    fun getSuccess(): Boolean? {
        return success
    }

    fun setSuccess(success: Boolean?) {
        this.success = success
    }

    fun getCode(): Int? {
        return code
    }

    fun setCode(code: Int?) {
        this.code = code
    }

    fun getMessage(): String? {
        return message
    }

    fun setMessage(message: String?) {
        this.message = message
    }

    fun getBody(): Body? {
        return body
    }

    fun setBody(body: Body?) {
        this.body = body
    }

    class Body {
        @SerializedName("id")
        @Expose
        var id: Int? = null

        @SerializedName("isApproved")
        @Expose
        var isApproved: Int? = null

        @SerializedName("status")
        @Expose
        var status: Int? = null

        @SerializedName("isAvailable")
        @Expose
        var isAvailable: Int? = null

        @SerializedName("taxCategoryId")
        @Expose
        var taxCategoryId: Int? = null

        @SerializedName("vendorId")
        @Expose
        var vendorId: Int? = null

        @SerializedName("categoryId")
        @Expose
        var categoryId: Int? = null

        @SerializedName("subCategoryId")
        @Expose
        var subCategoryId: Int? = null

        @SerializedName("name")
        @Expose
        var name: String? = null

        @SerializedName("description")
        @Expose
        var description: String? = null

        @SerializedName("countryOfOrigin")
        @Expose
        var countryOfOrigin: String? = null

        @SerializedName("gtinNumber")
        @Expose
        var gtinNumber: String? = null

        @SerializedName("image")
        @Expose
        var image: String? = null

        @SerializedName("isBarcodeItem")
        @Expose
        var isBarcodeItem: Int? = null

        @SerializedName("barcode")
        @Expose
        var barcode: String? = null

        @SerializedName("barcodeImage")
        @Expose
        var barcodeImage: String? = null

        @SerializedName("sku")
        @Expose
        var sku: String? = null

        @SerializedName("skuImage")
        @Expose
        var skuImage: String? = null

        @SerializedName("brandName")
        @Expose
        var brandName: String? = null

        @SerializedName("mrp")
        @Expose
        var mrp: String? = null

        @SerializedName("minimumSellingPrice")
        @Expose
        var minimumSellingPrice: String? = null

        @SerializedName("percentageDiscount")
        @Expose
        var percentageDiscount: Int? = null

        @SerializedName("length")
        @Expose
        var length: String? = null

        @SerializedName("width")
        @Expose
        var width: String? = null

        @SerializedName("height")
        @Expose
        var height: String? = null

        @SerializedName("dimensionsUnit")
        @Expose
        var dimensionsUnit: Int? = null

        @SerializedName("weight")
        @Expose
        var weight: String? = null

        @SerializedName("weightUnit")
        @Expose
        var weightUnit: Int? = null

        @SerializedName("created")
        @Expose
        var created: Int? = null

        @SerializedName("updated")
        @Expose
        var updated: Int? = null

        @SerializedName("createdAt")
        @Expose
        var createdAt: String? = null

        @SerializedName("updatedAt")
        @Expose
        var updatedAt: String? = null

    }

}