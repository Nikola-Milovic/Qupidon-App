package com.nikolam.feature_messages.domain

import com.nikolam.data.db.models.MessageDataModel
import kotlinx.coroutines.flow.Flow

class GetChatMessagesUseCase(
        private val messageRepository: MessageRepository
) {
    
    suspend fun execute(id : String, myID : String): Flow<Array<com.nikolam.data.db.models.MessageDataModel>> {
        return messageRepository.getMessagesWithUser(id, myID)
    }
}