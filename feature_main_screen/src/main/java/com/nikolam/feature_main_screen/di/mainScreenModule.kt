package com.nikolam.feature_main_screen.di
import com.nikolam.feature_main_screen.presentation.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainScreenModule = module{

    viewModel { MainViewModel(get()) }
}