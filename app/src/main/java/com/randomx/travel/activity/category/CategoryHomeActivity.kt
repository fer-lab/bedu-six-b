package com.randomx.travel.activity.category

import androidx.recyclerview.widget.RecyclerView
import com.randomx.travel.R
import com.randomx.travel.activity.BaseRecyclerViewActivity
import com.randomx.travel.adapter.HomeCategoriesAdapter
import com.randomx.travel.model.CategoryModel
import com.randomx.travel.network.ApiResponse
import kotlinx.coroutines.runBlocking

class CategoryHomeActivity : BaseRecyclerViewActivity() {


    override fun initComponent() {
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.activity_category_home
    }

    override fun getRecyclerViewResourceId(): Int {
        return R.id.categories_home_recycler_view
    }

    override fun getAdapterInstance(): RecyclerView.Adapter<*> {
        return HomeCategoriesAdapter(this, getData(), R.layout.home_categories_adapter)
    }

    override fun getData(): List<CategoryModel> = runBlocking {
        val response: ApiResponse<List<CategoryModel>> = apiCategories().getAll()
        return@runBlocking response.data?:emptyList<CategoryModel>()
    }

}