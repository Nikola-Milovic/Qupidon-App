package com.nikolam.feature_messages.di

import com.nikolam.feature_messages.presentation.chat.ChatViewModel
import com.nikolam.feature_messages.presentation.chat_list.ChatListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val chatModule = module {
    viewModel{ ChatViewModel(get()) }
}