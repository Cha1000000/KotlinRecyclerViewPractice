package com.kotlin.recyclerview.practice.screens.fragments.task1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kotlin.recyclerview.practice.R
import com.kotlin.recyclerview.practice.databinding.FragmentRecyclerviewCombineInputsBinding

class FragmentRecyclerViewCombineInputs : Fragment() {

    private lateinit var binding: FragmentRecyclerviewCombineInputsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_recyclerview_combine_inputs,
            container,
            false
        )
        binding.toolbarTask1.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_fragmentRecyclerViewCombineInputs_to_main)
        }
        return binding.root
    }
}