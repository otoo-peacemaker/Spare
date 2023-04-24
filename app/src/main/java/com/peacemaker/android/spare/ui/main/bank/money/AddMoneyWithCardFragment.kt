package com.peacemaker.android.spare.ui.main.bank.money

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.peacemaker.android.spare.R
import com.peacemaker.android.spare.databinding.FragmentAddMoneyWithCardBinding
import com.peacemaker.android.spare.ui.util.BaseFragment

class AddMoneyWithCardFragment : BaseFragment() {
    private var _binding: FragmentAddMoneyWithCardBinding? = null
    private val binding get() = _binding!!
    companion object {
        fun newInstance() = AddMoneyWithCardFragment()
    }

    private lateinit var viewModel: AddMoneyWithCardViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddMoneyWithCardBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[AddMoneyWithCardViewModel::class.java]

        binding.floatingActionButton.setOnClickListener {
            binding.mainL.visibility = View.GONE
            binding.moneySentDialog.root.visibility = View.VISIBLE
            showActionBarOnFragment(this,false)
            binding.moneySentDialog.apply {
                done.setOnClickListener {
                    findNavController().navigate(R.id.action_addMoneyWithCardFragment_to_bottom_nav_graph)
                    binding.mainL.visibility = View.VISIBLE
                    binding.moneySentDialog.root.visibility = View.GONE
                    showActionBarOnFragment(this@AddMoneyWithCardFragment,true)
                }
                addMoreMoney.setOnClickListener {
                    binding.mainL.visibility = View.VISIBLE
                    binding.moneySentDialog.root.visibility = View.GONE
                    showActionBarOnFragment(this@AddMoneyWithCardFragment,true)
                }
                handleOnBackPressed {
                    binding.mainL.visibility = View.VISIBLE
                    binding.moneySentDialog.root.visibility = View.GONE
                    showActionBarOnFragment(this@AddMoneyWithCardFragment,true)
                }
            }
        }
    }
}

