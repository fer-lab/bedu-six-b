package com.randomx.travel.network.api

import com.randomx.travel.model.destination.DestinationModel
import com.randomx.travel.model.ProductModel
import com.randomx.travel.network.ApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DestinationsApi {

    @GET("destinations/{id}")
    suspend fun get(@Path("id") id: String): Response<ApiResponse<DestinationModel>>

    @GET("destinations")
    suspend fun getAll(): Response<ApiResponse<List<DestinationModel>>>

    @GET("destinations/random/{items}")
    suspend fun getRandom(@Path("items") items: Int): Response<ApiResponse<List<DestinationModel>>>

    @GET("destinations/{id}/products")
    suspend fun getProducts(@Path("id") id: String): Response<ApiResponse<List<ProductModel>>>


}
