package com.hefny.hady.marvelstudios.api

import com.hefny.hady.marvelstudios.api.responses.DataContainerResponse
import com.hefny.hady.marvelstudios.api.responses.MainResponse
import com.hefny.hady.marvelstudios.models.Character
import com.hefny.hady.marvelstudios.models.MarvelSummary
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface MarvelApi {
    @GET("characters")
    fun getCharacters(
        @Query("nameStartsWith") name: String? = null,
        @Query("offset") offset: Int
    ): Single<MainResponse<DataContainerResponse<Character>>>

    @GET()
    fun getMarvelSummaries(
        @Url marvelSummariesUrl: String
    ): Single<MainResponse<DataContainerResponse<MarvelSummary>>>
}