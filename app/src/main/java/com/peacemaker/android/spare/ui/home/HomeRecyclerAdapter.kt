package com.peacemaker.android.spare.ui.home

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.peacemaker.android.spare.R
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

class MyAdapter(private val itemList: List<MyItem>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val HEADER_VIEW_TYPE = 0
    private val ITEM_VIEW_TYPE = 1
    private val headerDateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
    private val itemDateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    @RequiresApi(Build.VERSION_CODES.O)
    private val groupedItems = groupItemsByDate(itemList)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            HEADER_VIEW_TYPE -> HeaderViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.home_list_header_layout, parent, false))
            ITEM_VIEW_TYPE -> ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.home_transaction_list, parent, false))
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder -> {
                val header = groupedItems[position]
                holder.headerTextView.text = headerDateFormat.format(header)
            }
            is ItemViewHolder -> {
                val item = groupedItems[position]
              //  holder.nameTextView.text = "${item.name} at ${itemDateFormat.format(item.dateTime)}"
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getItemCount(): Int {
        return groupedItems.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getItemViewType(position: Int): Int {
        return if (groupedItems[position] is LocalDate) HEADER_VIEW_TYPE else ITEM_VIEW_TYPE
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun groupItemsByDate(itemList: List<MyItem>): List<Any> {
        val groupedItems = mutableListOf<Any>()
        var lastDate: LocalDate? = null
        for (item in itemList) {
            val currentDate = item.dateTime.toLocalDate()
            if (lastDate == null || lastDate != currentDate) {
                groupedItems.add(currentDate)
                lastDate = currentDate
            }
            groupedItems.add(item)
        }
        return groupedItems
    }

    class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val headerTextView: TextView = itemView.findViewById(R.id.dateTv)
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.transactionTv)
    }
}

data class MyItem(val id: Int, val name: String, val dateTime: LocalDateTime)
