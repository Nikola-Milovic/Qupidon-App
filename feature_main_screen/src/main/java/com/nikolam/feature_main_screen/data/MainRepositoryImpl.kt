package com.nikolam.feature_main_screen.data


import com.nikolam.common.db.AppDatabase
import com.nikolam.common.db.models.UserDataModel
import com.nikolam.feature_main_screen.data.model.LikeResponse
import com.nikolam.feature_main_screen.data.model.LikedUser
import com.nikolam.feature_main_screen.data.model.ProfileModel
import com.nikolam.feature_main_screen.data.model.RejectedUser
import com.nikolam.feature_main_screen.domain.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


class MainRepositoryImpl(
    private val mainService: MainScreenService,
    private val db: AppDatabase
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

    override suspend fun likeUser(id: String, likeID: String) =
        withContext(Dispatchers.IO + NonCancellable) {
            Timber.d("LIKED ID IS $likeID")
            val likedUser = LikedUser(likeID)
            mainService.likeUser(id, likedUser).enqueue(object : Callback<LikeResponse> {
                override fun onResponse(
                    call: Call<LikeResponse>,
                    response: Response<LikeResponse>
                ) {
                    Timber.d("Response is ${response.body()}")
                    if (response.code() == 200) {
                        if (response.body() != null) {
                            Timber.d("MATCHED ID IS ${response.body()!!.matched_id}")
                            launch {
                                db.chatDao().addMatch(
                                    UserDataModel(
                                        response.body()!!.matched_id,
                                        "Nikolina"
                                    )
                                )
                                Timber.d("Added new match")
                            }

                        }
                    }
                }

                override fun onFailure(call: Call<LikeResponse>, t: Throwable) {
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

