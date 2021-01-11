package com.nikolam.common.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nikolam.common.db.models.MessageDataModel
import com.nikolam.common.db.models.UserDataModel

@Database(entities = [UserDataModel::class, MessageDataModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun chatDao(): ChatDao
}