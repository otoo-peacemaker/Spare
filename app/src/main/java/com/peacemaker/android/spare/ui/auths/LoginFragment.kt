package com.peacemaker.android.spare.ui.auths

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.peacemaker.android.spare.MainActivity
import com.peacemaker.android.spare.R
import com.peacemaker.android.spare.databinding.FragmentLandingPageBinding
import com.peacemaker.android.spare.databinding.FragmentLoginBinding
import com.peacemaker.android.spare.ui.util.BaseFragment
import com.peacemaker.android.spare.ui.util.Status
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class LoginFragment : BaseFragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var viewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[UserViewModel::class.java]
        setOnClickListeners()

        viewModel.signInLiveData.observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    // User signed in successfully
                    showSnackBar(requireView(),"${resource.data?.email} signed in successfully")
                    findNavController().navigate(R.id.action_global_bottom_nav_graph)
                    runBlocking {
                        delay(2000)
                        showLoadingScreen(false)
                    }

                }
                Status.ERROR -> {
                    // Handle error
                    showLoadingScreen(false)
                    showSnackBar(requireView(), resource.message.toString())
                }
                Status.LOADING -> {
                    // Show loading progress
                  showLoadingScreen(true)
                }
            }
        }
    }

    private fun setOnClickListeners(){
        binding.login.setOnClickListener {
            signIn()
        }
        binding.signUp.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
        }
    }

    private fun signIn(){
        with(binding){
            val email = email.text.toString()
            val password = password.text.toString()
            viewModel.signIn(email,password)
        }
    }

}