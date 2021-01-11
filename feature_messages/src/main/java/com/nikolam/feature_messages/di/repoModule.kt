package com.nikolam.feature_messages.di

import com.nikolam.feature_messages.data.MessageRepositoryImpl
import com.nikolam.feature_messages.domain.MessageRepository
import org.koin.dsl.module

val repoModule = module {
    single <MessageRepository> {MessageRepositoryImpl(get())}
}