package com.nikolam.feature_login.di

import com.nikolam.feature_login.domain.FacebookLoginUseCase
import com.nikolam.feature_login.data.LoginRepositoryImpl
import com.nikolam.feature_login.data.LoginService
import com.nikolam.feature_login.domain.LoginRepository
import com.nikolam.feature_login.presentation.LoginViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

internal val loginModule = module {
    viewModel { LoginViewModel(get(), get()) }
    single <LoginRepository> { LoginRepositoryImpl(get())}
    single { provideLoginService(get()) }
    single { FacebookLoginUseCase(get()) }
}

fun provideLoginService(retrofit: Retrofit): LoginService {
    return retrofit.create(LoginService::class.java)
}