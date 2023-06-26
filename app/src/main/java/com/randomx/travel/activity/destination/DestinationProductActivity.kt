package com.randomx.travel.activity.destination

import android.widget.TextView
import com.randomx.travel.R
import com.randomx.travel.activity.category.CategoryActivity
import com.randomx.travel.activity.product.ProductActivity
import com.randomx.travel.model.DestinationModel
import com.randomx.travel.model.ProductCaller
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class DestinationProductActivity: ProductActivity() {

    private lateinit var destination: DestinationModel

    override fun getLayout(): Int {
        return R.layout.activity_destination_product
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


        val destinationJson = intent.getStringExtra("destination")
        val destinationId = intent.getStringExtra("destination_id")

        when {
            destinationJson != null -> {
                destination = DestinationModel.fromJson(destinationJson)
            }
            destinationId != null -> {
                runBlocking {
                    val apiDestination = withContext(Dispatchers.IO) { apiDestinations().get(destinationId).data }

                    if (apiDestination != null) {
                        destination = apiDestination
                    } else {
                        product404(DestinationActivity::class.java, "Unknown category")
                    }
                }

            }
            else -> {
                product404(DestinationActivity::class.java, "Unknown category")
            }
        }
        productCaller = ProductCaller(destination.destinationID.toString(), "destination")
    }


    override fun initComponent() {
        findViewById<TextView>(R.id.product_name_sub).text = destination.destinationName
    }
}