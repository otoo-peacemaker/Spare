package com.peacemaker.android.spare.data.repository

import com.peacemaker.android.spare.data.BankAccount
import com.peacemaker.android.spare.data.Transaction
import com.peacemaker.android.spare.data.User
import com.peacemaker.android.spare.data.Wallets
import com.peacemaker.android.spare.data.dao.BankAccountDao
import com.peacemaker.android.spare.data.dao.TransactionDao
import com.peacemaker.android.spare.data.dao.UserDao
import com.peacemaker.android.spare.data.dao.WalletDao

class RoomRepository(
    private val userDao: UserDao,
    private val transactionDao: TransactionDao,
    private val bankAccountDao: BankAccountDao,
    private val creditCardDao: WalletDao) {

    suspend fun insertUser(user: User) {
        userDao.insertUser(user)
    }

    suspend fun getUserById(id: Int): User? {
        return userDao.getUserById(id)
    }

    suspend fun getAllUsers(): List<User> {
        return userDao.getAllUsers()
    }

    suspend fun deleteUser(user: User) {
        userDao.deleteUser(user)
    }

    suspend fun insertTransaction(transaction: Transaction) {
        transactionDao.insertTransaction(transaction)
    }

    suspend fun getTransactionById(id: Int): Transaction? {
        return transactionDao.getTransactionById(id)
    }

    suspend fun getAllTransactions(): List<Transaction> {
        return transactionDao.getAllTransactions()
    }

    suspend fun deleteTransaction(transaction: Transaction) {
        transactionDao.deleteTransaction(transaction)
    }

    suspend fun insertBankAccount(bankAccount: BankAccount) {
        bankAccountDao.insertBankAccount(bankAccount)
    }

    suspend fun getBankAccountById(id: Int): BankAccount? {
        return bankAccountDao.getBankAccountById(id)
    }

    suspend fun getAllBankAccounts(): List<BankAccount> {
        return bankAccountDao.getAllBankAccounts()
    }

    suspend fun deleteBankAccount(bankAccount: BankAccount) {
        bankAccountDao.deleteBankAccount(bankAccount)
    }

    suspend fun insertCreditCard(creditCard: Wallets) {
        creditCardDao.insertCreditCard(creditCard)
    }

    suspend fun getCreditCardById(id: Int): Wallets? {
        return creditCardDao.getCreditCardById(id)
    }

    suspend fun getAllCreditCards(): List<Wallets> {
        return creditCardDao.getAllCreditCards()
    }

    suspend fun deleteCreditCard(creditCard: Wallets) {
        creditCardDao.deleteCreditCard(creditCard)
    }

}
