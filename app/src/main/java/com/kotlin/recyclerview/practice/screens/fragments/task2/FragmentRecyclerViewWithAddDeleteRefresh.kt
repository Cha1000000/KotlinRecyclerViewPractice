package com.kotlin.recyclerview.practice.screens.fragments.task2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.recyclerview.practice.R
import com.kotlin.recyclerview.practice.data.UuidItem
import com.kotlin.recyclerview.practice.databinding.FragmentRecyclerviewWithAddDeleteRefreshBinding

class FragmentRecyclerViewWithAddDeleteRefresh : Fragment() {

    private lateinit var binding: FragmentRecyclerviewWithAddDeleteRefreshBinding
    private lateinit var adapter: UuidItemsAdapter
    private val uuidItemsViewModel by viewModels<UuidItemsViewModel> {
        UuidsListViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_recyclerview_with_add_delete_refresh,
            container,
            false
        )
        binding.lifecycleOwner = this
        initialize()
        return binding.root
    }

    private fun initialize() = with(binding) {
        toolbarTask2.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_fragmentRecyclerViewWithAddDeleteRefresh_to_main)
        }
        adapter = UuidItemsAdapter { item -> uuidItemsViewModel.refreshUuid(item) }
        val swipeDelete = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = adapter.currentList[viewHolder.bindingAdapterPosition]
                uuidItemsViewModel.deleteUuid(item)
            }
        }
        val touchHelper = ItemTouchHelper(swipeDelete)
        touchHelper.attachToRecyclerView(recyclerViewTask2)
        val itemDecoration: RecyclerView.ItemDecoration =
            DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        recyclerViewTask2.addItemDecoration(itemDecoration)
        recyclerViewTask2.adapter = adapter
        uuidItemsViewModel.uuidLiveData.observe(lifecycleOwner!!) { uuidItemList ->
            uuidItemList?.let {
                adapter.submitList(it as MutableList<UuidItem>)
                recyclerViewTask2.smoothScrollToPosition(it.size)
            }
        }
        addButton.setOnClickListener { uuidItemsViewModel.insertUuid() }
    }

}