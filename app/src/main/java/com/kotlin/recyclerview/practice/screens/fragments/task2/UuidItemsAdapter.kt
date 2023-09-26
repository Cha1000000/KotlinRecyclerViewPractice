package com.kotlin.recyclerview.practice.screens.fragments.task2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.recyclerview.practice.data.UuidItem
import com.kotlin.recyclerview.practice.databinding.ViewholderUuidItemBinding

class UuidItemsAdapter(private val onRefreshClickCallback: (UuidItem) -> Unit) :
    ListAdapter<UuidItem, UuidItemsAdapter.ItemViewHolder>(UuidItemDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder =
        ItemViewHolder(
            ViewholderUuidItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onRefreshClickCallback
        )

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) =
        holder.bind(getItem(position))

    inner class ItemViewHolder(
        private val binding: ViewholderUuidItemBinding,
        private val onRefreshClick: (UuidItem) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: UuidItem) = with(binding) {
            uuidTextWithImage.text = item.uuid
            uuidTextWithImage.setOnClickListener {
                val refreshButton = uuidTextWithImage.compoundDrawables[2]
                if (refreshButton != null) {
                    onRefreshClick(item)
                }
            }
        }
    }
}

object UuidItemDiffCallback : DiffUtil.ItemCallback<UuidItem>() {

    override fun areItemsTheSame(oldItem: UuidItem, newItem: UuidItem) =
        oldItem.uuid == newItem.uuid

    override fun areContentsTheSame(oldItem: UuidItem, newItem: UuidItem) =
        oldItem == newItem
}