package com.nikolam.feature_messages.domain.models

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class MessageDomainModel(val userID : String, val id : Int, val content :String, val addedAtMillis : Long, val isMine : String )
