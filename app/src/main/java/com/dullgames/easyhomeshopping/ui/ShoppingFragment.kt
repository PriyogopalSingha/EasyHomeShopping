package com.dullgames.easyhomeshopping.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dullgames.easyhomeshopping.R
import com.dullgames.easyhomeshopping.data.model.CartItem
import com.dullgames.easyhomeshopping.data.model.Item
import com.dullgames.easyhomeshopping.databinding.ShoppingMainScreenBinding
import com.dullgames.easyhomeshopping.ui.adapters.CategoryAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShoppingFragment : Fragment(R.layout.shopping_main_screen),
    CategoryAdapter.OnItemClickListener {

    private var FLAG = false
    private val viewModel: CategoryViewModel by viewModels()
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var binding: ShoppingMainScreenBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireActivity(), R.color.orange)
        binding = ShoppingMainScreenBinding.bind(view)
        binding.apply {
            shoppingRecyclerview.apply {
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
            favIcon.setOnClickListener {
                val action = ShoppingFragmentDirections.actionShoppingFragmentToFavoritesFragment()
                findNavController().navigate(action)
            }
            cartIcon.cartIconLayout.setOnClickListener {
                val action = ShoppingFragmentDirections.actionShoppingFragmentToCartFragment()
                findNavController().navigate(action)
            }
            fabCategories.setOnClickListener {
                val bottomSheetFragment = CategoriesBottomDialogFragment()
                bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)
            }
        }

        setUpDrawer()
        var itemsList = ArrayList<Item>()
        viewModel.categoryDataList.observe(viewLifecycleOwner){
            categoryAdapter = CategoryAdapter(it, this, itemsList)
            binding.shoppingRecyclerview.adapter = categoryAdapter
        }
        viewModel.itemDataList.observe(viewLifecycleOwner){
            itemsList = it as ArrayList<Item>
        }

        viewModel.cartItemsDataList.observe(viewLifecycleOwner) {
            binding.cartIcon.cartBadge.text = it.size.toString()
        }



 }

    private fun setUpDrawer() {
        (activity as AppCompatActivity?)!!.setSupportActionBar(binding.appBar as Toolbar?)
        val actionBarDrawerToggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(
            activity,
            binding.drawerLayout,
            binding.appBar as Toolbar?,
            R.string.open_drawer,
            R.string.close_drawer
        ) {}


        binding.apply {
            drawerLayout.addDrawerListener(actionBarDrawerToggle)
            actionBarDrawerToggle.syncState()
            navigationView.setNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.item_profile -> {

                        true
                    }

                    R.id.item_rate_us -> {
                        true
                    }

                    R.id.item_follow_us -> {
                        true
                    }

                    else -> {
                        true
                    }
                }

            }
        }
    }


    override fun onAddProductClick(item: Item) {
        val cartItems = viewModel.cartItemsDataList.value
        if (cartItems == null) {
            viewModel.insertCartItem(CartItem(item = item, quantity = 1))
        } else {
            for (curItem in cartItems) {
                if (curItem.item.id == item.id) {
                    var qty: Int = curItem.quantity
                    qty++
                    viewModel.updateCartItem(curItem, qty)
                    FLAG = true
                    break
                }
            }
            if (!FLAG) viewModel.insertCartItem(CartItem(item = item, quantity = 1))
        }
        Snackbar.make(requireView(), "Added to the Cart", Snackbar.LENGTH_LONG).show()
    }

    override fun removeFromFavorites(item: Item) {

    }


    override fun addToFavourites(item: Item, isFavorite: Boolean) {
        viewModel.updateItem(item, isFavorite)
    }

}