package com.nikolam.data.db

import com.nikolam.data.db.models.MatchedUsersResponse
import com.nikolam.data.db.models.ProfileDataModel
import com.nikolam.data.db.models.UserDataModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class AppRepository(private val db: AppDatabase,
                    private val appRetrofitService: AppRetrofitService
) {

    fun getMatches(id: String) {
        appRetrofitService.getMatchedUsers(id).enqueue(object : Callback<List<String>> {
            override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {
                if (response.code() == 200) {
                    Timber.d(response.body().toString())
                    response.body()?.let {
                        saveMatches(matchIds = it)
                    }
                }

            }

            override fun onFailure(call: Call<List<String>>, t: Throwable) {
                Timber.d(t.localizedMessage)
            }

        })
    }


    fun saveMatches(matchIds: List<String>) {
        GlobalScope.launch(Dispatchers.IO + NonCancellable) {
            matchIds.forEach { id ->
                appRetrofitService.getMatchedUserProfile(id).enqueue(object : Callback<MatchedUsersResponse> {
                    override fun onResponse(call: Call<MatchedUsersResponse>, response: Response<MatchedUsersResponse>) {
                        if (response.isSuccessful) {
                            GlobalScope.launch {
                                val profile = response.body()
                                Timber.d("Added new match! $id")
                                db.chatDao().addMatch(UserDataModel(id, profile?.name, "https://qupidon-images.s3.eu-central-1.amazonaws.com/${profile?.profile_pic}"))
                            }
                        }
                    }

                    override fun onFailure(call: Call<MatchedUsersResponse>, t: Throwable) {
                        TODO("Not yet implemented")
                    }
                }
                )
            }
        }
    }


    suspend fun saveProfile(profile : ProfileDataModel){
        db.profileDao().addProfile(profile)
    }

}