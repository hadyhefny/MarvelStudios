package com.hefny.hady.marvelstudios.api.responses

import com.google.gson.annotations.SerializedName

data class MainResponse<T>(
    @SerializedName("code")
    val code: Int,
    @SerializedName("status")
    val status: String,
    @SerializedName("data")
    var data: T
)