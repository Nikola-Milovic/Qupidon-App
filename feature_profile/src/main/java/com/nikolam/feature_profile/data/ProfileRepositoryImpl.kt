package com.nikolam.feature_profile.data

import com.nikolam.data.db.AppRepository
import com.nikolam.data.db.models.ProfileDataModel
import com.nikolam.feature_profile.data.models.ProfileServerDataModel
import com.nikolam.feature_profile.data.models.toDomainModel
import com.nikolam.feature_profile.domain.ProfileRepository
import com.nikolam.feature_profile.domain.models.ProfileDomainModel
import kotlinx.coroutines.*

class ProfileRepositoryImpl(private val profileService: ProfileService,
                            private val appRepository: AppRepository
) : ProfileRepository {

    override suspend fun getProfile(id: String): ProfileDomainModel {

        val profile = appRepository.getProfile(id)

        return profile.toDomainModel()

        //TODO move this to somewhere else, so we can periodically update the profile with the data from the server
//        profileService.getProfile(id).enqueue(object : retrofit2.Callback<ProfileDataModel> {
//            override fun onResponse(call: Call<ProfileDataModel>, response: Response<ProfileDataModel>) {
//                if (response.isSuccessful) {
//                    Timber.d("response ${response.body()!!.toDomainModel()}")
//                    cont.resume(response.body()!!.toDomainModel())
//                }
//            }
//
//            override fun onFailure(call: Call<ProfileDataModel>, t: Throwable) {
//                cont.resumeWithException(t)
//            }
//
//        })

    }

    override suspend fun saveProfile( profile: ProfileDomainModel) = coroutineScope{
       val dbProfile = ProfileDataModel(userID = profile.id, name = profile.name, profilePictureUrl = profile.profilePictureUrl,bio = profile.bio,
               gender = profile.gender, email = profile.email
               )
        val profileResponse = ProfileServerDataModel("", user_id = profile.id, email = profile.email, name = profile.name, bio = profile.bio,
        gender = profile.gender, location_id = "", profile_picture = profile.profilePictureUrl
                )

        val task1 = async { appRepository.updateProfile(dbProfile) }
        val task2 = async { profileService.saveProfile(profile.id, profileResponse) }

        val defs = arrayListOf(task1, task2)

        defs.awaitAll()

        return@coroutineScope true
    }

}































