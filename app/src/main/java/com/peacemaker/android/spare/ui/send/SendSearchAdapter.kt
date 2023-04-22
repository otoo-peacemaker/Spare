package com.peacemaker.android.spare.ui.send

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.peacemaker.android.spare.R
import com.peacemaker.android.spare.databinding.SendRecipientListBinding

class SendSearchAdapter(private var items: List<String>) :
    RecyclerView.Adapter<SendSearchAdapter.ViewHolder>() {

    private var filteredItems: List<String> = items

    inner class ViewHolder(private val binding: SendRecipientListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            // Bind the item to the UI elements in the ViewHolder
            binding.username.text = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Inflate the layout for the ViewHolder
        val view =SendRecipientListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = filteredItems[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return filteredItems.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filter(query: String) {
        filteredItems = items.filter { it.contains(query, ignoreCase = true) }
        notifyDataSetChanged()
    }
}
