package com.peacemaker.android.spare.ui.transfer

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.peacemaker.android.spare.R
import com.peacemaker.android.spare.ui.util.BaseFragment

class TransferFragment : BaseFragment() {

    companion object {
        fun newInstance() = TransferFragment()
    }

    private lateinit var viewModel: TransferViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_transfer, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[TransferViewModel::class.java]
        // TODO: Use the ViewModel
    }

}