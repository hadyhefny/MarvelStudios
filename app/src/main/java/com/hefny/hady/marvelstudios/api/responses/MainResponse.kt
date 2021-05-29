package com.hefny.hady.marvelstudios.api.responses

import com.google.gson.annotations.SerializedName
import com.hefny.hady.marvelstudios.api.responses.CharacterDataContainerResponse

data class MainResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("status")
    val status: String,
    @SerializedName("data")
    var data: CharacterDataContainerResponse
)