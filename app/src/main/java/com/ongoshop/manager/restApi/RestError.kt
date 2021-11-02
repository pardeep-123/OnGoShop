package com.ongoshop.manager.restApi

import com.google.gson.annotations.SerializedName

class RestError
{
    @SerializedName("code")
    var code: Int = 0

    @SerializedName("message")
    var message: String? = null
}