package com.hefny.hady.marvelstudios.models

import com.google.gson.annotations.SerializedName

data class Character(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String
//    @SerializedName("comics")
//    val comics: ArrayList<Comic>
//    @SerializedName("stories")
//    val stories: ArrayList<Story>,
//    @SerializedName("events")
//    val events: ArrayList<MarvelEvent>,
//    @SerializedName("series")
//    val series: ArrayList<Series>,
)