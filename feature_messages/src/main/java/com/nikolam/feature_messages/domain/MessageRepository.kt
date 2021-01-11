package com.nikolam.feature_messages.domain

import com.nikolam.feature_messages.domain.models.MessageDomainModel
import com.nikolam.feature_messages.domain.models.UserDomainModel

interface MessageRepository {
    suspend fun getUserList() : ArrayList<UserDomainModel>
    suspend fun getMessagesWithUser(userID : String) : ArrayList<MessageDomainModel>
}