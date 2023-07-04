package com.randomx.travel.network.datasource


import ApiClient
import com.randomx.travel.exceptions.ProductNotFoundException
import com.randomx.travel.model.ProductModel
import com.randomx.travel.network.ApiResponse
import com.randomx.travel.network.ApiResponseExtractor
import com.randomx.travel.network.api.ProductsApi

class ProductsDataSource {

   private val productsApi: ProductsApi = ApiClient.productsApi

    suspend fun get(id: String): ApiResponse<ProductModel> {

        val product = ApiResponseExtractor.extractData(productsApi.get(id));

        if (product.data == null || product.data.productID as String != id)
        {
            throw ProductNotFoundException()
        }

        return product
    }
    suspend fun getRandom(items: Int): ApiResponse<List<ProductModel>> {
        return ApiResponseExtractor.extractData(productsApi.getRandom(items))
    }


}
