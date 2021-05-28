package com.hefny.hady.marvelstudios.models

import com.google.gson.annotations.SerializedName

data class Comic(
    @SerializedName("returned")
    val returned: Int,
    @SerializedName("items")
    val items: ArrayList<ComicSummary>
)