package com.peacemaker.android.spare.model


import com.google.gson.annotations.SerializedName

data class Transaction(
    @SerializedName("items")
    val items: List<Item>?,
    @SerializedName("month")
    val month: String
)