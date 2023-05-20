package com.randomx.travel.fragment

import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.randomx.travel.R
import com.randomx.travel.model.CategoryViewModel

class CategoryProductsFragment: ProductsFragment() {

    private lateinit var categoryViewModel: CategoryViewModel


    override fun getFragmentLayout(): Int {
        return R.layout.fragment_products
    }

    override fun initViewModel() {
        categoryViewModel = ViewModelProvider(requireActivity())[CategoryViewModel::class.java]
    }

    override fun initView(view: View) {

    }
}