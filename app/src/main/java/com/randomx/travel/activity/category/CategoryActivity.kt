package com.randomx.travel.activity.category

import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.randomx.travel.R
import com.randomx.travel.activity.BaseActivity
import com.randomx.travel.fragment.CategoryProductsFragment
import com.randomx.travel.model.CategoryModel
import com.randomx.travel.model.ProductModel
import com.randomx.travel.model.ProductsViewModel
import com.randomx.travel.network.ApiResponse
import kotlinx.coroutines.runBlocking

class CategoryActivity : BaseActivity() {
    private lateinit var category: CategoryModel
    private lateinit var productsViewModel: ProductsViewModel
    private lateinit var category_name: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        initComponent()

    }

    private fun initComponent() {

        category = CategoryModel.fromJson(intent.getStringExtra("category")?:"{}")

        productsViewModel = ViewModelProvider(this)[ProductsViewModel::class.java]
        productsViewModel.setProducts(getProducts())

        supportFragmentManager.beginTransaction()
            .replace(R.id.categories_product_recycler_view, CategoryProductsFragment())
            .commit()
    }

    private fun getProducts(): List<ProductModel> = runBlocking {
        val response: ApiResponse<List<ProductModel>> = apiCategories().getProducts(category.categoryID.toString())
        return@runBlocking response.data?:emptyList<ProductModel>()
    }
}