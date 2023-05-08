package com.peacemaker.android.spare.data

import com.peacemaker.android.spare.model.Item
import com.peacemaker.android.spare.model.Transaction


val dumyData = listOf(
    Transaction(
        listOf(
            Item(20384.00, "Transfer", "April 12 2023", "Sent to Peacemaker", "1"),
            Item(30384.00, "Deposit", "April 30 2023", "Received from Kaska", "2"),
            Item(10384.00, "Transfer", "April 1 2023", "Sent to Peacemaker", "3")
        ), "April 2023"
    ),
    Transaction(
        listOf(
            Item(20384.00, "Deposit", "march 1 2023", "Received from Kaska", "1"),
            Item(50384.00, "Deposit", "march 5 2023", "Received from Kaska", "2"),
            Item(60384.00, "Transfer", "march 5 2023", "Sent to Peacemaker", "3"),
            Item(20384.00, "Transfer", "march 5 2023", "Sent to Peacemaker", "1"),
            Item(10384.00, "Deposit", "march 5 2023", "Sent to Peacemaker", "1")
        ), "March 2023"
    ),
    Transaction(listOf(Item(20384.00, "Deposit", "", "", "1")), "Jan 2023"),
    Transaction(listOf(Item(20384.00, "Deposit", "", "", "1")), "Dec 2022"),
    Transaction(listOf(Item(20384.00, "Deposit", "", "", "1")), "Oct 2022"),
)

val items = listOf(
    Transaction(null,"April 2023"),
    Item(20384.00, "Transfer", "April 12 2023", "Sent to Peacemaker", "1"),
    Item(30384.00, "Deposit", "April 30 2023", "Received from Kaska", "2"),
    Item(10384.00, "Transfer", "April 1 2023", "Sent to Peacemaker", "3"),


    Transaction(null,"March 2023"),
    Item(20384.00, "Deposit", "march 1 2023", "Received from Kaska", "1"),
    Item(50384.00, "Deposit", "march 5 2023", "Received from Kaska", "2"),
    Item(60384.00, "Transfer", "march 5 2023", "Sent to Peacemaker", "3"),
    Item(20384.00, "Transfer", "march 5 2023", "Sent to Peacemaker", "1"),
    Item(10384.00, "Deposit", "march 5 2023", "Sent to Peacemaker", "1"),

    Transaction(null,"Feb 2023"),
    Item(1000.00,"Deposit","$2000","may 10",""),
    Item(2000.00,"Send","$66000","may 10","2"),
    Item(1000.00,"Deposit","$2000","may 10",""),
    Item(2000.00,"Send","$66000","may 10","2"),

    Transaction(null,"Jan 2023"),
    Item(1000.00,"Deposit","$2000","may 10",""),
    Item(2000.00,"Send","$66000","may 10","2"),
    Item(1000.00,"Deposit","$2000","may 10",""),
    Item(2000.00,"Send","$66000","may 10","2"),

    )