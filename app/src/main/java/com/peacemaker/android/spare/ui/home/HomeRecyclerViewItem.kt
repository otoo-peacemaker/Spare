package com.peacemaker.android.spare.ui.home


sealed class HomeRecyclerViewItem {

    class Title(
        val id: Int,
        val title: String
    ) : HomeRecyclerViewItem()

    class Transaction(
        val id: Int,
        val type: String,
        val amt: String,
        val date: String
    ) : HomeRecyclerViewItem()

}