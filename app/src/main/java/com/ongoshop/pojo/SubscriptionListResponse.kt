package com.ongoshop.pojo

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

class SubscriptionListResponse
{
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

         @SerializedName("status")
         @Expose
         private var status: Int? = null

         @SerializedName("amount")
         @Expose
         private var amount: Int? = null

         @SerializedName("type")
         @Expose
         private var type: Int? = null

         @SerializedName("item")
         @Expose
         private var item: Int? = null

         @SerializedName("employes")
         @Expose
         private var employes: Int? = null

         @SerializedName("createdAt")
         @Expose
         private var createdAt: String? = null

         @SerializedName("updatedAt")
         @Expose
         private var updatedAt: String? = null

         fun getId(): Int? {
             return id
         }

         fun setId(id: Int?) {
             this.id = id
         }

         fun getStatus(): Int? {
             return status
         }

         fun setStatus(status: Int?) {
             this.status = status
         }

         fun getAmount(): Int? {
             return amount
         }

         fun setAmount(amount: Int?) {
             this.amount = amount
         }

         fun getType(): Int? {
             return type
         }

         fun setType(type: Int?) {
             this.type = type
         }

         fun getItem(): Int? {
             return item
         }

         fun setItem(item: Int?) {
             this.item = item
         }

         fun getEmployes(): Int? {
             return employes
         }

         fun setEmployes(employes: Int?) {
             this.employes = employes
         }

         fun getCreatedAt(): String? {
             return createdAt
         }

         fun setCreatedAt(createdAt: String?) {
             this.createdAt = createdAt
         }

         fun getUpdatedAt(): String? {
             return updatedAt
         }

         fun setUpdatedAt(updatedAt: String?) {
             this.updatedAt = updatedAt
         }
    }
}