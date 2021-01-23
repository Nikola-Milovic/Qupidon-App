package com.nikolam.data.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "Profile"
)
data class ProfileDataModel(
    @PrimaryKey val userID: String,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "profilePictureUrl") val profilePictureUrl: String?,
    @ColumnInfo(name = "bio") val bio: String?,
    @ColumnInfo(name = "gender") val gender: String?,
    @ColumnInfo(name = "email") val email: String?
)