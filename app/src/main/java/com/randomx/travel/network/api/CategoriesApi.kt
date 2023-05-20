package com.randomx.travel.network.api

import com.randomx.travel.model.CategoryModel
import com.randomx.travel.model.ProductModel
import com.randomx.travel.network.ApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface CategoriesApi {

    @Headers("Cache-Control: max-age=3600")
    @GET("categories/{id}")
    suspend fun get(@Path("id") id: String): Response<ApiResponse<CategoryModel>>


    @Headers("Cache-Control: max-age=3600")
    @GET("categories")
    suspend fun getAll(): Response<ApiResponse<List<CategoryModel>>>

    @Headers("Cache-Control: max-age=3600")
    @GET("categories/random/{items}")
    suspend fun getRandom(@Path("items") items: Int): Response<ApiResponse<List<CategoryModel>>>

    @Headers("Cache-Control: max-age=3600")
    @GET("categories/{id}/products")
    suspend fun getProducts(@Path("id") id: String): Response<ApiResponse<List<ProductModel>>>

}
