package com.nikolam.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nikolam.data.db.models.MessageDataModel
import com.nikolam.data.db.models.UserDataModel
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatDao {
    @Query("SELECT * FROM users")
    suspend fun getAllMatches() : List<UserDataModel>

    @Query("SELECT * FROM users WHERE userID = :id")
    suspend fun getUserProfile(id : String) : UserDataModel

    @Query("SELECT * FROM messages WHERE senderID = :userID OR (senderID = :myID AND receiverID = :userID)")
    fun getMessagesWithUser(userID : String, myID : String) : Flow<Array<MessageDataModel>>

    @Query("SELECT COUNT(userID) FROM users WHERE userID = :userID")
    fun userExists(userID : String) : Int

    @Insert
    suspend fun addMessage(message : MessageDataModel)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addMatch(user : UserDataModel)
}