package com.peacemaker.android.spare.ui.main.home

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.peacemaker.android.spare.MainActivity
import com.peacemaker.android.spare.R
import com.peacemaker.android.spare.data.items
import com.peacemaker.android.spare.data.repository.AuthRepository
import com.peacemaker.android.spare.data.repository.FireStoreRepository
import com.peacemaker.android.spare.databinding.FragmentHomeBinding
import com.peacemaker.android.spare.ui.util.BaseFragment
import com.peacemaker.android.spare.ui.viewmodels.UserViewModel
import java.time.LocalTime

@RequiresApi(Build.VERSION_CODES.O)
class HomeFragment : BaseFragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val dataSets = arrayListOf<IBarDataSet?>()
    private lateinit var viewModel : UserViewModel
    private val authRepository by lazy { AuthRepository() }
    private val fireStoreRepository by lazy { FireStoreRepository() }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        val homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

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
        viewModel = ViewModelProvider(this, UserViewModel.UserViewModelFactory(authRepository, fireStoreRepository))[UserViewModel::class.java]
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
        dataSets.add(barDataSet((R.color.light_blue_A200), arrayListOf(BarEntry(5f, 48f)), "January"))
        dataSets.add(barDataSet((R.color.light_blue_A400), arrayListOf(BarEntry(2f, 100f)), "March"))
        dataSets.add(barDataSet((R.color.light_blue_600), arrayListOf(BarEntry(4f, 68.0F)), "April"))
        dataSets.add(barDataSet((R.color.md_theme_dark_error), arrayListOf(BarEntry(3f, 90f)), "Feb"))
        dataSets.add(barDataSet((R.color.light_blue_900), arrayListOf(BarEntry(1f, 80.0F)), "May"))
        setBarChart(binding.bankTransactionChart, dataSets, arrayListOf("January", "March", "April","Feb","May"))

        binding.addMore.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_addMoneyFragment)
        }
        observeData()

    }


    private fun observeData(){
         val mainBinding = (activity as MainActivity).binding
        // Observe the LiveData objects
        viewModel.profilePictureUri.observe(viewLifecycleOwner) { uri ->
            if (uri !=null){
                // Update the profile picture ImageView
                Glide.with(this)
                    .load(uri)
                    .apply(
                        RequestOptions()
                            .placeholder(R.drawable.baseline_person_outline)
                            .error(R.drawable.baseline_person_outline))
                    .into(mainBinding.profileImg)

                val imageView = ImageView(requireContext())
                Glide.with(this)
                    .load(uri)
                    .into(imageView)
               // mainBinding.navView.menu.findItem(R.id.navigation_profile).icon = null
                mainBinding.navView.menu.findItem(R.id.navigation_profile).actionView = imageView
            }
        }
        viewModel.name.observe(viewLifecycleOwner) { name ->
            mainBinding.displayName.text=name
            val currentTime = LocalTime.now()
            when {
                currentTime.isBefore(LocalTime.NOON) -> {
                    mainBinding.actionBarSubtitle.text = "Good morning! ☀️"
                }
                currentTime.isBefore(LocalTime.of(17, 0)) -> {
                    mainBinding.actionBarSubtitle.text = "Good afternoon! \uD83C\uDF1E "
                }
                else -> {
                    mainBinding.actionBarSubtitle.text = "Good evening! \uD83C\uDF06"
                }
            }
        }

        mainBinding.profileImg.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_navigation_profile)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}