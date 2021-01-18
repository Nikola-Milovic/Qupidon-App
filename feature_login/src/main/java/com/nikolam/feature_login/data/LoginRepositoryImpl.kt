package com.nikolam.feature_login.data

import com.nikolam.feature_login.domain.LoginRepository
import com.nikolam.feature_login.data.model.FacebookLoginResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class LoginRepositoryImpl(
        private val loginService: LoginService
) :
        LoginRepository {

    @ExperimentalCoroutinesApi
    override suspend fun facebookTokenLogin(token : String): FacebookLoginResponse=
        suspendCoroutine { cont ->
            Timber.d("sort by in repo is $token")
            val call = loginService.facebookTokenLogin(token)
            call.enqueue(object : Callback<FacebookLoginResponse>{
                override fun onResponse(call: Call<FacebookLoginResponse>, response: Response<FacebookLoginResponse>) {

                    cont.resume(response.body()!!)
                }

                override fun onFailure(call: Call<FacebookLoginResponse>, t: Throwable) {
                    cont.resumeWithException(t)
                }

            })
        }
}