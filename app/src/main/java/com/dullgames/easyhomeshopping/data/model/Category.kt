package com.dullgames.easyhomeshopping.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category_table")
data class Category(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    var items: List<Item>,
    val name: String,
    private var _isCollapsable: Boolean? = true,
){
    var isCollapsable
        get() = _isCollapsable ?: true
        set(value) {
            _isCollapsable = value
        }

}