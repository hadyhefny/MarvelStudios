package com.hefny.hady.marvelstudios.api

import com.hefny.hady.marvelstudios.api.responses.MainResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelApi {
    @GET("characters")
    fun getCharacters(
        @Query("nameStartsWith") name: String? = null,
        @Query("offset") offset: Int
    ): Single<MainResponse>
}