package com.peacemaker.android.spare.ui.adapters

import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.peacemaker.android.spare.databinding.RecentRecipientProfileBinding
import com.peacemaker.android.spare.ui.util.GenericRecyclerAdapter

class RecentRecipientProfileAdapter(
    items: List<String>,
    onClickListener: ((String) -> Unit)? = null) : GenericRecyclerAdapter<String, RecentRecipientProfileBinding>(items = items,
    bindingInflater = { inflater, parent, attachToParent ->
        RecentRecipientProfileBinding.inflate(inflater, parent, attachToParent)
    },
    onBind = { binding, item ->
        Glide.with(binding.root)
            .load(item)
            .transform(CenterCrop())
            .into(binding.profileImg)
        // Bind other user data to the view
    },
    onClickListener = onClickListener) {
    override fun filterCondition(item: String, query: String): Boolean {
        return item.contains(query)
    }
}