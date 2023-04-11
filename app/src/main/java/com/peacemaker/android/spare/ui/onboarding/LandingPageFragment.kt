package com.peacemaker.android.spare.ui.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.peacemaker.android.spare.R
import com.peacemaker.android.spare.databinding.FragmentLandingPageBinding

class LandingPageFragment : Fragment() {
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
            findNavController().navigate(R.id.action_landingPageFragment_to_authentication_nav_graph)
        }
        return binding.root
    }

}