package com.dullgames.easyhomeshopping.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.dullgames.easyhomeshopping.data.model.Category
import com.dullgames.easyhomeshopping.data.model.CategoryItem
import com.dullgames.easyhomeshopping.data.model.Item
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Query("Select * from category_table")
    fun getProductCategories(): Flow<List<Category>>

    @Insert
    suspend fun insert(category: Category)

    @Update
    suspend fun update(category: Category)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: Item)

    @Query("SELECT * FROM item_table")
    fun getAllItems(): Flow<List<Item>>

    @Update
    suspend fun updateItem(item: Item)

    @Query("SELECT * FROM item_table where _isFavorite = 1")
    fun getFavoriteItems(): Flow<List<Item>>



}