package com.nikolam.di

import com.nikolam.data.ProfileRepositoryImpl
import com.nikolam.data.ProfileService
import com.nikolam.domain.ProfileRepository
import com.nikolam.feature_profile.ProfileViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val profileModule = module {
    viewModel { ProfileViewModel(get(), get()) }
    single<ProfileRepository> {ProfileRepositoryImpl(get())}
    single { provideProfileService(get(named("app")))}
}


fun provideProfileService(retrofit: Retrofit): ProfileService {
    return retrofit.create(ProfileService::class.java)
}