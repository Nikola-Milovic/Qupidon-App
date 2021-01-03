package com.nikolam.feature_new_user.data

import com.nikolam.common.BaseUserAPIUrl
import com.nikolam.feature_new_user.data.model.NewProfileModel
import com.nikolam.feature_new_user.data.model.SaveResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface NewUserService {
    @POST("$BaseUserAPIUrl/profile/")
    fun saveProfile(@Query("id") id : String, @Body profile : NewProfileModel): Call<Void>
}