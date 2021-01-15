package com.nikolam.feature_main_screen.data

import com.nikolam.common.BaseChatUserApiUrl
import com.nikolam.common.BaseMatchAPIUrl
import com.nikolam.common.db.models.MatchedUsersModel
import com.nikolam.feature_main_screen.data.model.LikedUser
import com.nikolam.feature_main_screen.data.model.ProfileModel
import com.nikolam.feature_main_screen.data.model.RejectedUser
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*


interface MainScreenChatService {
    @POST("$BaseChatUserApiUrl/tok")
    fun saveFCMToken(@Query("id") id: String, @Body token: RequestBody): Call<Void>
}