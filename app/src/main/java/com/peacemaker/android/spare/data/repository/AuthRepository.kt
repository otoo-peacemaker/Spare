package com.peacemaker.android.spare.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AuthRepository {

    private val auth = FirebaseAuth.getInstance()

    companion object{

    }
    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    // Implement other functions as needed for your app
}
