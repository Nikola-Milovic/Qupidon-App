package com.nikolam.feature_messages.data

import com.nikolam.data.db.AppDatabase
import com.nikolam.data.db.models.MessageDataModel
import com.nikolam.feature_messages.domain.MessageRepository
import com.nikolam.feature_messages.domain.models.UserDomainModel
import kotlinx.coroutines.flow.Flow

class MessageRepositoryImpl (private val db : com.nikolam.data.db.AppDatabase) : MessageRepository{
    private val chatDao = db.chatDao()

    override suspend fun getUserList(): ArrayList<UserDomainModel> {
        val users = arrayListOf<UserDomainModel>()
        chatDao.getAllMatches().forEach {
            //TODO add default image
            users.add(UserDomainModel(it.userID, it.name ?: "", profilePicture = it.profilePictureUrl ?: ""))
        }

        return users
    }


    override suspend fun getMessagesWithUser(userID: String, myID : String) : Flow<Array<com.nikolam.data.db.models.MessageDataModel>> {
        return db.chatDao().getMessagesWithUser(userID, myID)
    }

}