package com.dullgames.easyhomeshopping.data.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation

data class CategoryItem(
    @Embedded
    val category: Category,

    @Relation(
        parentColumn = "name",
        entityColumn = "category_name"
    )

    val item: List<Item>

)
