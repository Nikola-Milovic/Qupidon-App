package com.nikolam.data

import com.nikolam.common.BaseMatchAPIUrl
import com.nikolam.common.BaseUserAPIUrl
import com.nikolam.data.models.ProfileDataModel
import retrofit2.Call
import retrofit2.http.*

interface ProfileService {
    @GET("$BaseUserAPIUrl/profile/")
    fun getProfile(@Query("id") id: String): Call<ProfileDataModel>
}