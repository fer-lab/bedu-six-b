package com.randomx.travel.activity.category

import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.randomx.travel.R
import com.randomx.travel.activity.BaseActivity
import com.randomx.travel.fragment.CategoryProductsFragment
import com.randomx.travel.model.CategoryModel
import com.randomx.travel.model.CategoryViewModel
import com.randomx.travel.model.ProductModel
import com.randomx.travel.model.ProductsViewModel
import com.randomx.travel.network.ApiResponse
import kotlinx.coroutines.runBlocking

class CategoryActivity : BaseActivity() {

    private lateinit var category: CategoryModel
    private lateinit var categoryViewModel: CategoryViewModel
    private lateinit var productsViewModel: ProductsViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        initComponent()

    }

    private fun initComponent() {


        category = CategoryModel.fromJson(intent.getStringExtra("category")?:"{}")
        categoryViewModel = ViewModelProvider(this)[CategoryViewModel::class.java]
        categoryViewModel.setCategory(category)

        productsViewModel = ViewModelProvider(this)[ProductsViewModel::class.java]
        productsViewModel.setProducts(getProducts())

        findViewById<TextView>(R.id.toolbar_title).text = category.categoryName.toString()

        supportFragmentManager.beginTransaction()
            .replace(R.id.category_fragment_container, CategoryProductsFragment())
            .commit()
    }

    private fun getProducts(): List<ProductModel> = runBlocking {
        val response: ApiResponse<List<ProductModel>> = apiCategories().getProducts(category.categoryID.toString())
        return@runBlocking response.data?:emptyList<ProductModel>()
    }

}