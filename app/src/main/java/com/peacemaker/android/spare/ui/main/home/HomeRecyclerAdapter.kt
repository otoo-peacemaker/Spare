package com.peacemaker.android.spare.ui.main.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.peacemaker.android.spare.databinding.HomeListHeaderLayoutBinding
import com.peacemaker.android.spare.databinding.HomeTransactionListBinding
import com.peacemaker.android.spare.model.Item
import com.peacemaker.android.spare.model.Transaction

class MyRecAdapter(private val items: List<Any>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_ONE -> {
                val binding = HomeListHeaderLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ItemOneViewHolder(binding)
            }
            VIEW_TYPE_TWO -> {
                val binding = HomeTransactionListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ItemTwoViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            VIEW_TYPE_ONE -> {
                val item = items[position] as Transaction
                (holder as ItemOneViewHolder).bind(item)
            }
            VIEW_TYPE_TWO -> {
                val item = items[position] as Item
                (holder as ItemTwoViewHolder).bind(item)
            }
        }
    }
    override fun getItemCount(): Int {
        return items.size
    }
    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is Transaction -> VIEW_TYPE_ONE
            is Item -> VIEW_TYPE_TWO
            else -> throw IllegalArgumentException("Invalid item type")
        }
    }
    inner class ItemOneViewHolder(private val binding: HomeListHeaderLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Transaction) {
            binding.dateTv.text = item.month
        }
    }

    inner class ItemTwoViewHolder(private val binding: HomeTransactionListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item) {
            binding.transactionTv.text = item.description
            binding.amt.text = item.amount.toString()
            binding.date.text = item.date
        }
    }

    companion object{
        private const val VIEW_TYPE_ONE = 0
        private const val VIEW_TYPE_TWO = 1
    }
}
