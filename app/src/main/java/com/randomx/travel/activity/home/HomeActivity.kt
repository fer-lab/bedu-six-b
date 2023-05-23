package com.randomx.travel.activity.home

import android.content.res.Configuration
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.randomx.travel.R
import com.randomx.travel.activity.BaseActivity
import com.randomx.travel.adapter.CategoriesAdapter
import com.randomx.travel.adapter.DestinationsAdapter
import com.randomx.travel.model.CategoryModel
import com.randomx.travel.model.DestinationModel
import com.randomx.travel.network.ApiResponse
import kotlinx.coroutines.runBlocking


class HomeActivity : BaseActivity() {

    private lateinit var homeCategoriesRecyclerView: RecyclerView
    private lateinit var homeCategoriesAdapter: CategoriesAdapter

    private lateinit var homeDestinationsRecyclerView: RecyclerView
    private lateinit var homeDestinationsAdapter: DestinationsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        initCategories()
        initDestinations()

    }

    private fun initCategories() {

        homeCategoriesRecyclerView = findViewById(R.id.home_categories_recycler_view)
        homeCategoriesAdapter = CategoriesAdapter(this, getCategories(), R.layout.home_categories_adapter)

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = RecyclerView.HORIZONTAL
        homeCategoriesRecyclerView.layoutManager = layoutManager
        homeCategoriesRecyclerView.adapter = homeCategoriesAdapter


    }

    private fun initDestinations() {

        homeDestinationsRecyclerView = findViewById(R.id.home_destinations_recycler_view)
        homeDestinationsAdapter = DestinationsAdapter(this, getDestinations(), R.layout.home_destinations_adapter)

        val layoutManager = LinearLayoutManager(this)
        val orientation = resources.configuration.orientation

        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            layoutManager.orientation = RecyclerView.HORIZONTAL
        } else if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            layoutManager.orientation = RecyclerView.VERTICAL
        }

        homeDestinationsRecyclerView.layoutManager = layoutManager
        homeDestinationsRecyclerView.adapter = homeDestinationsAdapter
    }

    private fun getCategories(): List<CategoryModel> = runBlocking {
        val response: ApiResponse<List<CategoryModel>> = apiCategories().getRandom(4)
        return@runBlocking response.data?:emptyList<CategoryModel>()
    }
    private fun getDestinations(): List<DestinationModel> = runBlocking {
        val response: ApiResponse<List<DestinationModel>> = apiDestinations().getRandom(4)
        return@runBlocking response.data?:emptyList<DestinationModel>()
    }

}