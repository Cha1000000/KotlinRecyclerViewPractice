package com.kotlin.recyclerview.practice.screens.fragments.task1

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.recyclerview.practice.R
import com.kotlin.recyclerview.practice.common.setBackgroundColorRes
import com.kotlin.recyclerview.practice.common.showKeyboardImplicit
import com.kotlin.recyclerview.practice.data.TextItemData
import com.kotlin.recyclerview.practice.databinding.ViewholderTextinputItemBinding

class TextInputItemsAdapter :
    ListAdapter<TextItemData, TextInputItemsAdapter.ItemViewHolder>(ItemDiffCallback) {

    private val selectedPositions = mutableListOf<Int>()

    fun toggleSelection(position: Int) {
        if (selectedPositions.contains(position)) {
            selectedPositions.remove(position)
        } else {
            selectedPositions.add(position)
        }
        notifyItemChanged(position)
    }

    fun clearSelections() {
        selectedPositions.clear()
        notifyItemRangeChanged(0, this.itemCount)
    }

    fun getSelectedItemPositions(): List<Int> {
        return selectedPositions.toList()
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

    inner class ItemViewHolder(binding: ViewholderTextinputItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val currentItem = binding.textInputContainer
        private val textInput = binding.textInputText
        private var currentItemInput: TextItemData? = null
        private var itemPosition: Int = -1

        init {
            currentItem.apply {
                setOnFocusChangeListener { _, hasFocus ->
                    if (hasFocus) {
                        resetSelection()
                        textInput.requestFocus()
                    }
                }
                setOnClickListener {
                    resetSelection()
                    textInput.showKeyboardImplicit()
                }
                setOnLongClickListener {
                    if (textInput.isFocused) textInput.clearFocus()
                    if (itemPosition != RecyclerView.NO_POSITION) {
                        toggleSelection(itemPosition)
                        true
                    } else {
                        false
                    }
                }
            }
            textInput.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) resetSelection()
            }
        }

        private fun resetSelection() {
            if (selectedPositions.isEmpty()) return
            selectedPositions.remove(itemPosition)
            currentItem.setBackgroundColorRes(R.color.white)
        }

        fun bind(textItem: TextItemData) {
            itemPosition = adapterPosition
            currentItemInput = textItem
            textInput.setText(currentItemInput!!.text)
            textInput.addTextChangedListener { currentItemInput!!.text = it.toString() }
            if (itemPosition == adapterPosition) {
                toggleSelectionBackground()
            }
        }

        private fun toggleSelectionBackground() {
            val backgroundColorRes = if (selectedPositions.contains(itemPosition)) {
                R.color.selectedItemBackground
            } else {
                R.color.white
            }
            currentItem.setBackgroundColorRes(backgroundColorRes)
        }
    }
}

object ItemDiffCallback : DiffUtil.ItemCallback<TextItemData>() {

    override fun areItemsTheSame(oldItem: TextItemData, newItem: TextItemData) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: TextItemData, newItem: TextItemData) =
        oldItem == newItem
}