package com.ongoshop.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class OrderItemsListResponse {
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
    private var body: ArrayList<Body?>? = null

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

    fun getBody(): ArrayList<Body?>? {
        return body
    }

    fun setBody(body: ArrayList<Body?>?) {
        this.body = body
    }

    class Product {
        @SerializedName("name")
        @Expose
        var name: String? = null

        @SerializedName("weight")
        @Expose
        var weight: String? = null

        @SerializedName("image")
        @Expose
        var image: String? = null

        @SerializedName("description")
        @Expose
        var description: String? = null

        @SerializedName("weightUnit")
        @Expose
        var weightUnit: Int? = null

    }

    class Body {
        @SerializedName("id")
        @Expose
        var id: Int? = null

        @SerializedName("orderId")
        @Expose
        var orderId: Int? = null

        @SerializedName("productId")
        @Expose
        var productId: Int? = null

        @SerializedName("netAmount")
        @Expose
        var netAmount: String? = null

        @SerializedName("qty")
        @Expose
        var qty: Int? = null

        @SerializedName("taxCharged")
        @Expose
        var taxCharged: String? = null

        @SerializedName("shippingCharges")
        @Expose
        var shippingCharges: String? = null

        @SerializedName("adminCommission")
        @Expose
        var adminCommission: String? = null

        @SerializedName("total")
        @Expose
        var total: String? = null

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

        @SerializedName("product")
        @Expose
        var product: Product? = null

    }

}