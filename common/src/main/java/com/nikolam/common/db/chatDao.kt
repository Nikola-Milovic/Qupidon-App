package com.nikolam.common.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nikolam.common.db.models.MessageDataModel
import com.nikolam.common.db.models.UserDataModel
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatDao {
    @Query("SELECT * FROM users")
    suspend fun getAllMatches() : List<UserDataModel>

    @Query("SELECT * FROM messages WHERE userID = :id")
    fun getMessagesWithUser(id : String) : Flow<Array<MessageDataModel>>

    @Insert
    suspend fun addMessage(message : MessageDataModel)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addMatch(user : UserDataModel)
}