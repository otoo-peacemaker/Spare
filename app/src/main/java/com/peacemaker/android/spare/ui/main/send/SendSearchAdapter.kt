package com.peacemaker.android.spare.ui.main.send

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.peacemaker.android.spare.data.User
import com.peacemaker.android.spare.databinding.SendRecipientListBinding

class SendSearchAdapter(private var items: ArrayList<User>, private val onClickListener:(User)->Unit) :
    RecyclerView.Adapter<SendSearchAdapter.ViewHolder>() {

    private var filteredItems: MutableList<User> = items

    inner class ViewHolder(private val binding: SendRecipientListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: User) {
            // Bind the item to the UI elements in the ViewHolder
            binding.username.text = item.name
            val imageUrl = item.profileImage
            Glide.with(binding.root)
                .load(imageUrl)
                .transform(CenterCrop())
                .into(binding.profileImg)
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
        holder.itemView.setOnClickListener {
            onClickListener.invoke(item)
        }
    }

    override fun getItemCount(): Int {
        return filteredItems.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filter(query: String) {
        filteredItems = items.filter { it.name?.contains(query, ignoreCase = true) == true }.toMutableList()
        notifyDataSetChanged()
    }
}
