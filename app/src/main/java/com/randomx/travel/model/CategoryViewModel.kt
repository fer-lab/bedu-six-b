package com.randomx.travel.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CategoryViewModel : ViewModel() {
    private val categoryData = MutableLiveData<CategoryModel>()

    fun setCategory(category: CategoryModel) {
        categoryData.value = category
    }

    fun getCategory(): LiveData<CategoryModel> {
        return categoryData
    }
}
