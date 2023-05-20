package com.randomx.travel.activity.destination

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.randomx.travel.R
import com.randomx.travel.activity.BaseRecyclerViewActivity
import com.randomx.travel.adapter.HomeDestinationsAdapter
import com.randomx.travel.model.DestinationModel
import com.randomx.travel.network.ApiResponse
import kotlinx.coroutines.runBlocking

class DestinationHomeActivity : BaseRecyclerViewActivity() {

    override fun initComponent() {
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.activity_destinations_home
    }

    override fun getRecyclerViewResourceId(): Int {
        return R.id.destinations_home_recycler_view
    }

    override fun getAdapterInstance(): RecyclerView.Adapter<*> {
        return HomeDestinationsAdapter(this, getData(), R.layout.home_destinations_adapter)
    }

    override fun getData(): List<DestinationModel> = runBlocking {
        val response: ApiResponse<List<DestinationModel>> = apiDestinations().getAll()
        Log.d("DestinationHomeActivity", "getData: " + response.data.toString())
        return@runBlocking response.data?:emptyList<DestinationModel>()
    }

}