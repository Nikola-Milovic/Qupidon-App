package com.nikolam.common.di

import okhttp3.ResponseBody
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type


val networkingModule = module {

    single { provideRetrofit() }

}

fun provideRetrofit() : Retrofit {
    return Retrofit.Builder()
        .baseUrl("http://10.0.2.2:3000/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}