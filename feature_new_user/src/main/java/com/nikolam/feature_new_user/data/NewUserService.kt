package com.nikolam.feature_new_user.data

import com.nikolam.common.BaseChatUserApiUrl
import com.nikolam.common.BaseImageAPIUrl
import com.nikolam.common.BaseUserAPIUrl
import com.nikolam.feature_new_user.data.model.NewProfileModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*


interface NewUserService {
    @POST("$BaseUserAPIUrl/profile/")
    fun saveProfile(@Query("id") id: String, @Body profile: NewProfileModel): Call<Void>

    @Multipart
    @POST("$BaseImageAPIUrl/ppic")
    fun postImage(@Part("user_id") id : RequestBody,@Part image : MultipartBody.Part) : Call<Void>
}

