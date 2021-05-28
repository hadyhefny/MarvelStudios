package com.hefny.hady.marvelstudios.api.responses

import com.google.gson.annotations.SerializedName
import com.hefny.hady.marvelstudios.models.Character

data class CharacterDataContainerResponse(
    @SerializedName("offset")
    val offset: Int,
    @SerializedName("limit")
    val limit: Int,
    @SerializedName("total")
    val total: Int,
    @SerializedName("count")
    val count: Int,
    @SerializedName("results")
    val results: ArrayList<Character>
)