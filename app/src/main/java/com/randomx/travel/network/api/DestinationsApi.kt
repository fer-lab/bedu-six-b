package com.randomx.travel.network.api

import com.randomx.travel.model.DestinationModel
import com.randomx.travel.model.ProductModel
import com.randomx.travel.network.ApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface DestinationsApi {

    @Headers("Cache-Control: max-age=3600")
    @GET("destinations/{id}")
    suspend fun get(@Path("id") id: String): Response<ApiResponse<DestinationModel>>

    @Headers("Cache-Control: max-age=3600")
    @GET("destinations")
    suspend fun getAll(): Response<ApiResponse<List<DestinationModel>>>

    @Headers("Cache-Control: max-age=3600")
    @GET("destinations/random/{items}")
    suspend fun getRandom(@Path("items") items: Int): Response<ApiResponse<List<DestinationModel>>>

    @Headers("Cache-Control: max-age=3600")
    @GET("destinations/{id}/products")
    suspend fun getProducts(@Path("id") id: String): Response<ApiResponse<List<ProductModel>>>


}
