package com.randomx.travel.model

import com.google.gson.annotations.SerializedName
import com.randomx.travel.model.assets.ImageModel

data class CategoryModel(
    @SerializedName("SPCategoryID") val categoryID: String?,
    @SerializedName("SPCategoryName") val categoryName: String?,
    @SerializedName("SPCategorySlug") val categorySlug: String?,
    @SerializedName("SPCategoryDescription") val categoryDescription: String?,
    @SerializedName("SPCategoryContent") val categoryContent: String?,
    @SerializedName("SPCategoryImage") val categoryImage: String?,
    @SerializedName("SPCategoryImages") val categoryImages: Map<String, ImageModel>?,
    @SerializedName("SPCategoryTags") val categoryTags: List<String>?
)
