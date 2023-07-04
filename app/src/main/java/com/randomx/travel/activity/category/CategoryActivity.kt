package com.randomx.travel.activity.category

import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.randomx.travel.R
import com.randomx.travel.activity.BaseActivity
import com.randomx.travel.activity.destination.DestinationHomeActivity
import com.randomx.travel.fragment.CategoryProductsFragment
import com.randomx.travel.model.CategoryModel
import com.randomx.travel.model.CategoryViewModel
import com.randomx.travel.model.DestinationModel
import com.randomx.travel.model.ProductModel
import com.randomx.travel.model.ProductsViewModel
import com.randomx.travel.network.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

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


        category = currentCategory()
        categoryViewModel = ViewModelProvider(this)[CategoryViewModel::class.java]
        categoryViewModel.setCategory(category)

        productsViewModel = ViewModelProvider(this)[ProductsViewModel::class.java]
        productsViewModel.setProducts(getProducts())

        findViewById<TextView>(R.id.toolbar_title).text = category.categoryName.toString()

        supportFragmentManager.beginTransaction()
            .replace(R.id.category_fragment_container, CategoryProductsFragment())
            .commit()
    }

    private fun currentCategory(): CategoryModel
    {
        var currentCategory: CategoryModel? = null
        val categoryJson = intent.getStringExtra("category")
        val categoryId = intent.getStringExtra("category_id")

        try {

            when {
                !categoryJson.isNullOrEmpty() -> {

                    currentCategory = CategoryModel.fromJson(categoryJson)
                }
                !categoryId.isNullOrEmpty() -> {
                    runBlocking {
                        currentCategory = withContext(Dispatchers.IO) { apiCategories().get(categoryId).data as CategoryModel }
                    }

                }
            }
        }
        catch (e: Exception)
        {
            safeError(DestinationHomeActivity::class.java, null, getString(R.string.destination_unknwown), e, mapOf("destination" to categoryJson as String, "category_id" to categoryId as String))
        }

        if (currentCategory?.categoryID.isNullOrEmpty())
        {
            safeError(DestinationHomeActivity::class.java, null, getString(R.string.destination_unknwown), null, mapOf("destination" to categoryJson as String, "category_id" to categoryId as String))
        }

        return currentCategory!!


    }

    private fun getProducts(): List<ProductModel> = runBlocking {
        val response: ApiResponse<List<ProductModel>> = apiCategories().getProducts(category.categoryID.toString())
        return@runBlocking response.data?:emptyList<ProductModel>()
    }

}