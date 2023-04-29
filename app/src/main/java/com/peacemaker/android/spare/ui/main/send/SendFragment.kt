package com.peacemaker.android.spare.ui.main.send

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import com.peacemaker.android.spare.databinding.FragmentSendBinding
import com.peacemaker.android.spare.model.RecentProfile
import com.peacemaker.android.spare.ui.adapters.RecipientListAdapter
import com.peacemaker.android.spare.ui.util.BaseFragment

class SendFragment : BaseFragment() {
    private var _binding: FragmentSendBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapterList: SendSearchAdapter
    private lateinit var viewModel: SendViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        _binding = FragmentSendBinding.inflate(inflater, container, false)
        // Initialize the adapter with a list of items to display
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[SendViewModel::class.java]
        val items = arrayListOf(RecentProfile())
        val imgUrl = arrayListOf(String())
        val userProfileList = readJsonData<RecentProfile>(requireContext(), "recent_users.json")
        userProfileList.forEach { profile ->
            items.add(profile)
            imgUrl.add(profile.profile_image_url)
        }


        val genericAdapter = RecipientListAdapter(imgUrl) { user ->
            // Handle click event on user item
        }

        binding.recyclerView2.apply {
            layoutManager = LinearLayoutManager(requireContext(), HORIZONTAL, false)
            adapter = genericAdapter
        }

        adapterList = SendSearchAdapter(items)

        /*binding.recyclerView2.apply {
            layoutManager = LinearLayoutManager(requireContext(), HORIZONTAL, false)
            adapter = adapterList
        }*/
        binding.recyclerViewList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = adapterList
        }
        performSearch()
    }

    private fun performSearch(){
        binding.searchView.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //TODO("Not yet implemented")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                adapterList.filter(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
               // TODO("Not yet implemented")
            }
        })
    }

}