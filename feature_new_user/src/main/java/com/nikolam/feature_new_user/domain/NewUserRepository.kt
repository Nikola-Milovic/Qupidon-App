package com.nikolam.feature_new_user.domain

import com.nikolam.feature_new_user.data.model.NewProfileModel
import com.nikolam.feature_new_user.data.model.SaveResponse


interface NewUserRepository {
    suspend fun saveProfile(id: String, profileModel: NewProfileModel): SaveResponse
    suspend fun uploadProfilePic(id : String, filePath : String) : SaveResponse
}