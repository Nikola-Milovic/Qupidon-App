package com.nikolam.domain

import com.nikolam.domain.models.ProfileDomainModel

interface ProfileRepository {
    suspend fun getProfile(id : String) : ProfileDomainModel
}