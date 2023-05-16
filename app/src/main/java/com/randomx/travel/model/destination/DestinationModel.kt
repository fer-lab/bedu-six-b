package com.randomx.travel.model.destination

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
