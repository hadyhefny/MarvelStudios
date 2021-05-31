package com.hefny.hady.marvelstudios.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Character(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("resourceURI")
    val resourceURI: String,
    @SerializedName("thumbnail")
    val thumbnail: Thumbnail,
    @SerializedName("comics")
    val comics: MarvelList,
    @SerializedName("stories")
    val stories: MarvelList,
    @SerializedName("events")
    val events: MarvelList,
    @SerializedName("series")
    val series: MarvelList
) : Parcelable