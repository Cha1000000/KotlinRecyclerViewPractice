package com.kotlin.recyclerview.practice.screens.fragments.task1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kotlin.recyclerview.practice.R
import com.kotlin.recyclerview.practice.databinding.FragmentRecyclerviewCollectInputsBinding

class FragmentRecyclerViewCollectInputs : Fragment() {

    private lateinit var binding: FragmentRecyclerviewCollectInputsBinding

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
        binding.toolbarTask1.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_fragmentRecyclerViewCollectInputs_to_main)
        }
        return binding.root
    }
}