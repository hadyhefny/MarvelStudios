package com.hefny.hady.marvelstudios.utils

import com.hefny.hady.marvelstudios.ServiceLocator
import com.hefny.hady.marvelstudios.api.responses.ErrorResponse
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.HttpException
import java.io.IOException

/**
 * helper class to parse errors thrown from retrofit
 */
class ErrorUtils {
    companion object {
        fun parseError(t: Throwable?): ErrorResponse {
            val converter: Converter<ResponseBody, ErrorResponse> =
                ServiceLocator.getRetrofitInstance()
                    .responseBodyConverter(ErrorResponse::class.java, arrayOfNulls<Annotation>(0))
            var errorResponse = ErrorResponse(0, "Something went wrong, please try again")
            when (t) {
                is HttpException -> {
                    try {
                        t.response()?.errorBody()?.let { responseBody ->
                            converter.convert(responseBody)?.let {
                                errorResponse = it
                            }
                        }
                    } catch (e: IOException) {
                        return ErrorResponse(0, "Check your internet connection")
                    }
                }
                is IOException -> {
                    errorResponse = ErrorResponse(0, "Check your internet connection")
                }
            }
            return errorResponse
        }
    }
}