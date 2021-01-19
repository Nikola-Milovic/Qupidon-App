package com.nikolam.feature_main_screen.domain

import timber.log.Timber

class SaveTokenUseCase(
    private val mainRepository: MainRepository
) {

    sealed class Result {
        object Success : Result()
        data class Error(val e: Throwable?) : Result()
    }

    suspend fun execute(id : String, token: String): Result {
        return try {
            val response = mainRepository.saveFCMToken(id, token)
            Timber.d("get matches response is $response")
            Result.Success
        } catch (e: Exception) {
            Timber.e("exception thrown ${e.localizedMessage}")
            Result.Error(e)
        }
    }
}