package com.hefny.hady.marvelstudios.models

import com.google.gson.annotations.SerializedName

data class MarvelIssue(
    @SerializedName("title")
    val title: String,
    @SerializedName("thumbnail")
    val thumbnail: Thumbnail
)