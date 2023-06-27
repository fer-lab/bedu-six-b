package com.randomx.travel.network.api

import com.randomx.travel.model.LocationModel
import com.randomx.travel.model.ProductModel
import com.randomx.travel.network.ApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface LocationApi {

    @Headers("Cache-Control: max-age=3600")
    @GET("location/{lat}/{lng}")
    suspend fun get(@Path("lat") lat: String, @Path("lng") lng: String): Response<ApiResponse<LocationModel>>
}
