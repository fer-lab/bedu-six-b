package com.randomx.travel.fragment

import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.randomx.travel.R
import com.randomx.travel.model.CategoryViewModel

class CategoryProductsFragment: ProductsFragment() {

    private lateinit var categoryViewModel: CategoryViewModel

    override fun getOwner(): String {
        return "category";
    }

    override fun getOwnerSerialized(): String {
        categoryViewModel.getCategory().value?.let {
            return it.exportToJson()
        }
        return "{}"
    }
    override fun getFragmentLayout(): Int {
        return R.layout.fragment_products
    }

    override fun initViewModel() {
        categoryViewModel = ViewModelProvider(requireActivity())[CategoryViewModel::class.java]
    }

    override fun initView(view: View) {

    }
}