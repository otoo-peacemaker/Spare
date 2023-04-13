package com.peacemaker.android.spare.ui.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.peacemaker.android.spare.R
import com.peacemaker.android.spare.databinding.FragmentLandingPageBinding
import com.peacemaker.android.spare.ui.util.BaseFragment

class LandingPageFragment : BaseFragment() {
    private var _binding: FragmentLandingPageBinding? = null
    private val binding get() = _binding!!
    companion object {
        fun newInstance() = LandingPageFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLandingPageBinding.inflate(inflater, container, false)

        binding.button.setOnClickListener {
            showLoadingScreen(true)
            findNavController().navigate(R.id.action_landingPageFragment_to_authentication_nav_graph)
            showLoadingScreen(false)
        }
        return binding.root
    }

}