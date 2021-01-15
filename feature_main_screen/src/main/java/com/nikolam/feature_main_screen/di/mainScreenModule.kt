package com.nikolam.feature_main_screen.di
import com.nikolam.feature_main_screen.data.MainRepositoryImpl
import com.nikolam.feature_main_screen.data.MainScreenChatService
import com.nikolam.feature_main_screen.data.MainScreenService
import com.nikolam.feature_main_screen.domain.*
import com.nikolam.feature_main_screen.presentation.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val mainScreenModule = module{

    viewModel { MainViewModel(get(), get(), get(), get(), get(), get()) }
    single <MainRepository> { MainRepositoryImpl(get(), get(), get())}
    single { provideMainScreenService(get(named("app"))) }
    single { provideMainScreenChatService(get(named("chat"))) }
    single { GetProfilesUseCase(get()) }
    single { InteractionUseCase(get()) }
    single { GetMatchesUseCase(get()) }
    single { SaveTokenUseCase(get()) }
}

fun provideMainScreenService(retrofit: Retrofit): MainScreenService {
    return retrofit.create(MainScreenService::class.java)
}

fun provideMainScreenChatService(retrofit: Retrofit): MainScreenChatService {
    return retrofit.create(MainScreenChatService::class.java)
}