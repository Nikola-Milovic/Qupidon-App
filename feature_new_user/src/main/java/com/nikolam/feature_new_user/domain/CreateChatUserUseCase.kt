package com.nikolam.feature_new_user.domain


import com.nikolam.feature_new_user.data.model.NewProfileModel
import com.nikolam.feature_new_user.data.model.SaveResponse
import timber.log.Timber

class CreateChatUserUseCase(
    private val newUserRepository: NewUserRepository
) {

    suspend fun execute(id: String) {
        Timber.d("Create new user")
        newUserRepository.createChatUser(id)
    }

}