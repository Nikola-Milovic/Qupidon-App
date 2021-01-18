package com.nikolam.feature_new_user.data

import com.nikolam.common.BaseChatUserApiUrl
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Query

interface ChatUserService {
    @POST("$BaseChatUserApiUrl/")
    fun createChatUser(@Query("id") id: String): Call<Void>
}