package com.peacemaker.android.spare.ui.auths

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.RuntimeExecutionException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.peacemaker.android.spare.model.User
import com.peacemaker.android.spare.ui.util.Constants.RC_SIGN_IN
import com.peacemaker.android.spare.ui.util.Resource

class UserViewModel : ViewModel() {

    private val auth = Firebase.auth

    private val _createUserLiveData = MutableLiveData<Resource<FirebaseUser>?>()
    val createUserLiveData: MutableLiveData<Resource<FirebaseUser>?> = _createUserLiveData

    private val _signInLiveData = MutableLiveData<Resource<AuthResult>>()
    val signInLiveData: LiveData<Resource<AuthResult>> = _signInLiveData

    fun createUser(firstName: String, lastName: String, email: String, phone: String, password: String) {
        _createUserLiveData.value = Resource.loading(null)
        try {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // User account created successfully
                        val firebaseUser = task.result?.user
                        val userId = firebaseUser?.uid
                        val db = Firebase.firestore

                        // Save additional user information in the Fire store database
                        val user = User(firstName, lastName, email, phone,password)
                        if (userId != null) {
                            db.collection("users").document(userId).set(user)
                                .addOnSuccessListener {
                                    _createUserLiveData.value = Resource.success(firebaseUser)
                                }
                                .addOnFailureListener { e ->
                                    _createUserLiveData.value =
                                        e.localizedMessage?.let { Resource.error(firebaseUser, it) }
                                }
                        }
                    } else {
                        // Handle error
                        _createUserLiveData.value =
                            task.exception?.localizedMessage?.let { Resource.error(null, it) }
                    }
                }
        }catch (e: FirebaseAuthInvalidUserException){
            _createUserLiveData.postValue(e.message?.let { Resource.error(null, it) })
        }catch (e:RuntimeExecutionException){
            _createUserLiveData.postValue(e.localizedMessage?.let { Resource.error(null, it) })
        }catch (e:IllegalArgumentException){
            _createUserLiveData.postValue(e.localizedMessage?.let { Resource.error(null, it) })
        }

    }
    fun signIn(email: String, password: String) {
        _signInLiveData.value = Resource.loading(null)
        try {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // User signed in successfully
                        _signInLiveData.value = Resource.success(task.result)
                    }
                }.addOnFailureListener {
                    _signInLiveData.value = it.message?.let { it1 -> Resource.error(null, it1) }
                }
        }
        catch (e: FirebaseAuthInvalidUserException){
            _signInLiveData.postValue(e.message?.let { Resource.error(null, it) })
        }catch (e:RuntimeExecutionException){
            _signInLiveData.postValue(e.localizedMessage?.let { Resource.error(null, it) })
        }catch (e:IllegalArgumentException){
            _signInLiveData.postValue(e.localizedMessage?.let { Resource.error(null, it) })
        }

    }

    fun signInWithGoogle(activity: Activity) {
        val googleSignInClient = GoogleSignIn.getClient(activity.applicationContext,
            GoogleSignInOptions.DEFAULT_SIGN_IN
        )
        val signInIntent = googleSignInClient.signInIntent
        activity.startActivityForResult(signInIntent, RC_SIGN_IN)
    }
    fun handleGoogleSignInResult(data: Intent?, onSuccess: () -> Unit, onFailure: () -> Unit) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            val account = task.getResult(ApiException::class.java)!!
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            auth.signInWithCredential(credential).addOnCompleteListener { taskListener ->
                if (taskListener.isSuccessful) {
                    onSuccess.invoke()
                } else {
                    onFailure.invoke()
                }
            }
        } catch (e: ApiException) {
            onFailure.invoke()
        }
    }

    fun signOut() {
        auth.signOut()
        _createUserLiveData.postValue(null)
    }
}
