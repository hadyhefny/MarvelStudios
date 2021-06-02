package com.hefny.hady.marvelstudios.utils

import com.google.gson.Gson
import com.hefny.hady.marvelstudios.api.responses.ErrorResponse
import retrofit2.HttpException
import java.io.IOException

/**
 * helper class to parse errors thrown from retrofit
 */
class ErrorUtils {
    companion object {
        fun parseError(t: Throwable?): ErrorResponse {
            var errorResponse = ErrorResponse(0, ErrorMessages.SOMETHING_WENT_WRONG)
            when (t) {
                is HttpException -> {
                    t.response()?.errorBody()?.string()?.let {
                        errorResponse = Gson().fromJson(it, ErrorResponse::class.java)
                    }
                }
                is IOException -> {
                    errorResponse = ErrorResponse(0, ErrorMessages.INTERNET_CONNECTION_ERROR)
                }
            }
            return errorResponse
        }
    }
}