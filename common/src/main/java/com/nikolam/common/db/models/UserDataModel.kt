package com.nikolam.common.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "Users"
)
data class UserDataModel(
    @PrimaryKey val userID: String,
    @ColumnInfo(name = "name") val name: String?,
)