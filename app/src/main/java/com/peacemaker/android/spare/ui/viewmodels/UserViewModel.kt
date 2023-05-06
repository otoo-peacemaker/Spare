package com.peacemaker.android.spare.ui.viewmodels

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.google.firebase.storage.FirebaseStorage
import com.peacemaker.android.spare.data.User
import com.peacemaker.android.spare.data.repository.AuthRepository
import com.peacemaker.android.spare.data.repository.FireStoreRepository

class UserViewModel(private val authRepository: AuthRepository, private val fireStoreRepository: FireStoreRepository):ViewModel() {
    private val storageRef = FirebaseStorage.getInstance().reference
    // LiveData to hold the user's profile picture Uri
    private val _profilePictureUri = MutableLiveData<Uri>()
    val profilePictureUri: LiveData<Uri>
        get() = _profilePictureUri

    // LiveData to hold the user's name
    private val _name = MutableLiveData<String?>()
    val name: MutableLiveData<String?>
        get() = _name

    init {
        getUserProfile()
    }
    // Function to retrieve the user's profile information
    private fun getUserProfile() {
        authRepository.getCurrentUser()?.let { user ->
            fireStoreRepository.getUserProfileByUserId(user.uid)
                .addOnSuccessListener { documentSnapshot ->
                    val spareUserProfile = documentSnapshot.toObject(User::class.java)
                    spareUserProfile?.let {
                        _profilePictureUri.postValue(Uri.parse(spareUserProfile.profileImage))
                        _name.postValue(spareUserProfile.name)
                    }
                }.addOnFailureListener {
                    // Failed to retrieve user profile
                }
        }
    }

    fun updateProfileImage(uid: String, profileImage: Uri, onResult: (Boolean) -> Unit) {
        val profileImageRef = storageRef.child("profile_images/$uid.jpg")
        // Upload the image to Firebase Storage
        profileImageRef.putFile(profileImage)
            .addOnSuccessListener {
                // Get the download URL for the image
                profileImageRef.downloadUrl.addOnSuccessListener { uri ->
                    // Update the user's profile image URL in Firestore
                    fireStoreRepository.getUsersCollection().document(uid).update("profileImageUrl", uri.toString())
                        .addOnSuccessListener {
                            onResult(true)
                        }
                        .addOnFailureListener {
                            onResult(false)
                        }
                }
            }
            .addOnFailureListener {
                onResult(false)
            }
    }

    fun updateProfile(
        uid: String,
        profileImage: Uri?,
        displayName: String?,
        onResult: (Boolean) -> Unit) {
        val profileUpdates = mutableMapOf<String, Any>()
        // If a new profile image was provided, upload it to Firebase Storage and update the user's profileImageUrl field
        if (profileImage != null) {
            val profileImageRef = storageRef.child("profilePictureUrl/$uid.jpg")
            // Upload the image to Firebase Storage
            profileImageRef.putFile(profileImage)
                .addOnSuccessListener {
                    // Get the download URL for the image
                    profileImageRef.downloadUrl.addOnSuccessListener { uri ->
                        // Add the profileImageUrl field to the profileUpdates map
                        profileUpdates["profilePictureUrl"] = uri.toString()
                        // Update the user's display name if provided
                        if (!displayName.isNullOrEmpty()) {
                            profileUpdates["displayName"] = displayName
                        }
                        // Update the user's profile information in Fire store
                        fireStoreRepository.getUsersCollection().document(uid).update(profileUpdates)
                            .addOnSuccessListener {
                                onResult(true)
                            }
                            .addOnFailureListener {
                                onResult(false)
                            }
                    } }
                .addOnFailureListener {
                    onResult(false)
                }
        } else {
            // If no new profile image was provided, only update the user's display name if provided
            if (!displayName.isNullOrEmpty()) {
                profileUpdates["displayName"] = displayName
            }
            // Update the user's profile information in Fire store
            fireStoreRepository.getUsersCollection().document(uid).update(profileUpdates)
                .addOnSuccessListener {
                    onResult(true)
                }
                .addOnFailureListener {
                    onResult(false)
                }
        }
    }


    fun getAllUsers(): LiveData<List<User>> {
        val usersLiveData = MutableLiveData<List<User>>()
        fireStoreRepository.getUsersCollection()
            .get()
            .addOnSuccessListener { querySnapshot ->
                val spareUsers = ArrayList<User>()
                for (documentSnapshot in querySnapshot.documents) {
                    val spareUser = documentSnapshot.toObject(User::class.java)
                    spareUser?.let { spareUsers.add(it) }
                }
                usersLiveData.value = spareUsers
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Error getting all users.", exception)
            }
        return usersLiveData
    }


    class UserViewModelFactory(private val authRepository: AuthRepository, private val fireStoreRepository: FireStoreRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
            if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
                return UserViewModel(authRepository, fireStoreRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    companion object {
        private const val TAG = "UsersViewModel"
    }

}

