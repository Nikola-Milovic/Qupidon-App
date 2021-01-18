package com.nikolam.common.db

import com.nikolam.common.BaseMatchAPIUrl
import com.nikolam.common.db.models.MatchedUsersResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AppRetrofitService {
    @GET("$BaseMatchAPIUrl/matches/")
    fun getMatchedUsers(@Query("id") id: String): Call<List<String>>

    @GET("$BaseMatchAPIUrl/matches/profile/")
    fun getMatchedUserProfile(@Query("id") id: String): Call<MatchedUsersResponse>
}