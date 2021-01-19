package com.nikolam.feature_messages.domain

import com.nikolam.data.db.models.MessageDataModel
import com.nikolam.feature_messages.domain.models.UserDomainModel
import kotlinx.coroutines.flow.Flow

interface MessageRepository {
    suspend fun getUserList() : ArrayList<UserDomainModel>
    suspend fun getMessagesWithUser(userID : String, myID : String) : Flow<Array<com.nikolam.data.db.models.MessageDataModel>>
}