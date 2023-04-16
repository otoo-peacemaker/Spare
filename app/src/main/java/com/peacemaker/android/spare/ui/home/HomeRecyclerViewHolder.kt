package com.peacemaker.android.spare.ui.home

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.peacemaker.android.spare.databinding.HomeListHeaderLayoutBinding
import com.peacemaker.android.spare.databinding.HomeTransactionListBinding

sealed class HomeRecyclerViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {

    class TitleViewHolder(private val binding: HomeListHeaderLayoutBinding) : HomeRecyclerViewHolder(binding){
        fun bind(title: HomeRecyclerViewItem.Title){
            binding.dateTv.text = title.title
        }
    }

    class TransactionViewHolder(private val binding: HomeTransactionListBinding) : HomeRecyclerViewHolder(binding){
        fun bind(director: HomeRecyclerViewItem.Transaction){
            binding.transactionTv.text = director.type
            binding.amt.text = director.amt
            binding.date.text=director.date
        }
    }
}