package com.nikolam.common.db

import com.nikolam.common.BaseMatchAPIUrl
import com.nikolam.common.db.models.MatchedUsersModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AppRetrofitService {
    @GET("$BaseMatchAPIUrl/matches/")
    fun getMatchedUsers(@Query("id") id: String): Call<MatchedUsersModel>
}