package com.randomx.travel.network.datasource


import ApiClient
import com.randomx.travel.model.LocationModel
import com.randomx.travel.network.ApiResponse
import com.randomx.travel.network.ApiResponseExtractor
import com.randomx.travel.network.api.LocationApi

class LocationDataSource {

   private val locationApi: LocationApi = ApiClient.locationApi

    suspend fun get(lat: String, lng: String): ApiResponse<LocationModel> {
        return ApiResponseExtractor.extractData(locationApi.get(lat, lng))
    }

}
