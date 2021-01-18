package com.nikolam.feature_main_screen.data

import com.nikolam.common.BaseChatUserApiUrl
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*


interface MainScreenChatService {
    @POST("$BaseChatUserApiUrl/tok")
    fun saveFCMToken(@Query("id") id: String, @Body token: RequestBody): Call<Void>
}