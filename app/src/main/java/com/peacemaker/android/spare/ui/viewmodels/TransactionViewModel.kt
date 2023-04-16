package com.peacemaker.android.spare.ui.viewmodels

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.peacemaker.android.spare.model.UserTxData

class TransactionViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    fun saveTransactionDataToFireStore(transaction: UserTxData) {
        val uid = auth.currentUser?.uid
        if (uid != null) {
            db.collection("users").document(uid).collection("transactions")
                .add(transaction)
                .addOnSuccessListener { documentReference ->
                    Log.d(TAG, "Transaction data saved successfully with ID: ${documentReference.id}")
                }
                .addOnFailureListener {
                    Log.e(TAG, "Error saving transaction data: ${it.message}")
                }
        }
    }

    fun getTransactionsLiveData(): LiveData<List<UserTxData>> {
        val uid = auth.currentUser?.uid
        if (uid != null) {
            val transactionList = mutableListOf<UserTxData>()
            val transactionsLiveData = MutableLiveData<List<UserTxData>>()
            db.collection("users").document(uid).collection("transactions")
                .addSnapshotListener { snapshot, e ->
                    if (e != null) {
                        Log.e(TAG, "Error getting transactions: ${e.message}")
                        return@addSnapshotListener
                    }

                    if (snapshot != null && !snapshot.isEmpty) {
                        for (document in snapshot.documents) {
                            val transaction = document.toObject(UserTxData::class.java)
                            transaction?.let {
                                transactionList.add(it)
                            }
                        }
                        transactionsLiveData.value = transactionList
                    }
                }
            return transactionsLiveData
        }

        return MutableLiveData(emptyList())
    }
}
