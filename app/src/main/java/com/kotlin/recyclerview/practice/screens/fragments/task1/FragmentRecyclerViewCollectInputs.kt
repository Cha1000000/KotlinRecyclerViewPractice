package com.kotlin.recyclerview.practice.screens.fragments.task1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.recyclerview.practice.R
import com.kotlin.recyclerview.practice.common.hideKeyboard
import com.kotlin.recyclerview.practice.common.setVisible
import com.kotlin.recyclerview.practice.common.showAlertDialog
import com.kotlin.recyclerview.practice.data.initTextInputItems
import com.kotlin.recyclerview.practice.databinding.FragmentRecyclerviewCollectInputsBinding

class FragmentRecyclerViewCollectInputs : Fragment() {

    private lateinit var binding: FragmentRecyclerviewCollectInputsBinding
    private var textItems = initTextInputItems()
    private val textItemAdapter = TextInputItemsAdapter()
    private var isParentFabOpened = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_recyclerview_collect_inputs,
            container,
            false
        )
        initialize()
        return binding.root
    }

    private fun initialize() = with(binding) {
        toolbarTask1.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_fragmentRecyclerViewCollectInputs_to_main)
        }
        val showAnim = AnimationUtils.loadAnimation(context, R.anim.fab_show_anim)
        val hideAnim = AnimationUtils.loadAnimation(context, R.anim.fab_hide_anim)

        recyclerViewTask1.apply {
            setHasFixedSize(true)
            adapter = textItemAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (!deleteFab.isVisible) return
                    isParentFabOpened = if (dy > 0) {
                        deleteFab.shrink()
                        false
                    } else {
                        deleteFab.extend()
                        true
                    }
                    if (isParentFabOpened) {
                        additionalFabContainer.startAnimation(showAnim)
                        additionalFabContainer.setVisible(true)
                    } else {
                        additionalFabContainer.startAnimation(hideAnim)
                        additionalFabContainer.setVisible(false)
                    }
                }
            })
        }
        textItemAdapter.submitList(textItems)

        buttonCollectInputs.setOnClickListener { button ->
            button.hideKeyboard()
            var collectedText = ""
            val dataList = textItemAdapter.currentList.toList()
            dataList.onEachIndexed { index, textItemData ->
                collectedText += "${index + 1}. ${textItemData.text}\n"
            }
            context?.showAlertDialog(
                titleRes = R.string.result_alert_title,
                msg = if (dataList.all { it.text.isBlank() })
                    getString(R.string.empty_data) else collectedText,
            )
        }

        deleteFab.apply {
            //hide()
            setOnClickListener {
                isParentFabOpened = !isParentFabOpened
                additionalFabContainer.setVisible(isParentFabOpened)
                if (isParentFabOpened) extend() else shrink()
            }
        }
        deleteSelectedFab.setOnClickListener { deleteSelectedItems() }
        deleteAllFab.setOnClickListener { deleteAll() }
    }

    private fun deleteSelectedItems() {
        val selectedItemPositions = textItemAdapter.getSelectedItemPositions().sortedDescending()
        textItems = textItems.filterIndexed { index, _ ->
            !selectedItemPositions.contains(index)
        }
        textItemAdapter.submitList(textItems)
        textItemAdapter.clearSelections()
    }

    private fun deleteAll() {
        textItemAdapter.submitList(null)
        textItemAdapter.clearSelections()
    }
}