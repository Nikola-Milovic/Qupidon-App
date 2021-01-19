package com.nikolam.data.di

import android.content.Context
import androidx.room.Room
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val dbModule = module {
    single { provideDB(get()) }
    single { com.nikolam.data.db.AppRepository(get(), get()) }
    single { provideAppRetrofitService(get(named("app"))) }
}

private fun provideDB(context: Context): com.nikolam.data.db.AppDatabase {
    return Room.databaseBuilder(
            context,
            com.nikolam.data.db.AppDatabase::class.java, "app-db"
    ).build()
}

private fun provideAppRetrofitService(retrofit: Retrofit): com.nikolam.data.db.AppRetrofitService {
    return retrofit.create(com.nikolam.data.db.AppRetrofitService::class.java)
}