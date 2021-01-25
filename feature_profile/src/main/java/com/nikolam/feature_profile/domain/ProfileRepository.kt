package com.nikolam.feature_profile.domain

import com.nikolam.feature_profile.domain.models.ProfileDomainModel

interface ProfileRepository {
    suspend fun getProfile(id : String) : ProfileDomainModel
    suspend fun saveProfile(profile: ProfileDomainModel): Boolean
}