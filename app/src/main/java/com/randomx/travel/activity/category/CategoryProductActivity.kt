package com.randomx.travel.activity.category

import android.widget.TextView
import com.randomx.travel.R
import com.randomx.travel.activity.product.ProductActivity
import com.randomx.travel.model.CategoryModel

class CategoryProductActivity: ProductActivity() {

    private lateinit var category: CategoryModel

    override fun getLayout(): Int {
        return R.layout.activity_category_product;
    }

    override fun initCaller() {
        category = CategoryModel.fromJson(intent.getStringExtra("category")?:"{}")
    }

    override fun initComponent() {
        findViewById<TextView>(R.id.product_name_sub).text = category.categoryName
    }
}