package com.nikolam.feature_new_user.di

import com.nikolam.feature_new_user.data.NewUserRepositoryImpl
import com.nikolam.feature_new_user.data.NewUserService
import com.nikolam.feature_new_user.domain.NewUserRepository
import com.nikolam.feature_new_user.presentation.NewUserViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

internal val newUserModule = module {
    viewModel { NewUserViewModel(get(), get(), get()) }
    single <NewUserRepository> { NewUserRepositoryImpl(get(), get())}
    single { provideNewChatUserService(get(named("chat"))) }
    single { provideNewUserService(get(named("app"))) }
}

fun provideNewUserService(retrofit: Retrofit): NewUserService {
    return retrofit.create(NewUserService::class.java)
}

fun provideNewChatUserService(retrofit: Retrofit): com.nikolam.feature_new_user.data.ChatUserService {
    return retrofit.create(com.nikolam.feature_new_user.data.ChatUserService::class.java)
}

