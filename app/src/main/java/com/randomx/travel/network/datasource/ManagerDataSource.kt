package com.randomx.travel.network.datasource

class ManagerDataSource {
    private lateinit var _destinations: DestinationsDataSource
    private lateinit var _categories: CategoriesDataSource
    private lateinit var _products: ProductsDataSource

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
    fun categories(): CategoriesDataSource {
        if (!::_categories.isInitialized) {
            _categories = CategoriesDataSource()
        }
        return _categories
    }
    fun products(): ProductsDataSource {
        if (!::_products.isInitialized) {
            _products = ProductsDataSource()
        }
        return _products
    }


}