package com.nikolam.common.di

import android.content.Context
import androidx.room.Room
import com.nikolam.common.db.AppDatabase
import com.nikolam.common.db.AppRepository
import com.nikolam.common.db.AppRetrofitService
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val dbModule = module {
    single { provideDB(get()) }
    single {AppRepository(get(), get())}
    single {provideAppRetrofitService(get(named("app")))}
}

private fun provideDB(context: Context): AppDatabase {
    return Room.databaseBuilder(
            context,
            AppDatabase::class.java, "app-db"
    ).build()
}

private fun provideAppRetrofitService(retrofit: Retrofit): AppRetrofitService {
    return retrofit.create(AppRetrofitService::class.java)
}