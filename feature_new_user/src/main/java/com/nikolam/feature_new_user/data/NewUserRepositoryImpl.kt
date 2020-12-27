package com.nikolam.feature_new_user.data


import com.nikolam.feature_new_user.data.model.NewProfileModel
import com.nikolam.feature_new_user.data.model.SaveResponse
import com.nikolam.feature_new_user.domain.NewUserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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
            val call = newUserService.saveProfile(id, profileModel)
            call.enqueue(object : Callback<SaveResponse> {
                override fun onResponse(
                    call: Call<SaveResponse>,
                    response: Response<SaveResponse>
                ) {
                    cont.resume(response.body()!!)
                }

                override fun onFailure(call: Call<SaveResponse>, t: Throwable) {
                    cont.resumeWithException(t)
                }

            })
        }
}