package com.randomx.travel.network.datasource


import ApiClient
import com.randomx.travel.model.CategoryModel
import com.randomx.travel.model.ProductModel
import com.randomx.travel.network.ApiResponse
import com.randomx.travel.network.ApiResponseExtractor
import com.randomx.travel.network.api.CategoriesApi

class CategoriesDataSource {

    private val categoriesApi: CategoriesApi = ApiClient.categoriesApi

    suspend fun get(id: String): ApiResponse<CategoryModel> {
        return ApiResponseExtractor.extractData(categoriesApi.get(id))
    }

    suspend fun getAll(): ApiResponse<List<CategoryModel>> {
        return ApiResponseExtractor.extractData(categoriesApi.getAll())
    }

    suspend fun getRandom(items: Int): ApiResponse<List<CategoryModel>> {
        return ApiResponseExtractor.extractData(categoriesApi.getRandom(items))
    }

    suspend fun getProducts(id: String): ApiResponse<List<ProductModel>> {
        return ApiResponseExtractor.extractData(categoriesApi.getProducts(id))
    }

}
