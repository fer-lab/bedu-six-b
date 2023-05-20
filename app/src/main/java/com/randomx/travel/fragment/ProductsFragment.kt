package com.randomx.travel.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.randomx.travel.R
import com.randomx.travel.adapter.HomeProductsAdapter
import com.randomx.travel.model.CategoryModel
import com.randomx.travel.model.DestinationModel
import com.randomx.travel.model.ProductModel

class ProductsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private var destination: DestinationModel? = null
    private var cagtegory: CategoryModel? = null

    companion object {
        private const val ARG_PRODUCTS = "products"
        private const val ARG_DESTINATION = "destination"
        private const val ARG_CATEGORY = "category"

        fun newInstance(products: List<ProductModel>, category: CategoryModel): ProductsFragment {
            val fragment = ProductsFragment()
            val args = Bundle()
            args.putString(ARG_PRODUCTS, Gson().toJson(products))
            args.putString(ARG_CATEGORY, category.exportToJson())
            fragment.arguments = args
            return fragment
        }

        fun newInstance(products: List<ProductModel>, destination: DestinationModel): ProductsFragment {
            val fragment = ProductsFragment()
            val args = Bundle()
            args.putString(ARG_PRODUCTS, Gson().toJson(products))
            args.putString(ARG_DESTINATION, destination.exportToJson())
            fragment.arguments = args
            return fragment
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_products, container, false)
        recyclerView = view.findViewById(R.id.destination_products_recycler_view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = HomeProductsAdapter(requireContext(), Gson().fromJson(arguments?.getString(ARG_PRODUCTS)?:"[]", Array<ProductModel>::class.java).toList(), R.layout.adapter_products)
        }
    }

}
