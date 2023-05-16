package com.randomx.travel.network

import com.google.gson.annotations.SerializedName
import com.randomx.travel.model.assets.ApiErrorEntryModel

data class ApiResponse<T>(
    @SerializedName("status") val status: String?,
    @SerializedName("data") val data: T?,
    @SerializedName("message") val message: String?,
    @SerializedName("error") val error: ApiErrorEntryModel?
)
{
    val isSuccessful: Boolean get() = status == "success"
    val isError: Boolean get() = !isSuccessful
}