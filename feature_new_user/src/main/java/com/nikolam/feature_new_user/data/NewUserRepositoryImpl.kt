package com.nikolam.feature_new_user.data


import com.nikolam.feature_new_user.data.model.NewProfileModel
import com.nikolam.feature_new_user.data.model.SaveResponse
import com.nikolam.feature_new_user.domain.NewUserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class NewUserRepositoryImpl(
    private val newUserService: NewUserService
) :
    NewUserRepository {

    @ExperimentalCoroutinesApi
    override suspend fun saveProfile(id: String, profileModel: NewProfileModel): SaveResponse =
        suspendCoroutine { cont ->
            Timber.d(profileModel.toString())
            val call = newUserService.saveProfile(id, profileModel)
            call.enqueue(object : Callback<Void> {
                override fun onResponse(
                    call: Call<Void>,
                    response: Response<Void>
                ) {
                    cont.resume(SaveResponse(response.code()))
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    cont.resumeWithException(t)
                }

            })
        }
}