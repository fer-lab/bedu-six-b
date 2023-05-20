package com.randomx.travel.network.api

import com.randomx.travel.model.ProductModel
import com.randomx.travel.network.ApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface ProductsApi {

    @Headers("Cache-Control: max-age=3600")
    @GET("products/{id}")
    suspend fun get(@Path("id") id: String): Response<ApiResponse<ProductModel>>

    @Headers("Cache-Control: max-age=3600")
    @GET("products/random/{items}")
    suspend fun getRandom(@Path("items") items: Int): Response<ApiResponse<List<ProductModel>>>
}
