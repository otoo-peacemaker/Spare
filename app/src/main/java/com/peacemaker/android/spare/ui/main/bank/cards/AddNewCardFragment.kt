package com.peacemaker.android.spare.ui.main.bank.cards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.peacemaker.android.spare.R
import com.peacemaker.android.spare.databinding.FragmentAddNewCardBinding

class AddNewCardFragment : Fragment() {

    private var _binding: FragmentAddNewCardBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = AddNewCardFragment()
    }

    private lateinit var viewModel: AddNewCardViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddNewCardBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[AddNewCardViewModel::class.java]
        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_addNewCardFragment_to_addMoneyWithCardFragment)
        }
    }

}