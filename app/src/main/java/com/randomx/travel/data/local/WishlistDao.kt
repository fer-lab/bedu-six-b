package com.randomx.travel.data.local

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface WishlistDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(wishlist: Wishlist)

    @Insert
    suspend fun insertAll(wishlist: List<Wishlist>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(wishlist: Wishlist)

    @Delete
    suspend fun remove(wishlist: Wishlist)

    @Query("DELETE FROM Wishlist WHERE id=:id")
    suspend fun removeById(id: Int)

    @Delete
    suspend fun removeItems(vararg wishlists: Wishlist)

    @Query("SELECT * FROM Wishlist")
    fun getAll(): LiveData<List<Wishlist>>

    @Query("SELECT * FROM Wishlist WHERE id = :id")
    suspend fun get(id: Int): Wishlist

    @Query("SELECT * FROM Wishlist WHERE product_id = :productId")
    suspend fun getByProductId(productId: String) : Wishlist?
}