package com.peacemaker.android.spare.ui.util

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.peacemaker.android.spare.databinding.RecentRecipientProfileBinding
import com.peacemaker.android.spare.databinding.SendRecipientListBinding
import com.peacemaker.android.spare.model.RecentProfile

object ViewType {
    /*const val HEADER = 1
    const val USER = 2*/
    const val RECENT_RECIPIENT = 1
    const val RECIPIENT_LIST = 2
}

@Suppress("UNCHECKED_CAST")
class GenericAdapter<T>(
    private val itemList: List<T>,
    private val onItemClickListener: ((T) -> Unit)? = null,
    private val filterPredicate: ((T, String) -> Boolean)? = null) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {
    private var filteredList = itemList
    private var filterText = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ViewType.RECIPIENT_LIST -> RecipientListViewHolder(
                SendRecipientListBinding.inflate(LayoutInflater.from(parent.context), parent, false),
                onItemClickListener
            )
            ViewType.RECENT_RECIPIENT -> RecentRecipientListViewHolder(
                RecentRecipientProfileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = filteredList[position]
        when (holder.itemViewType) {
            ViewType.RECIPIENT_LIST -> (holder as GenericAdapter<*>.RecipientListViewHolder).bind(item as RecentProfile)
            ViewType.RECENT_RECIPIENT -> (holder as GenericAdapter<*>.RecentRecipientListViewHolder).bind(item as String)
        }
    }

    override fun getItemCount() = filteredList.size

    override fun getItemViewType(position: Int): Int {
        return when (filteredList[position]) {
            is RecentProfile -> ViewType.RECIPIENT_LIST
            is String -> ViewType.RECENT_RECIPIENT
            else -> throw IllegalArgumentException("Invalid item type")
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                filterText = constraint.toString()
                filteredList = if (constraint.isNullOrEmpty() || filterPredicate == null) {
                    itemList
                } else {
                    itemList.filter {
                        filterPredicate.invoke(it, filterText)
                    }
                }
                filterResults.count = filteredList.size
                filterResults.values = filteredList
                return filterResults
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                notifyDataSetChanged()
            }
        }
    }

    inner class RecipientListViewHolder(
        private val binding: SendRecipientListBinding,
        private val onItemClickListener: ((T) -> Unit)?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(user: RecentProfile) {
            binding.username.text = user.first_name
            binding.root.setOnClickListener { onItemClickListener?.invoke(user as T) }
        }
    }

    inner class RecentRecipientListViewHolder(
        private val binding: RecentRecipientProfileBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(imgUrl: String) {
            Glide.with(binding.root)
                .load(imgUrl)
                .transform(CenterCrop())
                .into(binding.profileImg)
        }
    }
}

/*abstract class GenericRecyclerAdapter<T, VB : ViewBinding>(
    private val items: List<T>,
    private val layoutInflater: LayoutInflater,
    private val createBinding: (LayoutInflater) -> VB,
    private val bind: (VB, T) -> Unit) : RecyclerView.Adapter<GenericRecyclerAdapter<T, VB>.ViewHolder>() {

    private var filteredItems = items.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = createBinding(layoutInflater)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = filteredItems.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = filteredItems[position]
        holder.bind(item)
    }

    inner class ViewHolder(val binding: VB) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: T) {
            bind(binding, item)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filter(query: String) {
        filteredItems = if (query.isBlank()) {
            items.toMutableList()
        } else {
            items.filter { filterCondition(it, query) }.toMutableList()
        }
        notifyDataSetChanged()
    }

    abstract fun filterCondition(item: T, query: String): Boolean
}*/

abstract class GenericRecyclerAdapter<T, VB : ViewBinding>(
    private val items: List<T>,
    private val bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> VB,
    private val onBind: (VB, T) -> Unit,
    private val onClickListener: ((T) -> Unit)? = null
) : RecyclerView.Adapter<GenericRecyclerAdapter<T, VB>.ViewHolder>() {

    private var filteredItems = items.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = bindingInflater(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return filteredItems.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(filteredItems[position])
    }

    inner class ViewHolder(val binding: VB) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: T) {
            onBind(binding, item)
            onClickListener?.let { listener ->
                binding.root.setOnClickListener { listener(item) }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filter(query: String) {
        filteredItems.clear()
        if (query.isEmpty()) {
            filteredItems.addAll(items)
        } else {
            items.forEach { item ->
                if (filterCondition(item, query)) {
                    filteredItems.add(item)
                }
            }
        }
        notifyDataSetChanged()
    }

    abstract fun filterCondition(item: T, query: String): Boolean
}












