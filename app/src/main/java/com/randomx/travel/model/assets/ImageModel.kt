package com.randomx.travel.model.assets

import com.google.gson.annotations.SerializedName

data class ImageModel(
    @SerializedName("id") val id: String?,
    @SerializedName("small") val small: String?,
    @SerializedName("source") val source: String?,
    @SerializedName("regular") val regular: String?,
    @SerializedName("medium") val medium: String?,
    @SerializedName("thumbnail") val thumbnail: String?,
    @SerializedName("description") val description: String?
)
