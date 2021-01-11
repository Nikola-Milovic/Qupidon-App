package com.nikolam.common.di

import android.content.Context
import androidx.room.Room
import com.nikolam.common.db.AppDatabase
import org.koin.dsl.module

val dbModule = module {
    single { provideDB(get()) }
}

private fun provideDB(context: Context): AppDatabase {
    return Room.databaseBuilder(
            context,
            AppDatabase::class.java, "app-db"
    ).build()
}