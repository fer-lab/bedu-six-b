package com.randomx.travel.activity.destination

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.randomx.travel.R
import com.randomx.travel.activity.BaseRecyclerViewActivity
import com.randomx.travel.adapter.DestinationsAdapter
import com.randomx.travel.model.DestinationModel
import com.randomx.travel.network.ApiResponse
import kotlinx.coroutines.runBlocking

class DestinationHomeActivity : BaseRecyclerViewActivity() {

    override fun initComponent() {
        findViewById<TextView>(R.id.toolbar_title).text = getString(R.string.destination_title)
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.activity_destinations_home
    }

    override fun getRecyclerViewResourceId(): Int {
        return R.id.destinations_home_recycler_view
    }

    override fun getAdapterInstance(): RecyclerView.Adapter<*> {
        return DestinationsAdapter(this, getData(), R.layout.home_destinations_adapter)
    }

    override fun getData(): List<DestinationModel> = runBlocking {
        val response: ApiResponse<List<DestinationModel>> = apiDestinations().getAll()
        return@runBlocking response.data?:emptyList<DestinationModel>()
    }

}