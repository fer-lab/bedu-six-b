package com.randomx.travel.model

import com.google.gson.annotations.SerializedName
import com.randomx.travel.model.assets.ImageModel

data class ProductModel(
    @SerializedName("ProductID") val productID: String?,
    @SerializedName("ProductName") val productName: String?,
    @SerializedName("ProductSlug") val productSlug: String?,
    @SerializedName("ProductCategories") val productCategories: Map<String, String>?,
    @SerializedName("ProductTitle") val productTitle: String?,
    @SerializedName("ProductType") val productType: String?,
    @SerializedName("ProductContentShort") val productContentShort: String?,
    @SerializedName("ProductContentFull") val productContentFull: String?,
    //@SerializedName("ProductContentMETA") val productContentMETA: Map<String, Any>?,
    @SerializedName("ProductDestinations") val productDestinations: Map<String, String>?,
    @SerializedName("ProductDuration") val productDuration: String?,
    @SerializedName("ProductDurationTime") val productDurationTime: String?,
    @SerializedName("ProductDurationLabel") val productDurationLabel: String?,
    @SerializedName("ProductPriceModel") val productPriceModel: String?,
    @SerializedName("ProductPrice") val productPrice: Double?,
    @SerializedName("ProductPriceA") val productPriceA: Double?,
    @SerializedName("ProductPriceB") val productPriceB: Double?,
    @SerializedName("ProductPriceC") val productPriceC: Double?,
    @SerializedName("ProductPriceD") val productPriceD: Double?,
    @SerializedName("ProductPriceE") val productPriceE: Double?,
    @SerializedName("ProductImage") val productImage: String?,
    @SerializedName("ProductImages") val productImages: Map<String, ImageModel>?,
    @SerializedName("ProductTags") val productTags: List<String>?
)
