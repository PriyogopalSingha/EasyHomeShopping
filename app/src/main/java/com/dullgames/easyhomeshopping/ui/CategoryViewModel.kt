package com.dullgames.easyhomeshopping.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dullgames.easyhomeshopping.data.CartItemDao
import com.dullgames.easyhomeshopping.data.CategoryDao
import com.dullgames.easyhomeshopping.data.model.CartItem
import com.dullgames.easyhomeshopping.data.model.Item
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val categoryDao: CategoryDao,
    private val cartItemDao: CartItemDao
) : ViewModel() {

    val itemDataList = categoryDao.getAllItems().asLiveData()
    val categoryDataList = categoryDao.getProductCategories().asLiveData()
    val favoriteItemsList = categoryDao.getFavoriteItems().asLiveData()
    val cartItemsDataList = cartItemDao.getAllItems().asLiveData()


    fun updateItem(item: Item, isFav: Boolean) = viewModelScope.launch {
        categoryDao.updateItem(item.copy(_isFavorite = isFav))
    }

    fun deleteCartItem(cartItem: CartItem) = viewModelScope.launch { cartItemDao.delete(cartItem) }

    fun insertCartItem(cartItem: CartItem) = viewModelScope.launch { cartItemDao.insert(cartItem) }

    fun updateCartItem(cartItem: CartItem, qty: Int) =
        viewModelScope.launch { cartItemDao.update(cartItem.copy(quantity = qty)) }

    fun deleteCart() = viewModelScope.launch { cartItemDao.deleteAllCart() }

}