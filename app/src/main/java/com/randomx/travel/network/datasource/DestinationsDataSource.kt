package com.randomx.travel.network.datasource


import ApiClient
import com.randomx.travel.model.DestinationModel
import com.randomx.travel.model.ProductModel
import com.randomx.travel.network.ApiResponse
import com.randomx.travel.network.ApiResponseExtractor
import com.randomx.travel.network.api.DestinationsApi

class DestinationsDataSource {

    private val destinationsApi: DestinationsApi = ApiClient.destinationsApi

    suspend fun get(id: String): ApiResponse<DestinationModel> {
        return ApiResponseExtractor.extractData(destinationsApi.get(id))
    }

    suspend fun getAll(): ApiResponse<List<DestinationModel>> {
        return ApiResponseExtractor.extractData(destinationsApi.getAll())
    }

    suspend fun getRandom(items: Int): ApiResponse<List<DestinationModel>> {
        return ApiResponseExtractor.extractData(destinationsApi.getRandom(items))
    }

    suspend fun getProducts(id: String): ApiResponse<List<ProductModel>> {
        return ApiResponseExtractor.extractData(destinationsApi.getProducts(id))
    }

}
