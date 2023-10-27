package com.dullgames.easyhomeshopping.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dullgames.easyhomeshopping.R
import com.dullgames.easyhomeshopping.data.model.CartItem
import com.dullgames.easyhomeshopping.databinding.FragmentCartBinding
import com.dullgames.easyhomeshopping.ui.adapters.CartAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment(R.layout.fragment_cart), CartAdapter.OnItemClickListener {
    val viewModel: CategoryViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentCartBinding.bind(view)
        val adapter = CartAdapter(this)
        binding.cartRecyclerView.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(requireContext())

        }

        viewModel.cartItemsDataList.observe(viewLifecycleOwner) { dataList ->
            var totalPrice = 0.0
            for (i in dataList) {
                totalPrice += (i.quantity * i.item.price)
            }
            binding.initialPriceTextView.text = String.format("%.2f", totalPrice)
            binding.totalPriceTextview.text = String.format("%.2f", totalPrice)
            adapter.submitList(dataList)
        }

        binding.discountTextView.text = "0%"
        binding.cartToolbar.setNavigationOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        binding.checkoutbutton.setOnClickListener {
            viewModel.deleteCart()
            binding.initialPriceTextView.text = "0"
            binding.totalPriceTextview.text = "0"
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    override fun addCartItem(cartItem: CartItem) {
        var qty = cartItem.quantity
        qty++
        viewModel.updateCartItem(cartItem, qty)
    }

    override fun deleteCartItem(cartItem: CartItem) {
        var qty = cartItem.quantity
        if (qty == 1) viewModel.deleteCartItem(cartItem)
        else {
            qty--
            viewModel.updateCartItem(cartItem, qty)
        }
    }
}