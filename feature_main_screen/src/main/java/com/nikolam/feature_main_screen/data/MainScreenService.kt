package com.nikolam.feature_main_screen.data

import com.nikolam.common.BaseMatchAPIUrl
import com.nikolam.data.db.models.MatchedUsersResponse
import com.nikolam.feature_main_screen.data.model.LikedUser
import com.nikolam.feature_main_screen.data.model.ProfileModel
import com.nikolam.feature_main_screen.data.model.RejectedUser
import retrofit2.Call
import retrofit2.http.*


interface MainScreenService {
    @GET("$BaseMatchAPIUrl/profiles/")
    fun getProfiles(@Query("id") id: String): Call<ArrayList<ProfileModel>>

    @GET("$BaseMatchAPIUrl/matches/")
    fun getMatchedUsers(@Query("id") id: String): Call<MatchedUsersResponse>

    @POST("$BaseMatchAPIUrl/like/")
    fun likeUser(@Query("id") id: String, @Body requestBody: LikedUser) : Call<Void>

    @POST("$BaseMatchAPIUrl/reject/")
    fun rejectUser(@Query("id") id : String, @Body requestBody: RejectedUser) : Call<Void>
}