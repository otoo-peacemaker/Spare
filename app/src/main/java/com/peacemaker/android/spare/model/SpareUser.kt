package com.peacemaker.android.spare.model

data class SpareUser (
    val firstName: String?=null,
    val middleName:String?=null,
    val lastName: String?=null,
    val email: String?=null,
    val phone: String?=null,
    val password: String?=null,
    val profilePictureUrl: String?="",
){
    //Firebase requires a no-argument constructor to deserialize objects.
  //  constructor() : this("", "", "","", "", "","")
}

data class UserProfile(
    val fullName: String = "",
    val profilePictureUrl: String = "",
    val email: String,
)

data class RecentProfile(
    val first_name: String ="",
    val last_name:String ="",
    val profile_image_url: String ="",
    val recentRecipientProfile: RecentRecipientProfile ?=null
)

data class RecentRecipientProfile(
    val profile_image_url: String ="",
)