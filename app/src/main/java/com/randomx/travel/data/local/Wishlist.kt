package com.randomx.travel.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(indices = [Index(value = ["product_id"], unique = true)])
data class Wishlist @JvmOverloads constructor(
        @PrimaryKey(autoGenerate = true) val id: Int = 0,
        @ColumnInfo(name = "product_id") val product_id: String?,
        @ColumnInfo val product_name: String?,
        @ColumnInfo val caller_type: String?,
        @ColumnInfo val caller_id: String?,
        @ColumnInfo val image: String?
)