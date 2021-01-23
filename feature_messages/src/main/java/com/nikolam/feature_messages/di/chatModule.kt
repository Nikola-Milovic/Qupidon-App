package com.nikolam.feature_messages.di

import com.nikolam.feature_messages.domain.GetChatMessagesUseCase
import com.nikolam.feature_messages.domain.GetUserProfileUseCase
import com.nikolam.feature_messages.presentation.chat.ChatViewModel
import com.nikolam.feature_messages.presentation.chat_list.ChatListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val chatModule = module {
    viewModel{ ChatViewModel(get(), get(), get(), get()) }
    single{ GetChatMessagesUseCase(get()) }
    single{ GetUserProfileUseCase(get()) }
}