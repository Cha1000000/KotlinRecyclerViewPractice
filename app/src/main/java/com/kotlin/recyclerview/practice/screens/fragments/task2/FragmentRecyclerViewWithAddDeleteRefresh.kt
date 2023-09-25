package com.kotlin.recyclerview.practice.screens.fragments.task2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kotlin.recyclerview.practice.R
import com.kotlin.recyclerview.practice.databinding.FragmentRecyclerviewWithAddDeleteRefreshBinding

class FragmentRecyclerViewWithAddDeleteRefresh : Fragment() {

    private lateinit var binding: FragmentRecyclerviewWithAddDeleteRefreshBinding

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
        binding.toolbarTask2.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_fragmentRecyclerViewWithAddDeleteRefresh_to_main)
        }
        return binding.root
    }
}