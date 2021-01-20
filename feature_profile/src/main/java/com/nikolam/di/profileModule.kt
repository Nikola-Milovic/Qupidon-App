package com.nikolam.di

import com.nikolam.feature_profile.ProfileViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val profileModule = module{
    viewModel { ProfileViewModel() }
}