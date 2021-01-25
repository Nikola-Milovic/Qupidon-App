package com.nikolam.feature_profile.domain.models

data class ProfileDomainModel(
    val id : String,
val email: String,
val name: String,
val bio: String,
val gender: String,
val profilePictureUrl: String
)