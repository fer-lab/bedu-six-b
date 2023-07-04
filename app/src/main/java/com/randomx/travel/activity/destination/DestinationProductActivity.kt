package com.randomx.travel.activity.destination

import android.util.Log
import android.widget.TextView
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.randomx.travel.R
import com.randomx.travel.activity.category.CategoryActivity
import com.randomx.travel.activity.category.CategoryHomeActivity
import com.randomx.travel.activity.product.ProductActivity
import com.randomx.travel.exceptions.DestinationNotFoundException
import com.randomx.travel.model.DestinationModel
import com.randomx.travel.model.ProductCallerModel
import com.randomx.travel.model.ProductModel
import com.randomx.travel.utils.logUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class DestinationProductActivity: ProductActivity() {

    private lateinit var destination: DestinationModel

    override fun getLayout(): Int {
        return R.layout.activity_destination_product
    }

    override fun initProduct() {

        try {
            product = currentProduct() as ProductModel
        } catch (e: Exception) {

            safeError(DestinationActivity::class.java, mapOf("destination_id" to destination.destinationID as String), getString(R.string.destination_unknwown), e, mapOf("destination_id" to destination.destinationID as String))
        }

    }

    override fun initCaller() {


        val destinationData = mapOf<String, String>(
            "destination" to (intent.getStringExtra("destination") ?: ""),
            "destination_id" to (intent.getStringExtra("destination_id") ?: ""),
        )
        when {
            destinationData["destination"]?.isNotEmpty() != null -> {
                destination = DestinationModel.fromJson(destinationData["destination"])
            }
            destinationData["destination_id"]?.isNotEmpty() != null -> {
                runBlocking {

                    try {
                        destination = withContext(Dispatchers.IO) { apiDestinations().get(destinationData["destination_id"] as String).data as DestinationModel }
                    } catch (e: Exception)
                    {
                        safeError(DestinationActivity::class.java, null, getString(R.string.category_unknwown), e, destinationData)
                    }

                }

            }
            else -> {
                safeError(DestinationHomeActivity::class.java, null, getString(R.string.destination_unknwown), DestinationNotFoundException(), destinationData)
            }
        }

        productCaller = ProductCallerModel(destination.destinationID.toString(), "destination")
    }


    override fun initComponent() {
        findViewById<TextView>(R.id.product_name_sub).text = destination.destinationName
    }
}