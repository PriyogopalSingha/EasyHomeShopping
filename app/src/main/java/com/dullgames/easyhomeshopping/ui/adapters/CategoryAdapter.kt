package com.dullgames.easyhomeshopping.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dullgames.easyhomeshopping.data.model.Category
import com.dullgames.easyhomeshopping.data.model.Item
import com.dullgames.easyhomeshopping.databinding.CategoryLayoutBinding

class CategoryAdapter(
    private val categoryList: List<Category>,
    private val listener: OnItemClickListener,
    private val itemsList: List<Item>
) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {


    inner class CategoryViewHolder(private val binding: CategoryLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category, items: List<Item>) {
            val productItemAdapter = ProductItemAdapter(items, listener)
            binding.apply {
                categoryTitleTextview.text = category.name
                itemRecyclerview.layoutManager =
                    LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
                itemRecyclerview.adapter = productItemAdapter
                itemRecyclerview.visibility =
                    if (category.isCollapsable) View.VISIBLE else View.GONE
                root.setOnClickListener {
                    category.isCollapsable = !category.isCollapsable
                    notifyItemChanged(adapterPosition)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            CategoryLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categoryList[position]
        val items = ArrayList<Item>()
        for (item in itemsList) {
            if (item.category_name == category.name) {
                items.add(item)
            }
        }
        holder.bind(category, items)
    }

    interface OnItemClickListener {
        fun onAddProductClick(item: Item)
        fun removeFromFavorites(item: Item)
        fun addToFavourites(item: Item, isFavorite: Boolean)

    }
}


