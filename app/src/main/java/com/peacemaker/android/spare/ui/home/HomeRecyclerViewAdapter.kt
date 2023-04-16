package com.peacemaker.android.spare.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.peacemaker.android.spare.R
import com.peacemaker.android.spare.databinding.HomeListHeaderLayoutBinding
import com.peacemaker.android.spare.databinding.HomeTransactionListBinding

class HomeRecyclerViewAdapter : RecyclerView.Adapter<HomeRecyclerViewHolder>() {

    var items = listOf<HomeRecyclerViewItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeRecyclerViewHolder {
        return when(viewType){
            R.layout.home_list_header_layout -> HomeRecyclerViewHolder.TitleViewHolder(
                HomeListHeaderLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            R.layout.home_transaction_list -> HomeRecyclerViewHolder.TransactionViewHolder(
                HomeTransactionListBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> throw IllegalArgumentException("Invalid ViewType Provided")
        }
    }

    override fun onBindViewHolder(holder: HomeRecyclerViewHolder, position: Int) {
        when(holder){
            is HomeRecyclerViewHolder.TransactionViewHolder -> holder.bind(items[position] as HomeRecyclerViewItem.Transaction)
            is HomeRecyclerViewHolder.TitleViewHolder -> holder.bind(items[position] as HomeRecyclerViewItem.Title)
        }
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int): Int {
        return when(items[position]){
            is HomeRecyclerViewItem.Transaction -> R.layout.home_transaction_list
            is HomeRecyclerViewItem.Title -> R.layout.home_list_header_layout
        }
    }
}