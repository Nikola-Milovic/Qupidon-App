package com.nikolam.feature_profile.di

import com.nikolam.feature_profile.data.ProfileRepositoryImpl
import com.nikolam.feature_profile.data.ProfileService
import com.nikolam.feature_profile.domain.ProfileRepository
import com.nikolam.feature_profile.presenter.edit_profile.EditProfileViewModel
import com.nikolam.feature_profile.presenter.profile.ProfileViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val profileModule = module {
    viewModel { ProfileViewModel(get(), get()) }
    viewModel { EditProfileViewModel(get(), get()) }
    single<ProfileRepository> {ProfileRepositoryImpl(get(), get())}
    single { provideProfileService(get(named("app")))}
}

fun provideProfileService(retrofit: Retrofit): ProfileService {
    return retrofit.create(ProfileService::class.java)
}