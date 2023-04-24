package com.peacemaker.android.spare.ui.main.bank.money

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.peacemaker.android.spare.R
import com.peacemaker.android.spare.databinding.FragmentAddMoneyBinding
import com.peacemaker.android.spare.ui.main.home.HomeViewModel
import com.peacemaker.android.spare.ui.util.BaseFragment

class AddMoneyFragment : BaseFragment() {
    private var _binding: FragmentAddMoneyBinding? = null
    private val binding get() = _binding!!
    companion object {
        fun newInstance() = AddMoneyFragment()
    }

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddMoneyBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        setupUIElement()
    }

    private fun setupUIElement(){
        binding.apply {
            ccCards.apply {
                profileImg.setImageDrawable(getDrawable(R.drawable.ico_bank))
                username.text = getString(R.string.debit_cc)
                root.setOnClickListener {
                    findNavController().navigate(R.id.action_global_cardsFragment)
                }
            }
            backTransfer.apply {
                profileImg.setImageDrawable(getDrawable(R.drawable.ico_bank_transfer))
                username.text = getString(R.string.bank_transfer)
            }
        }
    }
}