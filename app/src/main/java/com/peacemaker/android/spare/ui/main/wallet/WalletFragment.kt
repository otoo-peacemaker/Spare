package com.peacemaker.android.spare.ui.main.wallet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.peacemaker.android.spare.data.items
import com.peacemaker.android.spare.databinding.FragmentWalletBinding
import com.peacemaker.android.spare.ui.main.home.MyRecAdapter
import com.peacemaker.android.spare.ui.util.BaseFragment

class WalletFragment : BaseFragment() {
    private var _binding: FragmentWalletBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: WalletViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWalletBinding.inflate(inflater, container, false)
        binding.apply {
            val cards = listOf(
                CreditCard("Visa", "1234 5678 9012 3456", "Kaska Doe", "12/23"),
                CreditCard("Mastercard", "2345 6789 0123 4567", "Jane Doe", "01/25"),
                CreditCard("Mastercard", "3456 7890 1234 5678", "Bob Smith", "06/22"),
            )
            cards.forEach { card ->
                when (card.cardType) {
                    "Visa" -> {}
                    "Mastercard" -> {}
                    "Visa" -> {}
                }

            }
            val cardAdapter = CreditCardAdapter(cards)
            viewPager.adapter = cardAdapter
            viewPager.offscreenPageLimit = 3
            dotsIndicator.attachTo(viewPager)


            val autoCompleteTextView = autoCompleteTextView
            val dataSpinner = listOf("Week", "Days", "Month", "Year")
            val adapter =
                ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, dataSpinner)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            autoCompleteTextView.adapter = adapter
            autoCompleteTextView.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        val selectedItem = parent.getItemAtPosition(position).toString()
                    }

                    override fun onNothingSelected(parent: AdapterView<*>) {
                        // do nothing
                    }
                }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[WalletViewModel::class.java]
        val myRecAdapter = MyRecAdapter(items)
        // recyclerView.adapter = adapter
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = myRecAdapter
        }
    }
}