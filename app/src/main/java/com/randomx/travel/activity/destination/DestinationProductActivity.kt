package com.randomx.travel.activity.destination

import android.widget.TextView
import com.randomx.travel.R
import com.randomx.travel.activity.product.ProductActivity
import com.randomx.travel.model.DestinationModel

class DestinationProductActivity: ProductActivity() {

    private lateinit var destination: DestinationModel

    override fun getLayout(): Int {
        return R.layout.activity_destination_product;
    }

    override fun initCaller() {
        destination = DestinationModel.fromJson(intent.getStringExtra("destination")?:"{}")
    }

    override fun initComponent() {
        findViewById<TextView>(R.id.product_name_sub).text = destination.destinationName
    }
}