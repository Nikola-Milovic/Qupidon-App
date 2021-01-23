package com.nikolam.feature_new_user.data


import android.content.Context
import com.nikolam.data.db.AppDatabase
import com.nikolam.data.db.AppRepository
import com.nikolam.feature_new_user.data.model.NewProfileModel
import com.nikolam.feature_new_user.data.model.SaveResponse
import com.nikolam.feature_new_user.domain.NewUserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.io.File
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class NewUserRepositoryImpl(
        private val newUserService: NewUserService,
        private val chatUserService: ChatUserService
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

    override suspend fun createChatUser(id: String) {
        Timber.d("Create new chat user  $id")
        chatUserService.createChatUser(id).enqueue(EmptyCallback())
    }

    override suspend fun uploadProfilePic(id: String, filePath: String): SaveResponse =
        suspendCoroutine { cont ->
           // val userID = RequestBody.create(MediaType.parse("multipart/form-data"), id);
            val file = File(filePath)

            val reqFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            val body =
                MultipartBody.Part.createFormData("profile_pic", file.name, reqFile);
            val userID = RequestBody.create(MediaType.parse("multipart/form-data"), id)

            val req = newUserService.postImage(userID, body)
            req.enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    cont.resume(SaveResponse(response.code()))
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Timber.e(t)
                    cont.resumeWithException(t)
                }
            })
        }


    inner class EmptyCallback<T> : Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) {}
        override fun onFailure(call: Call<T>, t: Throwable) {}
    }
}