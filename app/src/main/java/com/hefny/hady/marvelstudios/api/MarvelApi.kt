package com.hefny.hady.marvelstudios.api

import com.hefny.hady.marvelstudios.api.responses.MainResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface MarvelApi {
    @GET("characters")
    fun getCharacters(): Single<MainResponse>
}