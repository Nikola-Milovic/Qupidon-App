package com.nikolam.feature_messages.domain

import com.nikolam.feature_messages.domain.models.UserDomainModel
import timber.log.Timber

class GetUsersUseCase(
    private val messageRepository: MessageRepository
) {

        sealed class Result {
            data class Success(val response : ArrayList<UserDomainModel>) : Result()
            data class Error(val e: Throwable?) : Result()
        }

        suspend fun execute(): Result {
            return try {
                val response = messageRepository.getUserList()
                Timber.d("response is $response")
                Result.Success(response)
            } catch (e: Exception) {
                Timber.e("exception thrown ${e.localizedMessage}")
                Result.Error(e)
            }
        }
    }