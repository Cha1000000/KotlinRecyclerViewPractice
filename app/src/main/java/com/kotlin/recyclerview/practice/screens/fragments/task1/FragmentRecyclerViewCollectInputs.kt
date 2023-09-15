package com.kotlin.recyclerview.practice.screens.fragments.task1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kotlin.recyclerview.practice.R
import com.kotlin.recyclerview.practice.common.showAlertDialog
import com.kotlin.recyclerview.practice.data.initTextInputItems
import com.kotlin.recyclerview.practice.databinding.FragmentRecyclerviewCollectInputsBinding

class FragmentRecyclerViewCollectInputs : Fragment() {

    private lateinit var binding: FragmentRecyclerviewCollectInputsBinding
    private val textItems = initTextInputItems()
    private val textItemAdapter = TextInputItemsAdapter()

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
        recyclerViewTask1.apply {
            setHasFixedSize(true)
            adapter = textItemAdapter
        }
        textItemAdapter.submitList(textItems)
        buttonCollectInputs.setOnClickListener {
            var collectedText = ""
            textItemAdapter.currentList
                .onEachIndexed { index, textItemData ->
                    collectedText += "${index + 1}. ${textItemData.text}\n"
                }
            context?.showAlertDialog(
                titleRes = R.string.result_alert_title,
                msg = collectedText,
            )
        }
    }
}