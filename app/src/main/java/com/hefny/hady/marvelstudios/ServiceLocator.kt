package com.hefny.hady.marvelstudios

import com.hefny.hady.marvelstudios.api.MarvelApi
import com.hefny.hady.marvelstudios.api.ServiceGenerator
import retrofit2.Retrofit

object ServiceLocator {
    fun getMarvelApi(): MarvelApi {
        return ServiceGenerator.marvelApi
    }

    fun getRetrofitInstance(): Retrofit {
        return ServiceGenerator.getInstance()
    }
}