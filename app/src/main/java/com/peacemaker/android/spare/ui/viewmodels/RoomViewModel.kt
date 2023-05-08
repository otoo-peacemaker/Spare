package com.peacemaker.android.spare.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peacemaker.android.spare.data.BankAccount
import com.peacemaker.android.spare.data.Transaction
import com.peacemaker.android.spare.data.User
import com.peacemaker.android.spare.data.Wallets
import com.peacemaker.android.spare.data.repository.RoomRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RoomViewModel(private val repository: RoomRepository) : ViewModel() {

    private val _usersLiveData = MutableLiveData<List<User>>()
    val usersLiveData: LiveData<List<User>> = _usersLiveData

    private val _transactionsLiveData = MutableLiveData<List<Transaction>>()
    val transactionsLiveData: LiveData<List<Transaction>> = _transactionsLiveData

    private val _bankAccountsLiveData = MutableLiveData<List<BankAccount>>()
    val bankAccountsLiveData: LiveData<List<BankAccount>> = _bankAccountsLiveData

    private val _creditCardsLiveData = MutableLiveData<List<Wallets>>()
    val creditCardsLiveData: LiveData<List<Wallets>> = _creditCardsLiveData


    fun insertUser(user: User) {
        viewModelScope.launch {
            repository.insertUser(user)
        }
    }

    fun getUserById(id: Int): User? {
        var user: User? = null
        viewModelScope.launch {
            user = repository.getUserById(id)
        }
        return user
    }

    fun getAllUsers() {
        viewModelScope.launch {
            _usersLiveData.value = repository.getAllUsers()
        }
    }

    fun deleteUser(user: User) {
        viewModelScope.launch {
            repository.deleteUser(user)
        }
    }

    fun insertTransaction(transaction: Transaction) {
        viewModelScope.launch {
            repository.insertTransaction(transaction)
        }
    }

    fun getTransactionById(id: Int): Transaction? {
        var transaction: Transaction? = null
        viewModelScope.launch {
            transaction = repository.getTransactionById(id)
        }
        return transaction
    }

    fun getAllTransactions() {
        viewModelScope.launch {
            _transactionsLiveData.value = repository.getAllTransactions()
        }
    }

    fun deleteTransaction(transaction: Transaction) {
        viewModelScope.launch {
            repository.deleteTransaction(transaction)
        }
    }


    fun insertBankAccount(bankAccount: BankAccount) {
        viewModelScope.launch {
            repository.insertBankAccount(bankAccount)
        }
    }

    fun getBankAccountById(id: Int): BankAccount? {
        var bankAccount: BankAccount? = null
        viewModelScope.launch {
            bankAccount = repository.getBankAccountById(id)
        }
        return bankAccount
    }

    fun getAllBankAccounts() {
        viewModelScope.launch {
            _bankAccountsLiveData.value = repository.getAllBankAccounts()
        }
    }

    fun deleteBankAccount(bankAccount: BankAccount) {
        viewModelScope.launch {
            repository.deleteBankAccount(bankAccount)
        }
    }

    fun insert(creditCard: Wallets) = viewModelScope.launch {
        repository.insertCreditCard(creditCard)
    }

    val allCreditCards: Job = viewModelScope.launch {
        repository.getAllCreditCards()
    }


    fun delete(creditCard: Wallets) = viewModelScope.launch {
        repository.deleteCreditCard(creditCard)
    }


}
