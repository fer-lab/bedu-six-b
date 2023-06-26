package com.randomx.travel.model

data class ProductCallerModel(val callerId: String, val callerType: String)
{
    fun isCategory(): Boolean
    {
        return callerType == "category"
    }

    fun isDestination(): Boolean
    {
        return callerType == "destination"
    }
}
