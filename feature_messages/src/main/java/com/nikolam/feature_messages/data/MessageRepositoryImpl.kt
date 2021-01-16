package com.nikolam.feature_messages.data

import com.nikolam.common.db.AppDatabase
import com.nikolam.common.db.models.MessageDataModel
import com.nikolam.feature_messages.domain.MessageRepository
import com.nikolam.feature_messages.domain.models.MessageDomainModel
import com.nikolam.feature_messages.domain.models.UserDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.zip
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class MessageRepositoryImpl (private val db : AppDatabase) : MessageRepository{
    private val chatDao = db.chatDao()

    override suspend fun getUserList(): ArrayList<UserDomainModel> {
        val users = arrayListOf<UserDomainModel>()
        chatDao.getAllMatches().forEach {
            users.add(UserDomainModel(it.userID, it.name ?: ""))
        }

        return users
    }


    override suspend fun getMessagesWithUser(userID: String, myID : String) : Flow<Array<MessageDataModel>> {
        val messages = db.chatDao().getMessagesWithUser(id = userID)
        val mymessages = db.chatDao().getMessagesWithUser(id = myID)
        return messages
    }

}