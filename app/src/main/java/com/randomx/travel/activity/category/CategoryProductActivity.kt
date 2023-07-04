package com.randomx.travel.activity.category

import android.widget.TextView
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.randomx.travel.R
import com.randomx.travel.activity.product.ProductActivity
import com.randomx.travel.exceptions.CategoryNotFoundException
import com.randomx.travel.model.CategoryModel
import com.randomx.travel.model.ProductCallerModel
import com.randomx.travel.model.ProductModel
import com.randomx.travel.utils.logUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class CategoryProductActivity: ProductActivity() {

    private lateinit var category: CategoryModel

    override fun getLayout(): Int {
        return R.layout.activity_category_product
    }

    override fun initProduct() {

        try {
            product = currentProduct() as ProductModel
        } catch (e: Exception) {
            safeError(CategoryActivity::class.java, mapOf("category_id" to category.categoryID as String), getString(R.string.category_unknwown), e, mapOf("category_id" to category.categoryID as String))
        }

    }

    override fun initCaller() {
        val categoryData = mapOf<String, String>(
            "category" to (intent.getStringExtra("category") ?: ""),
            "category_id" to (intent.getStringExtra("category_id") ?: ""),
        )

        when {
            categoryData["category"]?.isNotEmpty() == true -> {
                category = CategoryModel.fromJson(categoryData["category"])
            }
            categoryData["category_id"]?.isNotEmpty() == true -> {
                runBlocking {

                    try {
                        category = withContext(Dispatchers.IO) { apiCategories().get(categoryData["category_id"] as String).data as CategoryModel }
                    } catch (e: Exception)
                    {
                        safeError(CategoryHomeActivity::class.java, null, getString(R.string.category_unknwown), e, categoryData)
                    }

                }

            }
            else -> {
                safeError(CategoryHomeActivity::class.java, null, getString(R.string.category_unknwown), CategoryNotFoundException(), categoryData)
            }
        }

        productCaller = ProductCallerModel(category.categoryID.toString(), "category")
    }

    override fun initComponent() {
        findViewById<TextView>(R.id.product_name_sub).text = category.categoryName
    }
}