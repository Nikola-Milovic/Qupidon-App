package com.nikolam.feature_main_screen.domain

import com.nikolam.data.db.AppRepository
import timber.log.Timber

class GetMatchesUseCase(
    private val appRepository: com.nikolam.data.db.AppRepository
) {

    sealed class Result {
        object Success : Result()
        data class Error(val e: Throwable?) : Result()
    }

    suspend fun execute(id: String): Result {
        return try {
            val response = appRepository.getMatches(id)
            Timber.d("get matches response is $response")
            Result.Success
        } catch (e: Exception) {
            Timber.e("exception thrown ${e.localizedMessage}")
            Result.Error(e)
        }
    }
}