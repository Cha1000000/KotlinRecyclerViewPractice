package com.kotlin.recyclerview.practice.screens.fragments.task2

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.TextView
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

        @SuppressLint("ClickableViewAccessibility")
        fun bind(item: UuidItem) = with(binding) {
            uuidTextWithImage.text = item.uuid
            uuidTextWithImage.setOnTouchListener { v, event ->
                if (event.action == MotionEvent.ACTION_DOWN) {
                    val refreshButton = (v as TextView).compoundDrawables[2]
                    val x = event.x.toInt()
                    val y = event.y.toInt()
                    if (refreshButton != null && x >= v.width - v.totalPaddingRight
                        && x <= v.width - v.paddingRight
                        && y >= v.paddingTop && y <= v.height - v.paddingBottom) {
                        onRefreshClick(item)
                        return@setOnTouchListener true
                    }
                }
                false
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