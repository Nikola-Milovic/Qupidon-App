package com.nikolam.domain

import com.nikolam.domain.models.ProfileDomainModel
import timber.log.Timber

class GetProfileUseCase(
    private val repo: ProfileRepository
) {

    sealed class Result {
        data class Success(val profile : ProfileDomainModel) : Result()
        data class Error(val e: Throwable?) : Result()
    }

    suspend fun execute(id: String): Result {
        return try {
            val response = repo.getProfile(id)
            Timber.d("get matches response is $response")
            Result.Success(response)
        } catch (e: Exception) {
            Timber.e("exception thrown ${e.localizedMessage}")
            Result.Error(e)
        }
    }
}