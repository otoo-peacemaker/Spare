package com.peacemaker.android.spare.ui.main.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.peacemaker.android.spare.R
import com.peacemaker.android.spare.data.repository.AuthRepository
import com.peacemaker.android.spare.data.repository.FireStoreRepository
import com.peacemaker.android.spare.databinding.FragmentProfileBinding
import com.peacemaker.android.spare.ui.auths.AuthViewModel
import com.peacemaker.android.spare.ui.util.BaseFragment
import com.peacemaker.android.spare.ui.viewmodels.UserViewModel

class ProfileFragment : BaseFragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!


    private val authRepository by lazy { AuthRepository() }
    private val fireStoreRepository by lazy { FireStoreRepository() }
    private lateinit var viewModel :UserViewModel
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, UserViewModel.UserViewModelFactory(authRepository, fireStoreRepository))[UserViewModel::class.java]

        observeData()
        setupUIElement()
    }

    private fun observeData(){
        // Observe the LiveData objects
        viewModel.profilePictureUri.observe(viewLifecycleOwner) { uri ->
            // Update the profile picture ImageView
            Glide.with(this)
                .load(uri)
                .apply(RequestOptions()
                    .placeholder(R.drawable.ico_acc_info)
                    .error(R.drawable.ico_acc_info))
                .into(binding.profileImg)
        }
        viewModel.name.observe(viewLifecycleOwner) { name ->
           binding.username.text = name
        }
    }
    private fun setupUIElement(){
        binding.apply {
            accInfo.apply {
                profileImg.setImageDrawable(getDrawable(R.drawable.ico_acc_info))
                username.text = getString(R.string.acc_info)
            }
            accVerification.apply {
                profileImg.setImageDrawable(getDrawable(R.drawable.ico_acc_verification))
                username.text = getString(R.string.acc_verification)
            }
            invitation.apply {
                profileImg.setImageDrawable(getDrawable(R.drawable.ico_invite))
                username.text = getString(R.string.invite)
            }
            bankCards.apply {
                profileImg.setImageDrawable(getDrawable(R.drawable.ico_bank))
                username.text =getString(R.string.bank)
            }
            privacy.apply {
                profileImg.setImageDrawable(getDrawable(R.drawable.ico_privacy))
                username.text = getString(R.string.privacy)
            }
            helpAndSupport.apply {
                profileImg.setImageDrawable(getDrawable(R.drawable.ico_help))
                username.text = getString(R.string.help)
            }
            aboutUs.apply {
                profileImg.setImageDrawable(getDrawable(R.drawable.ico_about_us))
                username.text = getString(R.string.about_us)
            }
            signOut.apply {
                profileImg.setImageDrawable(getDrawable(R.drawable.ico_log_out))
                username.text = getString(R.string.logout)
                signOut.root.setOnClickListener {
                    authViewModel.signOut{
                        findNavController().navigate(R.id.action_global_authentication_nav_graph)
                    }
                }
            }
        }
    }

}