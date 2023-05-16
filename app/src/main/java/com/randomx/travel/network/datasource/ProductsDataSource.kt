package com.randomx.travel.network.datasource


import ApiClient
import com.randomx.travel.model.ProductModel
import com.randomx.travel.network.ApiResponse
import com.randomx.travel.network.ApiResponseExtractor
import com.randomx.travel.network.api.ProductsApi

class ProductsDataSource {

   private val productsApi: ProductsApi = ApiClient.productsApi

    suspend fun get(id: String): ApiResponse<ProductModel> {
        return ApiResponseExtractor.extractData(productsApi.get(id))
    }
    suspend fun getRandom(items: Int): ApiResponse<List<ProductModel>> {
        return ApiResponseExtractor.extractData(productsApi.getRandom(items))
    }


}
