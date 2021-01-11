package com.nikolam.common.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.nikolam.common.db.models.MessageDataModel
import com.nikolam.common.db.models.UserDataModel
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatDao {
    @Query("SELECT * FROM users")
    suspend fun getAllMatches() : List<UserDataModel>

    @Query("SELECT * FROM messages WHERE userID = :id")
    suspend fun getMessagesWithUser(id : String) : List<MessageDataModel>

    @Insert
    suspend fun addMessage(message : MessageDataModel)

    @Insert
    suspend fun addMatch(user : UserDataModel)
}