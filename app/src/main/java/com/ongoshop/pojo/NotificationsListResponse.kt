package com.ongoshop.pojo

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

class NotificationsListResponse {

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

    class Body {
        @SerializedName("id")
        @Expose
        private var id: Int? = null

        @SerializedName("message")
        @Expose
        private var message: String? = null

        @SerializedName("orderid")
        @Expose
        private var orderid: Int? = null

        @SerializedName("createdAt")
        @Expose
        private var createdAt: String? = null

        @SerializedName("userby")
        @Expose
        private var userby: String? = null

        @SerializedName("userto")
        @Expose
        private var userto: String? = null

        fun getId(): Int? {
            return id
        }

        fun setId(id: Int?) {
            this.id = id
        }

        fun getMessage(): String? {
            return message
        }

        fun setMessage(message: String?) {
            this.message = message
        }

        fun getOrderid(): Int? {
            return orderid
        }

        fun setOrderid(orderid: Int?) {
            this.orderid = orderid
        }

        fun getCreatedAt(): String? {
            return createdAt
        }

        fun setCreatedAt(createdAt: String?) {
            this.createdAt = createdAt
        }

        fun getUserby(): String? {
            return userby
        }

        fun setUserby(userby: String?) {
            this.userby = userby
        }

        fun getUserto(): String? {
            return userto
        }

        fun setUserto(userto: String?) {
            this.userto = userto
        }

    }

}