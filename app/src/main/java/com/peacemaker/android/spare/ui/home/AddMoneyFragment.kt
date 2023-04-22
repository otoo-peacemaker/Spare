package com.peacemaker.android.spare.ui.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.peacemaker.android.spare.R
import com.peacemaker.android.spare.databinding.FragmentAddMoneyBinding
import com.peacemaker.android.spare.databinding.FragmentProfileBinding
import com.peacemaker.android.spare.ui.util.BaseFragment

class AddMoneyFragment : BaseFragment() {
    private var _binding: FragmentAddMoneyBinding? = null
    private val binding get() = _binding!!
    companion object {
        fun newInstance() = AddMoneyFragment()
    }

    private lateinit var viewModel: AddMoneyViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddMoneyBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[AddMoneyViewModel::class.java]
        setupUIElement()
    }

    private fun setupUIElement(){
        binding.apply {
            accInfo.apply {
                profileImg.setImageDrawable(getDrawable(R.drawable.ico_bank))
                username.text = getString(R.string.debit_cc)
            }
            accVerification.apply {
                profileImg.setImageDrawable(getDrawable(R.drawable.ico_bank_transfer))
                username.text = getString(R.string.bank_transfer)
            }

        }
    }

}