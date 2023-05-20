package com.randomx.travel.model

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.randomx.travel.model.assets.ImageModel

data class DestinationModel(
    @SerializedName("SPDestinationID") val destinationID: String?,
    @SerializedName("SPDestinationSlug") val destinationSlug: String?,
    @SerializedName("SPDestinationName") val destinationName: String?,
    @SerializedName("SPDestinationContentShort") val destinationContentShort: String?,
    @SerializedName("SPDestinationContentFull") val destinationContentFull: String?,
    @SerializedName("SPDestinationImage") val destinationImage: String?,
    @SerializedName("SPDestinationImages") val destinationImages: Map<String, ImageModel>?,
    @SerializedName("SPDestinationTags") val destinationTags: List<String>?
)
{
    companion object {
        fun fromJson(json: String?): DestinationModel {
            return Gson().fromJson(json?:"{}", DestinationModel::class.java)
        }
    }

    fun exportToJson(): String {
        return Gson().toJson(this)
    }
}
