package com.randomx.travel.network.api

import com.randomx.travel.model.ProductModel
import com.randomx.travel.network.ApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductsApi {

    @GET("products/{id}")
    suspend fun get(@Path("id") id: String): Response<ApiResponse<ProductModel>>

    @GET("products/random/{items}")
    suspend fun getRandom(@Path("items") items: Int): Response<ApiResponse<List<ProductModel>>>
}
