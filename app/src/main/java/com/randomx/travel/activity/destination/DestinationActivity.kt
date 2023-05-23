package com.randomx.travel.activity.destination

import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.randomx.travel.R
import com.randomx.travel.activity.BaseActivity
import com.randomx.travel.fragment.DestinationProductsFragment
import com.randomx.travel.model.DestinationModel
import com.randomx.travel.model.DestinationViewModel
import com.randomx.travel.model.ProductModel
import com.randomx.travel.model.ProductsViewModel
import com.randomx.travel.network.ApiResponse
import com.randomx.travel.utils.ToolsUtils
import kotlinx.coroutines.runBlocking

class DestinationActivity : BaseActivity() {

    private lateinit var destination: DestinationModel
    private lateinit var destination_name: TextView
    private lateinit var destination_description: TextView
    private lateinit var destinationViewModel: DestinationViewModel
    private lateinit var productsViewModel: ProductsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_destination)

        initComponent()

    }

    private fun initComponent() {


        destination = DestinationModel.fromJson(intent.getStringExtra("destination")?:"{}")
        destinationViewModel = ViewModelProvider(this)[DestinationViewModel::class.java]
        destinationViewModel.setDestination(destination)

        productsViewModel = ViewModelProvider(this)[ProductsViewModel::class.java]
        productsViewModel.setProducts(getProducts())


        findViewById<TextView>(R.id.toolbar_title).text = destination.destinationName.toString()


        supportFragmentManager.beginTransaction()
            .replace(R.id.destination_fragment_container, DestinationProductsFragment())
            .commit()
    }

    private fun getProducts(): List<ProductModel> = runBlocking {
        val response: ApiResponse<List<ProductModel>> = apiDestinations().getProducts(destination.destinationID.toString())
        return@runBlocking response.data?:emptyList<ProductModel>()
    }

}