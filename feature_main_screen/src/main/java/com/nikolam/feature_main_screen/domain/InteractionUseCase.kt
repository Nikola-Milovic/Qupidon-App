package com.nikolam.feature_main_screen.domain

class InteractionUseCase(
    private val mainRepository: MainRepository
) {

    suspend fun likeUser(id: String, likedID : String) {
        mainRepository.likeUser(id, likedID)
    }

    suspend fun rejectUser(id: String, rejectedID : String) {
        mainRepository.rejectUser(id, rejectedID)
    }
}