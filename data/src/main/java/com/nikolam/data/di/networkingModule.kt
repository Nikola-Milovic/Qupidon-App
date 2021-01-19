package com.nikolam.data.di

import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkingModule = module {

    single(named("app")) { provideRetrofit() }
    single(named("chat")) { provideChatRetrofit() }

    single { com.nikolam.data.messaging.MessagingManager(get(), get()) }
    single { provideMessagingService(get(named("chat"))) }

}

fun provideRetrofit(): Retrofit {
    return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
}

fun provideChatRetrofit(): Retrofit {
    return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3001/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
}

fun provideMessagingService(retrofit: Retrofit): com.nikolam.data.messaging.MessagingService {
    return retrofit.create(com.nikolam.data.messaging.MessagingService::class.java)
}