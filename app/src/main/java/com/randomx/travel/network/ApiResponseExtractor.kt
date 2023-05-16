package com.randomx.travel.network

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Response

internal object ApiResponseExtractor {
    inline fun <reified T> extractData(response: Response<ApiResponse<T>>): ApiResponse<T> =
        response.body()
            ?: Gson().fromJson(response.errorBody()?.string() ?: "", object : TypeToken<ApiResponse<T>>() {}.type)
}
