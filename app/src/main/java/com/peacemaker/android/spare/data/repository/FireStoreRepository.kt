package com.peacemaker.android.spare.data.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.peacemaker.android.spare.model.UserProfile

class FireStoreRepository {
    private val fireStore = FirebaseFirestore.getInstance()

    fun getUserProfileByUserId(userId: String): Task<DocumentSnapshot> {
        return fireStore.collection("users").document(userId).get()
    }

    fun getUsersCollection(): CollectionReference {
        return fireStore.collection("users")
    }

    fun saveUserProfileWithGoogle(user: UserProfile): Task<Void> {
        val db = FirebaseFirestore.getInstance()
        val uid = FirebaseAuth.getInstance().currentUser!!.uid
        return db
            .collection("users")
            .document(uid)
            .set(user, SetOptions.merge())
    }

    // Implement other functions as needed for your app
}