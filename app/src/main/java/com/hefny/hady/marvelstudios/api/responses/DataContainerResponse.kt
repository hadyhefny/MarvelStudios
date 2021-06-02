package com.hefny.hady.marvelstudios.api.responses

import com.google.gson.annotations.SerializedName

data class DataContainerResponse<T>(
    @SerializedName("offset")
    val offset: Int,
    @SerializedName("limit")
    val limit: Int,
    @SerializedName("total")
    val total: Int,
    @SerializedName("count")
    val count: Int,
    @SerializedName("results")
    val results: ArrayList<T>
) {
    fun getNextOffset(): Int? {
        if (offset > total) {
            return null
        }
        return offset + 20
    }
}