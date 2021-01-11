package com.nikolam.feature_main_screen.di
import com.nikolam.feature_main_screen.data.MainRepositoryImpl
import com.nikolam.feature_main_screen.data.MainScreenService
import com.nikolam.feature_main_screen.domain.GetMatchesUseCase
import com.nikolam.feature_main_screen.domain.InteractionUseCase
import com.nikolam.feature_main_screen.domain.MainRepository
import com.nikolam.feature_main_screen.presentation.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val mainScreenModule = module{

    viewModel { MainViewModel(get(), get(), get(), get()) }
    single <MainRepository> { MainRepositoryImpl(get(), get())}
    single { provideMainScreenService(get()) }
    single { GetMatchesUseCase(get()) }
    single { InteractionUseCase(get()) }
}

fun provideMainScreenService(retrofit: Retrofit): MainScreenService {
    return retrofit.create(MainScreenService::class.java)
}