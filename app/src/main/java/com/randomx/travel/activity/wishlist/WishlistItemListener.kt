package com.randomx.travel.activity.wishlist

import com.randomx.travel.data.local.Wishlist

interface WishlistItemListener {
    fun onEdit(wishlist: Wishlist)

    fun onDelete(wishlist: Wishlist)

}
