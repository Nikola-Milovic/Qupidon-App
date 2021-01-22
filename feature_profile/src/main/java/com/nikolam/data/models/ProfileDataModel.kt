package com.nikolam.data.models

import com.nikolam.domain.models.ProfileDomainModel

data class ProfileDataModel(
    val __v: String,
    val user_id: String,
    val email: String,
    val name: String,
    val bio: String,
    val gender: String,
    val location_id: String,
    val profile_picture: String
)

fun ProfileDataModel.toDomainModel() : ProfileDomainModel{
    return ProfileDomainModel(email = email, name = name, bio = bio,
            //TODO remove the hardcode
            gender = gender, profilePictureUrl = "https://qupidon-images.s3.eu-central-1.amazonaws.com/${profile_picture}_medium.png"
    )
}