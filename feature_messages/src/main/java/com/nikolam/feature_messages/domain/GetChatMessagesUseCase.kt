package com.nikolam.feature_messages.domain

import com.nikolam.common.db.models.MessageDataModel
import com.nikolam.feature_messages.domain.models.MessageDomainModel
import com.nikolam.feature_messages.domain.models.UserDomainModel
import kotlinx.coroutines.flow.Flow
import timber.log.Timber

class GetChatMessagesUseCase(
        private val messageRepository: MessageRepository
) {


    suspend fun execute(id : String): Flow<Array<MessageDataModel>> {
        return messageRepository.getMessagesWithUser(id)
    }
}