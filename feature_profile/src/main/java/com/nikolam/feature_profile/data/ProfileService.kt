package com.nikolam.feature_profile.data

import com.nikolam.common.BaseUserAPIUrl
import com.nikolam.feature_profile.data.models.ProfileServerDataModel

import retrofit2.Call
import retrofit2.http.*

interface ProfileService {
    @GET("$BaseUserAPIUrl/profile/")
    fun getProfile(@Query("id") id: String): Call<ProfileServerDataModel>

    @POST("$BaseUserAPIUrl/profile/")
    fun saveProfile(@Query("id") id: String, @Body profile: ProfileServerDataModel): Call<Void>
}