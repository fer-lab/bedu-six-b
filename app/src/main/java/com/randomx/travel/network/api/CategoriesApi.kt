package com.randomx.travel.network.api

import com.randomx.travel.model.CategoryModel
import com.randomx.travel.model.ProductModel
import com.randomx.travel.network.ApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CategoriesApi {

    @GET("categories/{id}")
    suspend fun get(@Path("id") id: String): Response<ApiResponse<CategoryModel>>

    @GET("categories")
    suspend fun getAll(): Response<ApiResponse<List<CategoryModel>>>

    @GET("categories/random/{items}")
    suspend fun getRandom(@Path("items") items: Int): Response<ApiResponse<List<CategoryModel>>>

    @GET("categories/{id}/products")
    suspend fun getProducts(@Path("id") id: String): Response<ApiResponse<List<ProductModel>>>

}
