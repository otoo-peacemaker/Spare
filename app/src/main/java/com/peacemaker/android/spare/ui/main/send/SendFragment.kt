package com.peacemaker.android.spare.ui.main.send

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import com.peacemaker.android.spare.R
import com.peacemaker.android.spare.data.Transaction
import com.peacemaker.android.spare.data.User
import com.peacemaker.android.spare.data.crud.getCollection
import com.peacemaker.android.spare.databinding.FragmentSendBinding
import com.peacemaker.android.spare.ui.adapters.RecentRecipientProfileAdapter
import com.peacemaker.android.spare.ui.util.BaseFragment
import kotlinx.coroutines.runBlocking

class SendFragment : BaseFragment() {
    private var _binding: FragmentSendBinding? = null
    private val binding get() = _binding!!
    private lateinit var recipientAdapterList: SendSearchAdapter
    private lateinit var viewModel: SendViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSendBinding.inflate(inflater, container, false)
        // Initialize the adapter with a list of items to display
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[SendViewModel::class.java]
        val items = arrayListOf(User())
        val imgUrl = arrayListOf(String())

        runBlocking {
            val transactions = getCollection("transactions",Transaction::class.java)
            transactions.forEach { transaction ->
                transaction.receiver?.profileImage?.let { imgUrl.add(it) }
                transaction.receiver?.let { items.add(it) }
            }
            log("SendFragment",":::::::::::::::::::::::::::$transactions")
        }

        val recentAdapter = RecentRecipientProfileAdapter(imgUrl) { user ->
            // Handle click event on user item
        }
        binding.recentRecView.apply {
            layoutManager = LinearLayoutManager(requireContext(), HORIZONTAL, false)
            adapter = recentAdapter
        }

        recipientAdapterList = SendSearchAdapter(items){
            findNavController().navigate(R.id.action_sendFragment_to_transferFragment)
        }
        binding.recyclerViewList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = recipientAdapterList
        }
        performSearch()
    }

    private fun performSearch() {
        binding.searchView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //TODO("Not yet implemented")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                recipientAdapterList.filter(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
                // TODO("Not yet implemented")
            }
        })
    }

}