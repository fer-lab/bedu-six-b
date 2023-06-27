package com.randomx.travel.data.local

import android.content.Context
import androidx.annotation.VisibleForTesting

object ServiceLocator {

    private var database: WishlistDb? = null

    @VisibleForTesting
    internal var repository: WishlistRepositoryInterface? = null

    private val lock = Any()

    fun resetRepository(){

        synchronized(lock){
            database?.apply {
                clearAllTables()
                close()
            }
            database = null
            repository = null
        }

    }

    fun provideRepository(context: Context): WishlistRepositoryInterface{
        synchronized(lock){
            return repository ?: createRepository(context)
        }
    }

    private fun createRepository(context: Context): WishlistRepositoryInterface{
        database = WishlistDb.getInstance(context)
        val repo = WishlistRepository(database!!.wishlistDao())
        repository = repo
        return repo
    }

}