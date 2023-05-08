package com.peacemaker.android.spare.ui.main.bank.bills

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.peacemaker.android.spare.R

class BillsAndServicesFragment : Fragment() {

    companion object {
        fun newInstance() = BillsAndServicesFragment()
    }

    private lateinit var viewModel: BillsAndServicesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bills_and_services, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(BillsAndServicesViewModel::class.java)
        // TODO: Use the ViewModel
    }

}