package com.randomx.travel.data.local

import androidx.lifecycle.LiveData

interface WishlistRepositoryInterface {

    fun getAll(): LiveData<List<Wishlist>>

    suspend fun get(id: Int): Wishlist

    suspend fun getByProductId(product_id: String): Wishlist?

    suspend fun remove(wishlist: Wishlist)

    suspend fun insert(wishlist: Wishlist)

    suspend fun update(wishlist: Wishlist)

}