package com.nikolam.data

import com.nikolam.data.models.ProfileDataModel
import com.nikolam.data.models.toDomainModel
import com.nikolam.domain.ProfileRepository
import com.nikolam.domain.models.ProfileDomainModel
import retrofit2.Call
import retrofit2.Response
import timber.log.Timber
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class ProfileRepositoryImpl(private val profileService: ProfileService) : ProfileRepository {

    override suspend fun getProfile(id: String): ProfileDomainModel = suspendCoroutine { cont ->
        profileService.getProfile(id).enqueue(object : retrofit2.Callback<ProfileDataModel>{
            override fun onResponse(call: Call<ProfileDataModel>, response: Response<ProfileDataModel>) {
                if (response.isSuccessful){
                    Timber.d("response ${response.body()!!.toDomainModel()}")
                    cont.resume(response.body()!!.toDomainModel())
                }
            }

            override fun onFailure(call: Call<ProfileDataModel>, t: Throwable) {
                cont.resumeWithException(t)
            }

        })
    }
}