package com.peacemaker.android.spare.data.dao

import androidx.room.*
import com.peacemaker.android.spare.data.*
import com.peacemaker.android.spare.data.Transaction

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM users WHERE id = :id")
    suspend fun getUserById(id: Int): User?

    @Query("SELECT * FROM users")
    suspend fun getAllUsers(): List<User>

    @Delete
    suspend fun deleteUser(user: User)
}


@Dao
interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: Transaction)

    @Query("SELECT * FROM transactions WHERE id = :id")
    suspend fun getTransactionById(id: Int): Transaction?

    @Query("SELECT * FROM transactions")
    suspend fun getAllTransactions(): List<Transaction>

    @Delete
    suspend fun deleteTransaction(transaction: Transaction)
}


@Dao
interface BankAccountDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBankAccount(bankAccount: BankAccount)

    @Query("SELECT * FROM bank_accounts WHERE id=:id")
    suspend fun getBankAccountById(id: Int): BankAccount?

    @Query("SELECT * FROM bank_accounts")
    suspend fun getAllBankAccounts(): List<BankAccount>

    @Delete
    suspend fun deleteBankAccount(bankAccount: BankAccount)
}


@Dao
interface WalletDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCreditCard(creditCard: Wallets)

    @Query("SELECT * FROM wallets WHERE id=:id")
    suspend fun getCreditCardById(id: Int): Wallets?

    @Query("SELECT * FROM wallets")
    suspend fun getAllCreditCards(): List<Wallets>

    @Delete
    suspend fun deleteCreditCard(creditCard: Wallets)
}

