package com.peacemaker.android.spare.ui.main.home

import android.content.ContentValues.TAG
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.google.firebase.auth.FirebaseAuth
import com.peacemaker.android.spare.MainActivity
import com.peacemaker.android.spare.R
import com.peacemaker.android.spare.data.Transaction
import com.peacemaker.android.spare.data.crud.getAccountBalanceByUID
import com.peacemaker.android.spare.data.crud.getCollection
import com.peacemaker.android.spare.data.items
import com.peacemaker.android.spare.data.repository.AuthRepository
import com.peacemaker.android.spare.data.repository.FireStoreRepository
import com.peacemaker.android.spare.databinding.FragmentHomeBinding
import com.peacemaker.android.spare.ui.util.BaseFragment
import com.peacemaker.android.spare.ui.viewmodels.UserViewModel
import kotlinx.coroutines.runBlocking
import java.time.LocalTime

@RequiresApi(Build.VERSION_CODES.O)
class HomeFragment : BaseFragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val dataSets = arrayListOf<IBarDataSet?>()
    private lateinit var viewModel : UserViewModel
    private val authRepository by lazy { AuthRepository() }
    private val fireStoreRepository by lazy { FireStoreRepository() }
    private val auth = FirebaseAuth.getInstance()
    private var accBal : Double =0.0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        val homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val txDate = arrayListOf<String>()
        val txItems = arrayListOf(Transaction())


//        binding.spareBal.text = accBal.toString()
        runBlocking {
            val transactions = getCollection("transactions", Transaction::class.java)

            val itemsList = listOf(
                "2023 January",
                filterListBySubstring(transactions,"2023 January"),
                "2023 February",
                filterListBySubstring(transactions,"2023 February"),
                "2023 March",
                filterListBySubstring(transactions,"2023 March"),
                "2023 April",
                filterListBySubstring(transactions,"2023 April"),
                "2023 May",
                filterListBySubstring(transactions,"2023 May"),
                "2023 June",
                filterListBySubstring(transactions,"2023 June"),
                "2023 July",
                filterListBySubstring(transactions,"2023 July"),
                "2023 August",
                filterListBySubstring(transactions,"2023 August"),
                "2023 September",
                filterListBySubstring(transactions,"2023 September"),
                "2023 October",
                filterListBySubstring(transactions,"2023 October"),
                "2023 November",
                filterListBySubstring(transactions,"2023 November"),
                "2023 December",
                filterListBySubstring(transactions,"2023 December")

            )

            log("HomeFragment",":::::::::::::::::::::$itemsList")


            val myRecAdapter = MyRecAdapter(items)
            binding.recyclerView.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = myRecAdapter
            }

            transactions.forEach { transaction ->
                transaction.date?.let {
                    txDate.add(it)

                }
                log("SendFragment",":::::::::::::::::::::::::::$txDate")
                txItems.add(transaction)
            }
            log("SendFragment",":::::::::::::::::::::::::::$transactions")

            filterListBySubstring(transactions,"2023")
            log("SendFragment","::::::::::::::::::::::::::::::::::::::::::::::::::${filterListBySubstring(transactions,"2023")}")
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
               // Toast.makeText(requireContext(), "Selected item: $selectedItem", Toast.LENGTH_SHORT).show()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                // do nothing
            }
        }

        auth.uid?.let {
            getAccountBalanceByUID(it){ accountBalance ->
                if (accountBalance != null) {
                    // Account balance retrieved successfully
                    Log.d(TAG, "Account balance: $accountBalance")
                   // binding.spareAccBalance.text = accountBalance.toString()
                } else {
                    // Failed to retrieve account balance
                    Log.e(TAG, "Error retrieving account balance")
                }
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