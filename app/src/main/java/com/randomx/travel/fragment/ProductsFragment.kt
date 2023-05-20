package com.randomx.travel.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.randomx.travel.R
import com.randomx.travel.adapter.HomeProductsAdapter
import com.randomx.travel.model.ProductsViewModel

abstract class ProductsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var productsViewModel: ProductsViewModel

    protected abstract fun getFragmentLayout(): Int
    protected abstract fun initView(view: View)
    protected abstract fun initViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        initViewModels()

        val view = inflater.inflate(getFragmentLayout(), container, false)
        initView(view)

        recyclerView = view.findViewById(R.id.products_recycler_view)

        return view
    }

    private fun initViewModels()
    {
        productsViewModel = ViewModelProvider(requireActivity())[ProductsViewModel::class.java]
        initViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = HomeProductsAdapter(requireContext(), productsViewModel.getProducts().value?: emptyList(), R.layout.adapter_products)
        }
    }

}
