package com.peacemaker.android.spare.data.crud

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.peacemaker.android.spare.data.User
import kotlinx.coroutines.tasks.await

fun findUserById(users: List<User>, id: String): User? {
    return users.find { it.id == id }
}

fun findUserByName(users: List<User>, name: String): User? {
    return users.find { it.name.equals(name, ignoreCase = true) }
}

suspend fun <T> getCollection(collectionName: String, clazz: Class<T>): List<T> {
    val fireStore = FirebaseFirestore.getInstance()
    val collection = fireStore.collection(collectionName).get().await()
    return collection.toObjects(clazz)
}


fun <T> updateDocument(collectionRef: CollectionReference, documentId: String, fieldName: String, fieldValue: T, onComplete: () -> Unit) {
    val documentRef = collectionRef.document(documentId)
    documentRef.update(fieldName, fieldValue)
        .addOnSuccessListener {
            onComplete()
            Log.d(TAG, "::::::::::::::::Document $documentId updated successfully")
        }
        .addOnFailureListener { e ->
            Log.w(TAG, "Error updating document", e)
        }
}

fun getAccountBalanceByUID(uid: String, callback: (Double?) -> Unit) {
    val firestore = Firebase.firestore
    val docRef = firestore.collection("users").document(uid)
    docRef.get().addOnSuccessListener { document ->
        if (document != null) {
            val user = document.toObject(User::class.java)
            if (user != null) {
                val accountBalance = user.accBalance?:0.0
                callback.invoke(accountBalance)
            } else {
                Log.d(TAG, "User is null")
            }
        } else {
            Log.d(TAG, "Document does not exist")
        }
    }.addOnFailureListener { exception ->
        Log.d(TAG, "Error getting user account balance: ", exception)
    }
}



