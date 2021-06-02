package com.hefny.hady.marvelstudios.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MarvelList(
    @SerializedName("available")
    val available: Int,
    @SerializedName("returned")
    val returned: Int,
    @SerializedName("collectionURI")
    val collectionURI: String,
    @SerializedName("items")
    val items: ArrayList<MarvelSummary>
): Parcelable