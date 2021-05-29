package com.hefny.hady.marvelstudios.api.responses

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("status")
    val message: String
)