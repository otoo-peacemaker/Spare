package com.peacemaker.android.spare.ui.home

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.peacemaker.android.spare.R
import com.peacemaker.android.spare.data.items
import com.peacemaker.android.spare.databinding.FragmentHomeBinding
import com.peacemaker.android.spare.model.*
import com.peacemaker.android.spare.ui.util.BaseFragment

class HomeFragment : BaseFragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val dataSets = arrayListOf<IBarDataSet?>()
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

        val autoCompleteTextView = binding.autoCompleteTextView
        val dataSpinner = listOf("Week","Days","Month","Year")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, dataSpinner)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        autoCompleteTextView.adapter = adapter
        autoCompleteTextView.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedItem = parent.getItemAtPosition(position).toString()
                Toast.makeText(requireContext(), "Selected item: $selectedItem", Toast.LENGTH_SHORT).show()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                // do nothing
            }
        }

        dataSets.clear()
        dataSets.add(barDataSet((R.color.light_blue_A200), arrayListOf(BarEntry(3f, 48f)), "January"))
        dataSets.add(barDataSet((R.color.light_blue_A400), arrayListOf(BarEntry(2f, 70f)), "March"))
        dataSets.add(barDataSet((R.color.light_blue_600), arrayListOf(BarEntry(1f, 68.0F)), "April"))
        dataSets.add(barDataSet((R.color.md_theme_dark_error), arrayListOf(BarEntry(2f, 70f)), "Feb"))
        dataSets.add(barDataSet((R.color.light_blue_900), arrayListOf(BarEntry(1f, 68.0F)), "May"))
        setBarChart(binding.bankTransactionChart, dataSets, arrayListOf("January", "March", "April","Feb","May"))

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}