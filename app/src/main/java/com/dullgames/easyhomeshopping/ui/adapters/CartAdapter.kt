package com.dullgames.easyhomeshopping.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dullgames.easyhomeshopping.R
import com.dullgames.easyhomeshopping.data.model.CartItem
import com.dullgames.easyhomeshopping.databinding.CartItemLayoutBinding

class CartAdapter(private val listener: OnItemClickListener): ListAdapter<CartItem, CartAdapter.CartViewHolder>(DiffCallback()){

    inner class CartViewHolder(private val binding: CartItemLayoutBinding) : RecyclerView.ViewHolder(binding.root){
        init {
            binding.apply{
                addProductButton.setOnClickListener{
                    val cartItem = getItem(adapterPosition)
                    listener.addCartItem(cartItem)
                }
                deleteProductButton.setOnClickListener{
                    val cartItem = getItem(adapterPosition)
                    listener.deleteCartItem(cartItem)
                }

            }
        }
        fun bind(cartItem: CartItem){
            binding.apply {
                val options = RequestOptions.placeholderOf(R.drawable.loading_icon)
                    .error(R.drawable.error_icon)
                Glide.with(productImageView).setDefaultRequestOptions(options).load(cartItem.item.icon)
                    .into(productImageView)
                productNameTextView.text = cartItem.item.name
                quantityTextview.text = cartItem.quantity.toString()
                priceTextview.text = cartItem.item.price.toString()
                totalPriceTextview.text = String.format("%.2f", cartItem.item.price * cartItem.quantity)
                }

        }
    }

    class DiffCallback: DiffUtil.ItemCallback<CartItem>(){
        override fun areItemsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = CartItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    interface OnItemClickListener{
        fun addCartItem(cartItem: CartItem)
        fun deleteCartItem(cartItem: CartItem)
    }
}