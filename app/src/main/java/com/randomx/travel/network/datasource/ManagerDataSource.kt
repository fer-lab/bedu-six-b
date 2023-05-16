package com.randomx.travel.network.datasource

class ManagerDataSource {
    private lateinit var _destinations: DestinationsDataSource

    companion object {
        @Volatile
        private var instance: ManagerDataSource? = null

        fun getInstance(): ManagerDataSource {
            return instance ?: synchronized(this) {
                instance ?: ManagerDataSource().also { instance = it }
            }
        }
    }

    fun destinations(): DestinationsDataSource {
        if (!::_destinations.isInitialized) {
            _destinations = DestinationsDataSource()
        }
        return _destinations
    }


}