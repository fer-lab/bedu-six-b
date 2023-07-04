package com.randomx.travel.activity.destination

import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.randomx.travel.R
import com.randomx.travel.activity.BaseActivity
import com.randomx.travel.exceptions.DestinationNotFoundException
import com.randomx.travel.fragment.DestinationProductsFragment
import com.randomx.travel.model.DestinationModel
import com.randomx.travel.model.DestinationViewModel
import com.randomx.travel.model.ProductModel
import com.randomx.travel.model.ProductsViewModel
import com.randomx.travel.network.ApiResponse
import com.randomx.travel.utils.DialogUtils
import com.randomx.travel.utils.logUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

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

        destination = currentDestination()
        destinationViewModel = ViewModelProvider(this)[DestinationViewModel::class.java]
        destinationViewModel.setDestination(destination)

        productsViewModel = ViewModelProvider(this)[ProductsViewModel::class.java]
        productsViewModel.setProducts(getProducts())


        findViewById<TextView>(R.id.toolbar_title).text = destination.destinationName.toString()


        supportFragmentManager.beginTransaction()
            .replace(R.id.destination_fragment_container, DestinationProductsFragment())
            .commit()
    }

    private fun currentDestination(): DestinationModel
    {
        var currentDestination: DestinationModel? = null
        val destinationJson = intent.getStringExtra("destination")
        val destinationId = intent.getStringExtra("destination_id")

        try {

            when {
                !destinationJson.isNullOrEmpty() -> {

                    currentDestination = DestinationModel.fromJson(destinationJson)
                }
                !destinationId.isNullOrEmpty() -> {
                    runBlocking {
                        currentDestination = withContext(Dispatchers.IO) { apiDestinations().get(destinationId).data as DestinationModel }
                    }

                }
            }
        }
        catch (e: Exception)
        {
            safeError(DestinationHomeActivity::class.java, null, getString(R.string.destination_unknwown), e, mapOf("destinationJson" to destinationJson as String, "destinationId" to destinationId as String))
        }

        if (currentDestination?.destinationID.isNullOrEmpty())
        {
            safeError(DestinationHomeActivity::class.java, null, getString(R.string.destination_unknwown), null, mapOf("destinationJson" to destinationJson as String, "destinationId" to destinationId as String))
        }

        return currentDestination!!

    }



    private fun getProducts(): List<ProductModel> = runBlocking {
        val response: ApiResponse<List<ProductModel>> = apiDestinations().getProducts(destination.destinationID.toString())
        return@runBlocking response.data?:emptyList<ProductModel>()
    }

}