package com.nikolam.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nikolam.data.db.models.MessageDataModel
import com.nikolam.data.db.models.ProfileDataModel
import com.nikolam.data.db.models.UserDataModel

@Database(entities = [UserDataModel::class, MessageDataModel::class, ProfileDataModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun chatDao(): ChatDao
    abstract fun profileDao(): ProfileDao
}