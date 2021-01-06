package com.nikolam.feature_main_screen.data


import android.content.Context
import com.nikolam.feature_main_screen.data.MainScreenService
import com.nikolam.feature_main_screen.data.model.ProfileModel
import com.nikolam.feature_main_screen.domain.MainRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class MainRepositoryImpl(
        private val mainService: MainScreenService,
) :
        MainRepository {
    override suspend fun getMatches(id: String): ArrayList<ProfileModel> =
        suspendCoroutine { cont ->
            val call = mainService.getMatches(id)
            call.enqueue(object : Callback<ArrayList<ProfileModel>> {
                override fun onResponse(
                        call: Call<ArrayList<ProfileModel>>,
                        response: Response<ArrayList<ProfileModel>>
                ) {
                  if (response.code() == 200) {
                      cont.resume(response.body()!!)
                  } else {
                      cont.resumeWithException(Exception())
                  }
                }

                override fun onFailure(call: Call<ArrayList<ProfileModel>>, t: Throwable) {
                    cont.resumeWithException(t)
                }

            })
        }
}

