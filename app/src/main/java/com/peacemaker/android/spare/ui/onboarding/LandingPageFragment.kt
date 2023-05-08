package com.peacemaker.android.spare.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.peacemaker.android.spare.R
import com.peacemaker.android.spare.databinding.FragmentLandingPageBinding
import com.peacemaker.android.spare.ui.util.BaseFragment

class LandingPageFragment : BaseFragment() {
    private var _binding: FragmentLandingPageBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        _binding = FragmentLandingPageBinding.inflate(inflater, container, false)
        FirebaseApp.initializeApp(requireContext())
        auth = Firebase.auth
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        updateUI(auth.currentUser)
    }

    override fun onResume() {
        super.onResume()
        showActionBarOnFragment(this,false)
    }
    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            findNavController().navigate(R.id.action_landingPageFragment_to_bottom_nav_graph)
        } else {
            binding.button.setOnClickListener {
                showLoadingScreen(true)
                findNavController().navigate(R.id.action_landingPageFragment_to_authentication_nav_graph)
            }
        }
    }
    override fun onPause() {
        super.onPause()
        showLoadingScreen(true)
        showActionBarOnFragment(this,true)

    }

}