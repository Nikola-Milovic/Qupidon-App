package com.nikolam.feature_messages.di

import com.nikolam.feature_messages.domain.GetUsersUseCase
import com.nikolam.feature_messages.presentation.chat_list.ChatListViewModel
import org.koin.android.viewmodel.dsl.viewModel

import org.koin.dsl.module

val chatListModule = module {
    viewModel{ChatListViewModel(get(), get())}
    single {GetUsersUseCase(get())}
}