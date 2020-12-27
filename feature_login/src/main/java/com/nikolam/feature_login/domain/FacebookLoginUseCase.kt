package com.nikolam.feature_login.domain

import com.nikolam.feature_login.data.model.FacebookLoginResponse
import timber.log.Timber

class FacebookLoginUseCase(
    private val loginRepository: LoginRepository
) {

    sealed class Result {
        data class Success(val response : FacebookLoginResponse) : Result()
        data class Error(val e: Throwable?) : Result()
    }

    suspend fun execute(token: String): Result {
        return try {
            val response = loginRepository.facebookTokenLogin(token)
            Timber.d("response is $response")
            Result.Success(response)
        } catch (e: Exception) {
            Timber.e("exception thrown ${e.localizedMessage}")
            Result.Error(e)
        }
    }
}