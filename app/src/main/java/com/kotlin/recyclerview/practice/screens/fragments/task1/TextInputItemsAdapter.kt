package com.kotlin.recyclerview.practice.screens.fragments.task1

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.recyclerview.practice.data.TextItemData
import com.kotlin.recyclerview.practice.databinding.ViewholderTextinputItemBinding

class TextInputItemsAdapter :
    ListAdapter<TextItemData, TextInputItemsAdapter.ItemViewHolder>(ItemDiffCallback) {

    inner class ItemViewHolder(binding: ViewholderTextinputItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val textEdit = binding.textInputText
        private var currentItem: TextItemData? = null

        init {
            with(binding) {
                textInputContainer.apply {
                    setOnClickListener { textInputText.requestFocus() }
                    setOnFocusChangeListener { v, hasFocus ->
                        if (hasFocus) textInputText.requestFocus()
                    }
                }
            }
        }

        fun bind(textItem: TextItemData) {
            currentItem = textItem
            textEdit.setText(currentItem!!.text)
            textEdit.addTextChangedListener { currentItem!!.text = it.toString() }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder =
        ItemViewHolder(
            ViewholderTextinputItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) =
        holder.bind(getItem(position))
}

object ItemDiffCallback : DiffUtil.ItemCallback<TextItemData>() {

    override fun areItemsTheSame(oldItem: TextItemData, newItem: TextItemData) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: TextItemData, newItem: TextItemData) =
        oldItem == newItem
}