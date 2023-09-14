package com.kotlin.recyclerview.practice.screens.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kotlin.recyclerview.practice.R
import com.kotlin.recyclerview.practice.databinding.FragmentMainSelectTaskBinding

class FragmentMainSelectTask : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val binding: FragmentMainSelectTaskBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_main_select_task,
            container,
            false
        )
        binding.buttonTask1.setOnClickListener {
            findNavController().navigate(R.id.action_main_to_fragmentRecyclerViewCombineInputs)
        }
        binding.buttonTask2.setOnClickListener {
            findNavController().navigate(R.id.action_main_to_fragmentRecyclerViewWithAddDeleteRefresh)
        }
        return binding.root
    }
}