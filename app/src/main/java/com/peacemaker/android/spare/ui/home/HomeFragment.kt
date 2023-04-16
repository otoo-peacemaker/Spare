package com.peacemaker.android.spare.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.peacemaker.android.spare.R
import com.peacemaker.android.spare.data.items
import com.peacemaker.android.spare.databinding.FragmentHomeBinding
import com.peacemaker.android.spare.model.*
import com.peacemaker.android.spare.ui.util.BaseFragment

class HomeFragment : BaseFragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        val homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        // Get the action bar instance from the parent activity
        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.apply {
                 title = "Kaska" // Set the new title for the fragment
                 setHomeAsUpIndicator(R.drawable.profile) }

        val myRecAdapter = MyRecAdapter(items)
        // recyclerView.adapter = adapter
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = myRecAdapter
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAutoCompleteTextView(binding.autoCompleteTextView, listOf("Week","Days","Month","Year")){

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}