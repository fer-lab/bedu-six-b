package com.randomx.travel.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProductViewModel : ViewModel() {
    private val productData = MutableLiveData<ProductModel>()

    fun setProduct(products: ProductModel) {
        productData.value = products
    }

    fun getProduct(): LiveData<ProductModel> {
        return productData
    }
}