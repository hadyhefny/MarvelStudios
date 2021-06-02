package com.hefny.hady.marvelstudios.utils

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * custom interceptor to add query parameters to every api call (public api key,
 * timestamp, hash code)
 */
class AuthenticationInterceptor() : Interceptor {
    private val publicApiKey: String = "be177ef877b01a3c396f1ab117c7783a"
    private val privateApiKey: String = "6e65f1d403c85144568bdc491e705546d40690ac"
    private val timestamp: String = System.currentTimeMillis().toString()
    private val hash: String = MD5HashGenerator.md5(timestamp, privateApiKey, publicApiKey)

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest: Request = chain.request()
        val originalHttpUrl: HttpUrl = originalRequest.url
        val newHttpUrl: HttpUrl = originalHttpUrl.newBuilder()
            .addQueryParameter("ts", timestamp)
            .addQueryParameter("apikey", publicApiKey)
            .addQueryParameter("hash", hash)
            .build()
        val newRequest: Request = originalRequest.newBuilder()
            .url(newHttpUrl)
            .build()
        return chain.proceed(newRequest)
    }
}