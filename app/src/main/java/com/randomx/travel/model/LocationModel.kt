package com.randomx.travel.model

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class LocationModel(
    @SerializedName("LocationStreet") val locationStreet: String?,
    @SerializedName("LocationStreetNumber") val locationStreetNumber: String?,
    @SerializedName("LocationLocality") val locationLocality: String?,
    @SerializedName("LocationCity") val locationCity: String?,
    @SerializedName("LocationState") val locationState: String?,
    @SerializedName("LocationCountry") val locationCountry: String?
)
{
    companion object {
        fun fromJson(json: String?): LocationModel {
            return Gson().fromJson(json?:"{}", LocationModel::class.java)
        }
    }
    fun exportToJson(): String {
        return Gson().toJson(this)
    }
}
