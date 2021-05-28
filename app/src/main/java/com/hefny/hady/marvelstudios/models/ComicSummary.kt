package com.hefny.hady.marvelstudios.models

import com.google.gson.annotations.SerializedName

data class ComicSummary(
    @SerializedName("resourceUri")
    val resourceUri: String,
    @SerializedName("name")
    val name: String
)