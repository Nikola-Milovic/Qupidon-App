package com.nikolam.feature_profile.data.models

import com.nikolam.data.db.models.ProfileDataModel
import com.nikolam.feature_profile.domain.models.ProfileDomainModel

data class ProfileServerDataModel(
    val __v: String,
    val user_id: String,
    val email: String,
    val name: String,
    val bio: String,
    val gender: String,
    val location_id: String,
    val profile_picture: String
)

fun ProfileServerDataModel.toDomainModel() : ProfileDomainModel{
    return ProfileDomainModel(id = user_id, email = email, name = name, bio = bio,
            //TODO remove the hardcode
            gender = gender, profilePictureUrl = "https://qupidon-images.s3.eu-central-1.amazonaws.com/${profile_picture}_medium.png"
    )
}

fun ProfileDataModel.toDomainModel() : ProfileDomainModel{
    return ProfileDomainModel(id = userID, email = email ?: "", name = name ?: "", bio = bio ?: "",
        //TODO remove the hardcode
        gender = gender ?: "", profilePictureUrl = "https://qupidon-images.s3.eu-central-1.amazonaws.com/${profilePictureUrl ?: ""}_medium.png"
    )
}