package com.peacemaker.android.spare.ui.auths

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.peacemaker.android.spare.R
import com.peacemaker.android.spare.databinding.FragmentLoginBinding
import com.peacemaker.android.spare.databinding.FragmentRegistrationBinding
import com.peacemaker.android.spare.ui.util.BaseFragment
import com.peacemaker.android.spare.ui.util.Status
import com.peacemaker.android.spare.ui.util.changeTextColor

class RegistrationFragment : BaseFragment() {
    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!

    private lateinit var googleSignInClient: GoogleSignInClient
    companion object {
        fun newInstance() = RegistrationFragment()
    }
    private lateinit var viewModel: UserViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[UserViewModel::class.java]
        binding.alreadyHaveAcc.changeTextColor("Sign in", color = R.color.md_theme_light_primary)
        binding.register.setOnClickListener { registerUser() }

        viewModel.createUserLiveData.observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    // User account created and information saved successfully
                  Toast.makeText(requireContext(),"User account created and information saved successfully", Toast.LENGTH_LONG).show()
                    findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)
                }
                Status.ERROR -> {
                    // Handle error
                    Toast.makeText(requireContext(),resource.message, Toast.LENGTH_LONG).show()
                }
                Status.LOADING -> {
                    // Show loading progress
                    print(":::::::::::::::::::::::::::::::::::::LOADING")
                }
            }
        }

    }

    private fun registerUser(){
        with(binding){
            val firstname = firstName.text.toString()
            val lastName = lastName.text.toString()
            val email = email.text.toString()
            val phone = phoneNum.text.toString()
            val password = password.text.toString()
            viewModel.createUser(firstName = firstname,lastName, email = email, phone = phone, password = password)
        }
    }
}