package com.dullgames.easyhomeshopping.data

import android.app.Application
import android.util.Log
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.dullgames.easyhomeshopping.R
import com.dullgames.easyhomeshopping.data.model.CartItem
import com.dullgames.easyhomeshopping.data.model.Category
import com.dullgames.easyhomeshopping.data.model.Item
import com.dullgames.easyhomeshopping.data.model.ProductCategories
import com.dullgames.easyhomeshopping.di.ApplicationScope
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [Category::class, CartItem::class, Item::class], version = 1, exportSchema = false)
@TypeConverters(Convertors::class)
abstract class ShoppingDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun cartItemDao(): CartItemDao

    class Callback @Inject constructor(
        private val database: Provider<ShoppingDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope,
        private val app: Application
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            val dao = database.get().categoryDao()
            applicationScope.launch {
                prePopulateDatabase(app = app, dao = dao)
            }
        }

        private suspend fun prePopulateDatabase(dao: CategoryDao, app: Application) {

            val jsonString = app.resources.openRawResource(R.raw.shopping).bufferedReader().use {
                it.readText()
            }
            val productCategories: ProductCategories =
                Gson().fromJson(jsonString, ProductCategories::class.java)
            for (category in productCategories.categories) {
                dao.insert(category)
                category.items.forEach { item ->
                    dao.insertItem(
                        Item(
                            name = item.name,
                            icon = item.icon,
                            price = item.price,
                            category_name = category.name,
                            _isFavorite = false,
                            id = item.id
                        )
                    )
                }
            }

        }
    }


}