package com.dullgames.easyhomeshopping.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("item_table")
data class Item(
    val icon: String,
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val price: Double,
    var category_name: String,
    private var _isFavorite: Boolean = false
){
    var isFavorite: Boolean
        get() = _isFavorite
        set(value) {
            _isFavorite = value
        }
}