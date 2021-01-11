package com.nikolam.feature_messages.data

import com.nikolam.common.db.AppDatabase
import com.nikolam.feature_messages.domain.MessageRepository
import com.nikolam.feature_messages.domain.models.MessageDomainModel
import com.nikolam.feature_messages.domain.models.UserDomainModel

class MessageRepositoryImpl (private val db : AppDatabase) : MessageRepository{
    private val chatDao = db.chatDao()
    override suspend fun getUserList(): ArrayList<UserDomainModel> {
        TODO("Not yet implemented")
    }

    override suspend fun getMessagesWithUser(userID: String): ArrayList<MessageDomainModel> {
        TODO("Not yet implemented")
    }

}