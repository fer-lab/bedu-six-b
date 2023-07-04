package com.randomx.travel.network

import android.util.Log
import com.google.firebase.crashlytics.BuildConfig
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Response

internal object ApiResponseExtractor {

    inline fun <reified T> extractData(response: Response<ApiResponse<T>>): ApiResponse<T> {

        val result = response.body()
            ?: Gson().fromJson(response.errorBody()?.string() ?: "", object : TypeToken<ApiResponse<T>>() {}.type)

        Log.d("ApiResponseExtractor", "Result: $result ${BuildConfig.DEBUG}")

        if (BuildConfig.DEBUG && result.isError && result.error?.message != null)
        {
            FirebaseCrashlytics.getInstance().log("Error: ${result.error.message}");
        }

        return result
    }
}


