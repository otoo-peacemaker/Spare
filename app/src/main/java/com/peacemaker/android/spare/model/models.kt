package com.peacemaker.android.spare.model

import com.peacemaker.android.spare.data.TransactionStatus
import com.peacemaker.android.spare.data.TransactionType
import java.util.*

// User model
data class User(
    val id: String,
    val name: String,
    val email: String,
    val phone: String,
    val profileImage: String // URL to user's profile image
)

// Transaction model
data class Transactions(
    val id: String,
    val sender: User,
    val receiver: User,
    val amount: Double,
    val currency: String,
    val date: Date,
    val type: TransactionType, // enum: SEND, REQUEST, PAYMENT
    val description: String,
    val status: TransactionStatus, // enum: PENDING, COMPLETED, FAILED
    val method: String
)

// Bank Account model
data class BankAccount(
    val id: String,
    val owner: User,
    val balance: Double,
    val currency: String,
    val accountNumber: String,
    val routingNumber: String,
    val bankName: String
)

// Credit Card model
data class CreditCard(
    val id: String,
    val owner: User,
    val cardNumber: String,
    val cardholderName: String,
    val expirationDate: String,
    val cvv: String
)

// Payment Method model (can be either a Bank Account or a Credit Card)
sealed class PaymentMethod {
    data class BankAccountMethod(val bankAccount: BankAccount) : PaymentMethod()
    data class CreditCardMethod(val  creditCard: CreditCard) : PaymentMethod()
}
