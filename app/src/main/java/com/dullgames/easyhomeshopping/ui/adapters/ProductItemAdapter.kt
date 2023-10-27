package com.dullgames.easyhomeshopping.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dullgames.easyhomeshopping.R
import com.dullgames.easyhomeshopping.data.model.Item
import com.dullgames.easyhomeshopping.databinding.ItemLayoutBinding

class ProductItemAdapter(
    private val itemsList: List<Item>,
    private val listener: CategoryAdapter.OnItemClickListener
) : RecyclerView.Adapter<ProductItemAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.apply {
                likeImageview.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val product = itemsList[position]
                        if (it.isSelected) {
                            it.isSelected = false
                            listener.addToFavourites(product, false)
                        } else {
                            it.isSelected = true
                            listener.addToFavourites(product, true)
                            xplosion.likeAnimation()
                        }
                    }
                }
                addProductButton.setOnClickListener{
                    val position = adapterPosition
                    if(position != RecyclerView.NO_POSITION){
                        val product = itemsList[position]
                        listener.onAddProductClick(product)
                    }
                }
            }
        }

        fun bind(item: Item) {
            binding.apply {
                itemTitleTextview.text = item.name
                itemPriceTextview.text = item.price.toString()
                val icon = if(item.isFavorite) R.drawable.ic_heart_red else R.drawable.heart_selector
                likeImageview.setImageResource(icon)
                val options = RequestOptions.placeholderOf(R.drawable.loading_icon)
                    .error(R.drawable.error_icon)
                Glide.with(itemImageview).setDefaultRequestOptions(options).load(item.icon)
                    .into(itemImageview)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            ItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item = itemsList[position]
        holder.bind(item)
    }

}
