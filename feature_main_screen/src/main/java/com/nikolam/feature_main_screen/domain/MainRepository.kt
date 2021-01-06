package com.nikolam.feature_main_screen.domain

import com.nikolam.feature_main_screen.data.model.ProfileModel


interface MainRepository {
    suspend fun getMatches(id : String) : ArrayList<ProfileModel>
}