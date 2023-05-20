package com.randomx.travel.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProductsViewModel : ViewModel() {
    private val productsData = MutableLiveData<List<ProductModel>>()

    fun setProducts(products: List<ProductModel>) {
        productsData.value = products
    }

    fun getProducts(): LiveData<List<ProductModel>> {
        return productsData
    }
}
