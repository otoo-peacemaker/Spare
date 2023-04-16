package com.peacemaker.android.spare.ui.auths

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.peacemaker.android.spare.R
import com.peacemaker.android.spare.databinding.FragmentRegistrationBinding
import com.peacemaker.android.spare.ui.util.BaseFragment
import com.peacemaker.android.spare.ui.util.Constants.RC_SIGN_IN
import com.peacemaker.android.spare.ui.util.Status
import com.peacemaker.android.spare.ui.util.changeTextColor

class RegistrationFragment : BaseFragment() {
    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = RegistrationFragment()
    }

    private lateinit var viewModel: AuthViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[AuthViewModel::class.java]
        binding.alreadyHaveAcc.changeTextColor("Sign in", color = R.color.md_theme_light_primary)
        binding.register.setOnClickListener { registerUser() }
        binding.alreadyHaveAcc.setOnClickListener { findNavController().navigate(R.id.action_registrationFragment_to_loginFragment) }
        binding.continueWithGoogle.setOnClickListener { signInWithGoogle() }

        viewModel.createUserLiveData.observe(viewLifecycleOwner) { resource ->
            when (resource?.status) {
                Status.SUCCESS -> {
                    // User account created and information saved successfully
                    Toast.makeText(
                        requireContext(),
                        "User account created and information saved successfully",
                        Toast.LENGTH_LONG
                    ).show()
                    findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)
                    showLoadingScreen(false)
                }
                Status.ERROR -> {
                    // Handle error
                    showLoadingScreen(false)
                    Toast.makeText(requireContext(), resource.message, Toast.LENGTH_LONG).show()
                }
                Status.LOADING -> {
                    // Show loading progress
                    showLoadingScreen(true)
                }
                else -> {}
            }
        }

    }

    // Implement Google Sign-In button click listener here
    private fun signInWithGoogle() {
        viewModel.signInWithGoogle(requireActivity())
    }

    // Implement onActivityResult method to handle Google Sign-In result
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            viewModel.handleGoogleSignInResult(data, onSuccess = {
                // Handle successful sign-in
                Toast.makeText(requireContext(), "Login successfully", Toast.LENGTH_LONG).show()
                findNavController().navigate(R.id.action_registrationFragment_to_bottomNavGraph)
            }, onFailure = {
                // Handle sign-in failure
                Toast.makeText(requireContext(), "Login Failure", Toast.LENGTH_LONG).show()
            })
        }
    }

    private fun registerUser() {
        with(binding) {
            val firstname = firstName.text.toString()
            val lastName = lastName.text.toString()
            val email = email.text.toString()
            val phone = phoneNum.text.toString()
            val password = password.text.toString()
            viewModel.createUser(
                firstName = firstname,
                lastName,
                email = email,
                phone = phone,
                password = password
            )
        }
    }
}