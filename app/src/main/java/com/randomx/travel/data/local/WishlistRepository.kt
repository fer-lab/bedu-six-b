package com.randomx.travel.data.local

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WishlistRepository(
    private val wishlistDao: WishlistDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    fun getAll(): LiveData<List<Wishlist>> = wishlistDao.getAll()

    suspend fun get(id: Int): Wishlist =  withContext(ioDispatcher){
        return@withContext wishlistDao.get(id)
    }

    suspend fun getByProductId(product_id: String): Wishlist? =  withContext(ioDispatcher){
        return@withContext wishlistDao.getByProductId(product_id)
    }

    suspend fun insertAll(wishlists: List<Wishlist>) = withContext(ioDispatcher){
        return@withContext wishlistDao.insertAll(wishlists)
    }

    suspend fun remove(wishlist: Wishlist){
        coroutineScope {
            launch { wishlistDao.remove(wishlist ) }
        }
    }

    suspend fun insert(wishlist: Wishlist){
        coroutineScope {
            launch { wishlistDao.insert(wishlist ) }
        }
    }

    suspend fun update(wishlist: Wishlist){
        coroutineScope {
            launch { wishlistDao.update(wishlist ) }
        }
    }

}