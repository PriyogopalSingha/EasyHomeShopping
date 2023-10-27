package com.dullgames.easyhomeshopping.di

import android.app.Application
import androidx.room.Room
import com.dullgames.easyhomeshopping.data.ShoppingDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HiltModule {
    @Provides
    @Singleton
    fun providesDatabase(
        app: Application,
        callback: ShoppingDatabase.Callback
    ) = Room.databaseBuilder(app,ShoppingDatabase::class.java, "product_table")
        .addCallback(callback)
        .fallbackToDestructiveMigration()
        .build()


    @Provides
    fun providesCategoryDao(db: ShoppingDatabase) = db.categoryDao()

    @Provides
    fun providesCartItemDao(db: ShoppingDatabase) = db.cartItemDao()

    @ApplicationScope
    @Singleton
    @Provides
    fun providesApplicationScope() = CoroutineScope(SupervisorJob())
}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope
