package com.peacemaker.android.spare.ui.main.transfer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.peacemaker.android.spare.databinding.FragmentTransferBinding
import com.peacemaker.android.spare.databinding.TransferSuccessDialogBinding
import com.peacemaker.android.spare.ui.util.BaseFragment

class TransferFragment : BaseFragment() {
    private var _binding: FragmentTransferBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: TransferViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTransferBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[TransferViewModel::class.java]
        binding.send.setOnClickListener {
            showSuccessTransfer()
        }
    }

    private fun showSuccessTransfer(){
      val dialogBinding = inflateViewBindingDialog(TransferSuccessDialogBinding::inflate){dialogBinding ->
            val done = dialogBinding.login
        }
        dialogBinding.show()
    }
}