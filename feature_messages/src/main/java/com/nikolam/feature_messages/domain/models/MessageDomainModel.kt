package com.nikolam.feature_messages.domain.models

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.nikolam.common.db.models.MessageDataModel

data class MessageDomainModel(val userID : String, val id : Int, val content : String, val addedAtMillis : Long, val isMine : Boolean )


fun MessageDataModel.toDomainModel() : MessageDomainModel{
    return MessageDomainModel(userID = userID?: "", content = contents ?: "", addedAtMillis = addedAtMillis ?: 0, isMine = isMine ?: false, id = 0)
}