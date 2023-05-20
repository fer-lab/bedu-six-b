package com.randomx.travel.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DestinationViewModel : ViewModel() {
    private val destinationData = MutableLiveData<DestinationModel>()


    fun setDestination(destination: DestinationModel) {
        destinationData.value = destination
    }

    fun getDestination(): LiveData<DestinationModel> {
        return destinationData
    }

}
