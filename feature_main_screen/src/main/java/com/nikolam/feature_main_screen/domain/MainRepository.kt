package com.nikolam.feature_main_screen.domain

import com.nikolam.feature_main_screen.data.model.ProfileModel


interface MainRepository {
    suspend fun getProfiles(id : String) : ArrayList<ProfileModel>
    suspend fun likeUser(id : String, likeID : String)
    suspend fun rejectUser(id : String, rejectID : String)
    suspend fun saveFCMToken(id : String, token : String)
}