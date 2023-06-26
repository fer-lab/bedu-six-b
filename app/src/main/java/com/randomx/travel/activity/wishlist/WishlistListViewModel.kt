package com.randomx.travel.activity.wishlist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.randomx.travel.data.local.Wishlist
import com.randomx.travel.data.local.WishlistRepository
import kotlinx.coroutines.launch

class WishlistListViewModel(private val wishlistRepository: WishlistRepository): ViewModel(){

    val wishlist: LiveData<List<Wishlist>> = wishlistRepository.getAll()

    private var _editWishId = MutableLiveData<Int?>()
    val eventEditWish = _editWishId

    private var _navigateToDetails = MutableLiveData<Wishlist?>()
    val navigateToDetails: LiveData<Wishlist?> get() = _navigateToDetails

    fun onEdit(wishId: Int){
        eventEditWish.value = wishId
    }

    fun onItemClicked(product: Wishlist) {
        _navigateToDetails.value = product
        Log.d("WishlistListViewModel", "onItemClicked")
    }

    fun onNavigationDone() {
        _navigateToDetails.value = null
    }

    fun remove(wish: Wishlist) = viewModelScope.launch{
        wishlistRepository.remove(wish)
    }

}