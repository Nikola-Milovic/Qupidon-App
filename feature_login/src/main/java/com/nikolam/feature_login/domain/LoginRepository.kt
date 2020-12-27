package com.nikolam.feature_login.domain

import com.nikolam.feature_login.data.model.FacebookLoginResponse

interface LoginRepository {
    suspend fun facebookTokenLogin(token : String): FacebookLoginResponse
}