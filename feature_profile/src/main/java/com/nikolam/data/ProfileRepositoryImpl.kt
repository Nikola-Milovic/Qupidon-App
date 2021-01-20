package com.nikolam.data

import com.nikolam.domain.ProfileRepository
import com.nikolam.domain.models.ProfileDomainModel

class ProfileRepositoryImpl(private val sprofileService: ProfileService) : ProfileRepository {
    override suspend fun getProfile(id: String): ProfileDomainModel {
        TODO("Not yet implemented")
    }
}