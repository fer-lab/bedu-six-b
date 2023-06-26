package com.randomx.travel.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Wishlist::class], version = 2)
abstract class WishlistDb : RoomDatabase(){

    companion object {
        private var dbInstance: WishlistDb? = null

        const val DB_NAME = "WISHLIST_DB"

        fun getInstance(context: Context) : WishlistDb?{
            if(dbInstance ==null){

                synchronized(WishlistDb::class){
                    Room.databaseBuilder(
                        context.applicationContext,
                        WishlistDb::class.java,
                        DB_NAME
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                        .also { dbInstance = it }
                }
            }

            return dbInstance
        }
    }

    abstract fun wishlistDao(): WishlistDao

}