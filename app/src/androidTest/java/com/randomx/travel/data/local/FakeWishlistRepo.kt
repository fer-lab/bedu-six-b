package com.randomx.travel.data.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class FakeWishlistRepo: WishlistRepositoryInterface {

    private var observableWishlist = MutableLiveData<List<Wishlist>>()

    override fun getAll(): LiveData<List<Wishlist>> = observableWishlist

    override suspend fun get(id: Int): Wishlist {
        return emptyWishlist("")
    }



    override suspend fun getByProductId(product_id: String): Wishlist? {
        return emptyWishlist("")
    }

    fun insertAll(wishlists: List<Wishlist>) {
        observableWishlist.value = wishlists
    }

    override suspend fun remove(wishlist: Wishlist) {
        val newList = observableWishlist.value?.toMutableList() ?: mutableListOf()
        newList.remove(wishlist)
        observableWishlist.value = newList
    }

    override suspend fun insert(wishlist: Wishlist) {
        val newList = observableWishlist.value?.toMutableList() ?: mutableListOf()
        newList.add(wishlist)
        observableWishlist.value = newList
    }

    override suspend fun update(wishlist: Wishlist) {

    }



    companion object{

        fun emptyWishlist(name: String): Wishlist {

            return Wishlist(
                id = 0,
                product_id = generateRandomCode(),
                product_name = name,
                caller_id = "1",
                caller_type = "category",
                image = "https://picsum.photos/200/300"
            )
        }

        fun generateRandomCode(): String {
            val letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
            val numbers = "0123456789"

            val randomLetters = (1..3).map { letters.random() }.joinToString("")
            val randomNumbers = (1..4).map { numbers.random() }.joinToString("")

            return randomLetters + randomNumbers
        }
    }

}