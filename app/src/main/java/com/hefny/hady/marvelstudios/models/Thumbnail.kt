package com.hefny.hady.marvelstudios.models

import com.google.gson.annotations.SerializedName

data class Thumbnail(
    @SerializedName("path")
    val path: String,
    @SerializedName("extension")
    val extension: String
){
    fun getImageUrl() : String = "$path.$extension"
}