package com.nikolam.feature_login.di

import com.nikolam.feature_login.presentation.LoginViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val loginModule = module {
    viewModel { LoginViewModel(get()) }
}