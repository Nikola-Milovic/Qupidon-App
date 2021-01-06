package com.nikolam.feature_main_screen.data

import com.nikolam.common.BaseMatchAPIUrl
import com.nikolam.feature_main_screen.data.model.ProfileModel
import retrofit2.Call
import retrofit2.http.*


interface MainScreenService {
    @GET("$BaseMatchAPIUrl/matches/")
    fun getMatches(@Query("id") id: String): Call<ArrayList<ProfileModel>>
}