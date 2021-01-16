package com.nikolam.feature_messages.data

import com.nikolam.common.db.AppDatabase
import com.nikolam.common.db.models.MessageDataModel
import com.nikolam.feature_messages.domain.MessageRepository
import com.nikolam.feature_messages.domain.models.UserDomainModel
import kotlinx.coroutines.flow.Flow

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
        return db.chatDao().getMessagesWithUser(userID, myID)
    }

}