package com.nikolam.feature_new_user.domain


import com.nikolam.feature_new_user.data.model.NewProfileModel
import com.nikolam.feature_new_user.data.model.SaveResponse
import timber.log.Timber

class SaveProfilePictureUseCase(
    private val newUserRepository: NewUserRepository
) {

    sealed class Result {
        data class Success(val response : SaveResponse) : Result()
        data class Error(val e: Throwable?) : Result()
    }

    suspend fun execute(id: String, filepath : String): Result {
        return try {
            val response = newUserRepository.uploadProfilePic(id ,filepath)
            Timber.d("save image response is $response")
            Result.Success(response)
        } catch (e: Exception) {
            Timber.e("exception thrown ${e.localizedMessage}")
            Result.Error(e)
        }
    }
}