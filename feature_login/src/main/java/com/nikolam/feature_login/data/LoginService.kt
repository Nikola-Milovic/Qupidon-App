package com.nikolam.feature_login.data

import com.nikolam.common.BaseAuthAPIUrl
import com.nikolam.feature_login.data.model.FacebookLoginResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface LoginService {
    @POST("$BaseAuthAPIUrl/facebook/token")
    fun facebookTokenLogin(@Query("access_token") token : String): Call<FacebookLoginResponse>
}