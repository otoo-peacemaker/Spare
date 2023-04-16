package com.peacemaker.android.spare.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.peacemaker.android.spare.R
import com.peacemaker.android.spare.databinding.FragmentHomeBinding
import com.peacemaker.android.spare.ui.util.BaseFragment

class HomeFragment : BaseFragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeRecyclerViewAdapter = HomeRecyclerViewAdapter()

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

        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = homeRecyclerViewAdapter
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAutoCompleteTextView(binding.autoCompleteTextView, listOf("Week","Days","Month","Year")){

        }

        val homeItemsList = mutableListOf<HomeRecyclerViewItem>()
        homeItemsList.add(HomeRecyclerViewItem.Title(1, "May"))
        homeItemsList.add(HomeRecyclerViewItem.Title(1, "May"))
        homeItemsList.addAll(listOf(
            HomeRecyclerViewItem.Transaction(1,"Deposit","$2000","may 10"),
            HomeRecyclerViewItem.Transaction(1,"Send","$66000","may 10"),
            HomeRecyclerViewItem.Transaction(1,"Send","$67000","may 10"),
            HomeRecyclerViewItem.Transaction(1,"Deposit","$21000","may 10"),
        ))
        homeItemsList.add(HomeRecyclerViewItem.Title(1, "June"))
        homeItemsList.add(HomeRecyclerViewItem.Title(1, "June"))
        homeItemsList.addAll(listOf(
            HomeRecyclerViewItem.Transaction(1,"Deposit","$2000","may 10"),
            HomeRecyclerViewItem.Transaction(1,"Send","$66000","may 10"),
            HomeRecyclerViewItem.Transaction(1,"Send","$67000","may 10"),
            HomeRecyclerViewItem.Transaction(1,"Deposit","$21000","may 10"),
        ))

        var items = listOf<HomeRecyclerViewItem>()
        homeItemsList.forEach {
            items = listOf(it)
        }

        homeRecyclerViewAdapter.items =items
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}