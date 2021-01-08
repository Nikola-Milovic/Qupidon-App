package com.nikolam.feature_main_screen.data


import com.nikolam.feature_main_screen.data.model.LikedUser
import com.nikolam.feature_main_screen.data.model.ProfileModel
import com.nikolam.feature_main_screen.data.model.RejectedUser
import com.nikolam.feature_main_screen.domain.MainRepository
import okhttp3.MediaType
import okhttp3.RequestBody
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

    override suspend fun likeUser(id: String, likeID: String) {
        val likedUser = LikedUser(likeID)
        mainService.likeUser(id, likedUser).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Timber.d("Response is $response")
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Timber.d("Failure is $t")
            }

        })
    }

    override suspend fun rejectUser(id: String, rejectID: String) {
        val rejectedUser = RejectedUser(rejectID)
        mainService.rejectUser(id, rejectedUser).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
            }

        })
    }
}

