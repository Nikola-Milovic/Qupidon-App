package com.nikolam.qupidon.di

import com.facebook.CallbackManager
import com.nikolam.common.navigation.NavManager
import org.koin.dsl.module

val facebookModule = module{
    single { provideFacebookCallbackManager() }
}

private fun provideFacebookCallbackManager() : CallbackManager{
    return CallbackManager.Factory.create()
}