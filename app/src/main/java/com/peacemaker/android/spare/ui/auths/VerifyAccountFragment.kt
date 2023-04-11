package com.peacemaker.android.spare.ui.auths

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.peacemaker.android.spare.R

class VerifyAccountFragment : Fragment() {

    companion object {
        fun newInstance() = VerifyAccountFragment()
    }

    private lateinit var viewModel: VerifyAccountViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_verify_account, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(VerifyAccountViewModel::class.java)
        // TODO: Use the ViewModel
    }

}