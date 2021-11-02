package com.ongoshop.pojo

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

class OrderListResponse {

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
        @SerializedName("pastdates")
        @Expose
        var pastdates: ArrayList<Pastdate>? = null

        @SerializedName("future_dates")
        @Expose
        var futureDates: ArrayList<FutureDates>? = null

    }

    class FutureDates {
        @SerializedName("id")
        @Expose
        var id: String? = null
        
        @SerializedName("orderNo")
        @Expose
        var orderNo: String? = null

        @SerializedName("createdAt")
        @Expose
        var createdAt: String? = null

        @SerializedName("deliverySlot")
        @Expose
        var deliverySlot: String? = null

        @SerializedName("isSelfpickup")
        @Expose
        var isSelfpickup: Int? = null

        @SerializedName("username")
        @Expose
        var username: String? = null

        @SerializedName("email")
        @Expose
        var email: String? = null

        @SerializedName("phone")
        @Expose
        var phone: String? = null

        @SerializedName("image")
        @Expose
        var image: String? = null

        @SerializedName("userAddress")
        @Expose
        var userAddress: String? = null

    }

    class Pastdate {

        @SerializedName("id")
        @Expose
        var id: String? = null
        @SerializedName("orderNo")
        @Expose
        var orderNo: String? = null

        @SerializedName("createdAt")
        @Expose
        var createdAt: String? = null

        @SerializedName("deliverySlot")
        @Expose
        var deliverySlot: String? = null

        @SerializedName("isSelfpickup")
        @Expose
        var isSelfpickup: Int? = null

        @SerializedName("username")
        @Expose
        var username: String? = null

        @SerializedName("email")
        @Expose
        var email: String? = null

        @SerializedName("phone")
        @Expose
        var phone: String? = null

        @SerializedName("image")
        @Expose
        var image: String? = null

        @SerializedName("userAddress")
        @Expose
        var userAddress: String? = null

    }

}