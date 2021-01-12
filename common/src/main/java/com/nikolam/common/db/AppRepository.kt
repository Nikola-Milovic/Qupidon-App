package com.nikolam.common.db

import com.nikolam.common.db.models.MatchedUsersModel
import com.nikolam.common.db.models.UserDataModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class AppRepository(private val db: AppDatabase,
                    private val appRetrofitService: AppRetrofitService
) {

    fun getMatches(id: String) {
        appRetrofitService.getMatchedUsers(id).enqueue(object : Callback<MatchedUsersModel> {
            override fun onResponse(call: Call<MatchedUsersModel>, response: Response<MatchedUsersModel>) {
                if (response.code() == 200) {
                    Timber.d(response.body().toString())
                    response.body()?.let {
                        saveMatches(matchModel = it)
                    }
                }

            }

            override fun onFailure(call: Call<MatchedUsersModel>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }


    fun saveMatches(matchModel: MatchedUsersModel) {
        GlobalScope.launch(Dispatchers.IO) {
            matchModel.matches.forEach { id ->
                Timber.d("Added new match! $id")
                db.chatDao().addMatch(UserDataModel(id, ""))
            }
        }
    }
}