package com.randomx.travel.model

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.randomx.travel.model.assets.ImageModel

data class DestinationModel(
    @SerializedName("SPDestinationID") val destinationID: String? = null,
    @SerializedName("SPDestinationSlug") val destinationSlug: String? = null,
    @SerializedName("SPDestinationName") val destinationName: String? = null,
    @SerializedName("SPDestinationContentShort") val destinationContentShort: String? = null,
    @SerializedName("SPDestinationContentFull") val destinationContentFull: String? = null,
    @SerializedName("SPDestinationImage") val destinationImage: String? = null,
    @SerializedName("SPDestinationImages") val destinationImages: Map<String, ImageModel>? = null,
    @SerializedName("SPDestinationTags") val destinationTags: List<String>? = null
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
