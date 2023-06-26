package com.randomx.travel.activity.category

import android.widget.TextView
import com.randomx.travel.R
import com.randomx.travel.activity.product.ProductActivity
import com.randomx.travel.model.CategoryModel
import com.randomx.travel.model.ProductCallerModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class CategoryProductActivity: ProductActivity() {

    private lateinit var category: CategoryModel

    override fun getLayout(): Int {
        return R.layout.activity_category_product
    }

    override fun initProduct() {

        val currentProduct = currentProduct()
        if (currentProduct === null) {
            product404(CategoryActivity::class.java)
        }
        else
        {
            product = currentProduct
        }
    }

    override fun initCaller() {

        val categoryJson = intent.getStringExtra("category")
        val categoryId = intent.getStringExtra("category_id")

        when {
            categoryJson != null -> {
                category = CategoryModel.fromJson(categoryJson)
            }
            categoryId != null -> {
                runBlocking {
                    val apiCategory = withContext(Dispatchers.IO) { apiCategories().get(categoryId).data }

                    if (apiCategory != null) {
                        category = apiCategory
                    } else {
                        product404(CategoryActivity::class.java, "Unknown category")
                    }
                }

            }
            else -> {
                product404(CategoryActivity::class.java, "Unknown category")
            }
        }

        productCaller = ProductCallerModel(category.categoryID.toString(), "category")
    }

    override fun initComponent() {
        findViewById<TextView>(R.id.product_name_sub).text = category.categoryName
    }
}