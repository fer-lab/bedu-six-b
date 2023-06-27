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
): WishlistRepositoryInterface {

    override fun getAll(): LiveData<List<Wishlist>> = wishlistDao.getAll()

    override suspend fun get(id: Int): Wishlist =  withContext(ioDispatcher){
        return@withContext wishlistDao.get(id)
    }

    override suspend fun getByProductId(product_id: String): Wishlist? =  withContext(ioDispatcher){
        return@withContext wishlistDao.getByProductId(product_id)
    }

    override suspend fun remove(wishlist: Wishlist){
        coroutineScope {
            launch { wishlistDao.remove(wishlist ) }
        }
    }

    override suspend fun insert(wishlist: Wishlist){
        coroutineScope {
            launch { wishlistDao.insert(wishlist ) }
        }
    }

    override suspend fun update(wishlist: Wishlist){
        coroutineScope {
            launch { wishlistDao.update(wishlist ) }
        }
    }

}