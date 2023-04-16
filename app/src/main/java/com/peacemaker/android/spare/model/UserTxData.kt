package com.peacemaker.android.spare.model


import com.google.gson.annotations.SerializedName

data class UserTxData(
    @SerializedName("transactions")
    val transactions: List<Transaction>
)