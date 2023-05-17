package com.randomx.travel.activity.home

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.randomx.travel.R
import com.randomx.travel.activity.BaseActivity
import com.randomx.travel.adapter.HomeCategoriesAdapter
import com.randomx.travel.model.CategoryModel
import com.randomx.travel.model.destination.DestinationModel
import com.randomx.travel.network.ApiResponse
import com.randomx.travel.network.datasource.ManagerDataSource
import kotlinx.coroutines.runBlocking


class HomeActivity : BaseActivity() {

    private lateinit var homeCategoriesRecyclerView: RecyclerView
    private lateinit var homeCategoriesAdapter: HomeCategoriesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        val destinations = getDestinations()
        val categories = getCategories()

        homeCategoriesRecyclerView = findViewById(R.id.homeCategoriesRecyclerView)
        homeCategoriesAdapter = HomeCategoriesAdapter(categories)

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = RecyclerView.HORIZONTAL
        homeCategoriesRecyclerView.layoutManager = layoutManager
        homeCategoriesRecyclerView.adapter = homeCategoriesAdapter


    }

    private fun getCategories(): List<CategoryModel> = runBlocking {
        val response: ApiResponse<List<CategoryModel>> = ManagerDataSource.getInstance().categories().getRandom(4)
        return@runBlocking response.data?:emptyList<CategoryModel>()
    }
    private fun getDestinations(): List<DestinationModel> = runBlocking {
        val response: ApiResponse<List<DestinationModel>> = ManagerDataSource.getInstance().destinations().getRandom(4)
        return@runBlocking response.data?:emptyList<DestinationModel>()
    }


}