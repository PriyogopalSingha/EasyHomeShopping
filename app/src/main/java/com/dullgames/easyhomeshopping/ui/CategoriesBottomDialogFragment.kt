package com.dullgames.easyhomeshopping.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dullgames.easyhomeshopping.databinding.TransparentCategoriesLayoutBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CategoriesBottomDialogFragment: BottomSheetDialogFragment() {
    private lateinit var binding : TransparentCategoriesLayoutBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = TransparentCategoriesLayoutBinding.inflate(inflater, container, false)
//        (binding.root.getParent() as View).setBackgroundColor(resources.getColor(R.color.transparent))
        return binding.root
    }


}