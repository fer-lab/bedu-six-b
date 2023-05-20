package com.randomx.travel.activity.destination

import android.os.Bundle
import android.widget.TextView
import com.randomx.travel.R
import com.randomx.travel.activity.BaseActivity
import com.randomx.travel.fragment.ProductsFragment
import com.randomx.travel.model.DestinationModel
import com.randomx.travel.model.ProductModel
import com.randomx.travel.network.ApiResponse
import kotlinx.coroutines.runBlocking

class DestinationActivity : BaseActivity() {
    private lateinit var destination: DestinationModel
    private lateinit var destination_name: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_destination)

        initComponent()

    }

    fun initComponent() {
        destination = DestinationModel.fromJson(intent.getStringExtra("entity")?:"{}")

        destination_name = findViewById(R.id.destination_name)
        destination_name.text = destination.destinationName


        supportFragmentManager.beginTransaction()
            .replace(R.id.destination_fragment_container, ProductsFragment.newInstance(getData(), destination))
            .commit()
    }

    fun getData(): List<ProductModel> = runBlocking {
        val response: ApiResponse<List<ProductModel>> = apiDestinations().getProducts(destination.destinationID.toString())
        return@runBlocking response.data?:emptyList<ProductModel>()
    }

}