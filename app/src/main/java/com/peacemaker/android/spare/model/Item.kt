package com.peacemaker.android.spare.model


import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("amount")
    val amount: Double,
    @SerializedName("category")
    val category: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: String
)