package com.ongoshop.pojo

import com.google.gson.annotations.SerializedName

data class GraphDetailResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("body")
	val body: Body? = null
)

data class Body(

	@field:SerializedName("amount")
	val amount: List<Int?>? = null,

	@field:SerializedName("dates")
	val dates: List<String?>? = null
)
