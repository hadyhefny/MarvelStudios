package com.hefny.hady.marvelstudios.api

import com.hefny.hady.marvelstudios.utils.AuthenticationInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ServiceGenerator {
    private const val BASE_API = "http://gateway.marvel.com/v1/public/"
    private val builder: Retrofit.Builder = Retrofit.Builder()
        .baseUrl(BASE_API)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())

    fun getInstance(): Retrofit {
        val authenticationInterceptor = AuthenticationInterceptor()
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.HEADERS
        logging.level = HttpLoggingInterceptor.Level.BODY
        val client: OkHttpClient = OkHttpClient.Builder()
            .addNetworkInterceptor(logging)
            .addInterceptor(authenticationInterceptor)
            .build()
        return builder.client(client).build()
    }

    val marvelApi: MarvelApi = getInstance().create(MarvelApi::class.java)
}