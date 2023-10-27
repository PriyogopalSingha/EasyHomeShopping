package com.dullgames.easyhomeshopping.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dullgames.easyhomeshopping.R
import com.dullgames.easyhomeshopping.data.model.CartItem
import com.dullgames.easyhomeshopping.data.model.Item
import com.dullgames.easyhomeshopping.databinding.FragmentFavoritesBinding
import com.dullgames.easyhomeshopping.ui.adapters.CategoryAdapter
import com.dullgames.easyhomeshopping.ui.adapters.FavoriteScreenAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment(R.layout.fragment_favorites),
    CategoryAdapter.OnItemClickListener {
    private var ADD_PRODUCT_TO_CART_FLAG = false
    private val categoryViewModel by viewModels<CategoryViewModel>()
    private lateinit var favAdapter: FavoriteScreenAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentFavoritesBinding.bind(view)
        binding.favListRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
        categoryViewModel.favoriteItemsList.observe(viewLifecycleOwner) { dataList ->
            favAdapter = FavoriteScreenAdapter(dataList, this)
            binding.favListRecyclerView.adapter = favAdapter
        }


        binding.favsToolbar.setNavigationOnClickListener{
            requireActivity().supportFragmentManager.popBackStack()
        }

        binding.goToCartButton.setOnClickListener {
            val action = FavoritesFragmentDirections.actionFavoritesFragmentToCartFragment()
            findNavController().navigate(action)
        }
    }

    override fun onAddProductClick(item: Item) {
        val cartItems = categoryViewModel.cartItemsDataList.value
        if (cartItems == null) {
            categoryViewModel.insertCartItem(CartItem(item = item, quantity = 1))
        } else {
            cartItems.let {
                for (curItem in it) {
                    if (curItem.item.id == item.id) {
                        var qty: Int = curItem.quantity
                        qty++
                        categoryViewModel.updateCartItem(curItem, qty)
                        ADD_PRODUCT_TO_CART_FLAG = true
                        break
                    }
                }
            }

            if (!ADD_PRODUCT_TO_CART_FLAG) categoryViewModel.insertCartItem(
                CartItem(
                    item = item,
                    quantity = 1
                )
            )
        }
        Snackbar.make(requireView(), "Added to the Cart", Snackbar.LENGTH_LONG).show()

    }

    override fun removeFromFavorites(item: Item) {
        categoryViewModel.updateItem(item, false)
    }

    override fun addToFavourites(item: Item, isFavorite: Boolean) {
        TODO("Not yet implemented")
    }

}