package com.dullgames.easyhomeshopping.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dullgames.easyhomeshopping.R
import com.dullgames.easyhomeshopping.data.model.Item
import com.dullgames.easyhomeshopping.databinding.FavItemLayoutBinding

class FavoriteScreenAdapter(private val items: List<Item>, private val listener: CategoryAdapter.OnItemClickListener): RecyclerView.Adapter<FavoriteScreenAdapter.FavoritesViewHolder>() {
    inner class FavoritesViewHolder(private val binding: FavItemLayoutBinding): RecyclerView.ViewHolder(binding.root){
        init {
            binding.addProductButton.setOnClickListener{
                val position = adapterPosition
                if(position != RecyclerView.NO_POSITION){
                    val product = items[position]
                    listener.onAddProductClick(product)
                }
            }
            binding.likeImageview.setOnClickListener {
                val position = adapterPosition
                if(position != RecyclerView.NO_POSITION){
                    val product = items[position]
                    listener.removeFromFavorites(product)
                }
            }
        }
        fun bind(favItem: Item) {
            binding.apply {
                val options = RequestOptions.placeholderOf(R.drawable.loading_icon)
                    .error(R.drawable.error_icon)
                Glide.with(productImageView).setDefaultRequestOptions(options).load(favItem.icon)
                    .into(productImageView)


                productNameTextView.text = favItem.name
                priceTextview.text = favItem.price.toString()
            }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        return FavoritesViewHolder(
            FavItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }
}