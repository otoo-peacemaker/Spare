package com.peacemaker.android.spare.ui.profile

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.peacemaker.android.spare.R
import com.peacemaker.android.spare.databinding.FragmentLoginBinding
import com.peacemaker.android.spare.databinding.FragmentProfileBinding
import com.peacemaker.android.spare.ui.util.BaseFragment

class ProfileFragment : BaseFragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]
        setupUIElement()
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
            signOut.apply {
                profileImg.setImageDrawable(getDrawable(R.drawable.ico_log_out))
                username.text = getString(R.string.logout)
            }
        }
    }

}