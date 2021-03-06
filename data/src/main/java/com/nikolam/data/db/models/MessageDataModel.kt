package com.nikolam.data.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "messages"
)
data class MessageDataModel(
    @PrimaryKey(autoGenerate = true) val uid: Int?,
    @ColumnInfo(name = "content") val contents: String?,
    @ColumnInfo(name = "senderID") val senderID : String?,
    @ColumnInfo(name = "receiverID") val receiverID : String?,
    @ColumnInfo(name = "isMine") val isMine : Boolean?,
    @ColumnInfo(name = "addedAtMillis") val addedAtMillis: Long?
)