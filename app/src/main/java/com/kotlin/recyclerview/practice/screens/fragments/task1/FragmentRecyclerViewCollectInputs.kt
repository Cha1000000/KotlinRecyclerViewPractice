package com.kotlin.recyclerview.practice.screens.fragments.task1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
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
import com.kotlin.recyclerview.practice.interfaces.OnSelectedPositionsChangedListener
import kotlin.properties.Delegates

class FragmentRecyclerViewCollectInputs : Fragment(), OnSelectedPositionsChangedListener {

    private lateinit var binding: FragmentRecyclerviewCollectInputsBinding
    private lateinit var showAnim: Animation
    private lateinit var hideAnim: Animation
    private lateinit var textItemAdapter: TextInputItemsAdapter
    private var hasSelectedItems = false
    private var textItems = initTextInputItems()
    private var isParentFabOpened: Boolean by Delegates
        .observable(false) { _, _, newVal ->
            onIsParentFabOpenedChanged(newVal)
        }

    private fun onIsParentFabOpenedChanged(isOpened: Boolean) = with(binding) {
        if (isOpened) {
            additionalFabContainer.startAnimation(showAnim)
            additionalFabContainer.setVisible(true)
        } else {
            additionalFabContainer.startAnimation(hideAnim)
            additionalFabContainer.setVisible(false)
        }
    }

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
        showAnim = AnimationUtils.loadAnimation(context, R.anim.fab_show_anim)
        hideAnim = AnimationUtils.loadAnimation(context, R.anim.fab_hide_anim)
        textItemAdapter = TextInputItemsAdapter()
        textItemAdapter.setOnSelectedPositionsChangedListener(this)
        initialize()
        return binding.root
    }

    private fun initialize() = with(binding) {
        toolbarTask1.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_fragmentRecyclerViewCollectInputs_to_main)
        }

        recyclerViewTask1.apply {
            setHasFixedSize(true)
            adapter = textItemAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (!deleteActionsFab.isVisible) return
                    isParentFabOpened = if (dy > 0) {
                        deleteActionsFab.shrink()
                        false
                    } else {
                        deleteActionsFab.extend()
                        true
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

        additionalFabContainer.visibility = deleteActionsFab.visibility
        deleteActionsFab.apply {
            hide()
            setOnClickListener {
                isParentFabOpened = !isParentFabOpened
                if (isParentFabOpened) extend() else shrink()
            }
        }
        deleteSelectedFab.setOnClickListener { deleteSelectedItems() }
        deleteAllFab.setOnClickListener { deleteAll() }
    }

    private fun deleteSelectedItems() {
        val selectedItemPositions = textItemAdapter.getSelectedItemPositions().sorted()
        textItems = textItems.filterIndexed { index, _ ->
            !selectedItemPositions.contains(index)
        }
        textItemAdapter.submitList(textItems)
        textItemAdapter.clearSelections()
    }

    private fun deleteAll() {
        textItems = listOf()
        textItemAdapter.submitList(textItems)
        textItemAdapter.clearSelections()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        textItemAdapter.removeOnSelectedPositionsChangedListener()
    }

    override fun onSelectedPositionsRangeChanged(hasItems: Boolean) = with(binding) {
        if (hasSelectedItems == hasItems) return
        hasSelectedItems = hasItems
        if (hasItems) {
            deleteActionsFab.show()
            isParentFabOpened = true
        } else {
            if (isParentFabOpened) {
                isParentFabOpened = false
            }
            deleteActionsFab.hide()
        }
    }
}