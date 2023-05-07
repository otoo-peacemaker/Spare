package com.peacemaker.android.spare.data

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey val id: String="",
    val name: String?=null,
    val email: String?=null,
    val password:String?=null,
    @ColumnInfo(name = "phone_number") val phoneNumber: String?=null,
    @ColumnInfo(name = "profile_image") val profileImage: String?=null,
    @ColumnInfo(name = "account_balance") val accBalance: Double?=0.0
){
      //constructor() : this("", "", "","", "", "","")
}

@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey val id: String="",
    @Embedded(prefix = "sender_") val sender: User?=null,
    @Embedded(prefix = "receiver_") val receiver: User?=null,
    val amount: Double?=0.0,
    val currency: String?=null,
    val date: String?=null,
    val description: String?=null,
    @Embedded(prefix = "method") val method: PaymentMethod?=null,
    val type: TransactionType?=null,
    val status: TransactionStatus?=null,
)


@Entity(tableName = "bank_accounts")
data class BankAccount(
    @PrimaryKey val id: String,
    @Embedded(prefix = "owner_") val owner: User?,
    @ColumnInfo(name = "account_number") val accountNumber: String?,
    @ColumnInfo(name = "balance") val balance: Double?=0.0,
    @ColumnInfo(name = "currency") val currency: String?
)

@Entity(tableName = "wallets")
data class Wallets(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "type") val type:String?,
    @Embedded(prefix = "owner_") val owner: User?,
    @ColumnInfo(name = "card_number") val cardNumber: String?,
    @ColumnInfo(name = "expiration_date") val expirationDate: String?,
    val cvv: String?,
    val balance:Double?=0.0,
    val currency: String?
)

@Entity(tableName = "payment_methods")
data class PaymentMethod(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "user_id") val userId: String?,
    @Embedded(prefix = "bank_account_id") val bankAccountId: BankAccount?,
    @Embedded(prefix = "credit_card_id") val creditCardId: Wallets?
)

// Transaction Type enum
enum class TransactionType {
    SEND,
    REQUEST,
    PAYMENT
}

// Transaction Status enum
enum class TransactionStatus {
    PENDING,
    COMPLETED,
    FAILED
}