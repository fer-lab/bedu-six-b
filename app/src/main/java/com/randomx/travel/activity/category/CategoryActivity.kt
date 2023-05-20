package com.randomx.travel.activity.category

import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.randomx.travel.R
import com.randomx.travel.activity.BaseRecyclerViewActivity
import com.randomx.travel.adapter.HomeProductsAdapter
import com.randomx.travel.model.CategoryModel
import com.randomx.travel.model.ProductModel
import com.randomx.travel.network.ApiResponse
import kotlinx.coroutines.runBlocking

class CategoryActivity : BaseRecyclerViewActivity() {
    private lateinit var category: CategoryModel
    private lateinit var category_name: TextView

    override fun initComponent() {

        val json = intent.getStringExtra("entity")
        category = Gson().fromJson(json, CategoryModel::class.java)

        category_name = findViewById(R.id.category_name)
        category_name.text = category.categoryName.toString()
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.activity_category
    }

    override fun getRecyclerViewResourceId(): Int {
        return R.id.categories_product_recycler_view
    }

    override fun getAdapterInstance(): RecyclerView.Adapter<*> {
        val adapter = HomeProductsAdapter(this, getData(), R.layout.adapter_products)

        Log.d("CategoryActivity", "getAdapterInstance: " + adapter.itemCount)
        return adapter
    }

    override fun getData(): List<ProductModel> = runBlocking {
        val response: ApiResponse<List<ProductModel>> = apiCategories().getProducts(category.categoryID.toString())
        Log.d("CategoryActivity", "getData: " + response.data.toString())
        return@runBlocking response.data?:emptyList<ProductModel>()
    }
}