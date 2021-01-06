package com.nikolam.feature_main_screen.domain

import com.nikolam.feature_main_screen.data.model.ProfileModel
import timber.log.Timber

class GetMatchesUseCase(
    private val mainRepository: MainRepository
) {

    sealed class Result {
        data class Success(val users : ArrayList<ProfileModel>) : Result()
        data class Error(val e: Throwable?) : Result()
    }

    suspend fun execute(id: String): Result {
        return try {
            val response = mainRepository.getMatches(id)
            Timber.d("save image response is $response")
            Result.Success(response)
        } catch (e: Exception) {
            Timber.e("exception thrown ${e.localizedMessage}")
            Result.Error(e)
        }
    }
}