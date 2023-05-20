package com.randomx.travel.fragment

import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.randomx.travel.R
import com.randomx.travel.model.DestinationViewModel

class DestinationProductsFragment: ProductsFragment() {

    private lateinit var destinationViewModel: DestinationViewModel

    override fun getFragmentLayout(): Int {
        return R.layout.fragment_products
    }

    override fun initViewModel() {
        destinationViewModel = ViewModelProvider(requireActivity())[DestinationViewModel::class.java]
    }

    override fun initView(view: View) {

    }
}