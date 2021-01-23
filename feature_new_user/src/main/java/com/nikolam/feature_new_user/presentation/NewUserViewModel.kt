package com.nikolam.feature_new_user.presentation

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.nikolam.common.navigation.MainScreenDeepLinkUri
import com.nikolam.common.navigation.NavManager
import com.nikolam.common.viewmodel.BaseAction
import com.nikolam.common.viewmodel.BaseViewModel
import com.nikolam.common.viewmodel.BaseViewState
import com.nikolam.data.db.AppRepository
import com.nikolam.data.db.models.ProfileDataModel
import com.nikolam.feature_new_user.data.model.NewProfileModel
import com.nikolam.feature_new_user.domain.NewUserRepository
import kotlinx.coroutines.*
import timber.log.Timber
import java.util.*

internal class NewUserViewModel(
    private val navManager: NavManager,
    private val appRepository: AppRepository,
    private val repo: NewUserRepository
) : BaseViewModel<NewUserViewModel.ViewState, NewUserViewModel.Action>(ViewState()) {

    private lateinit var id: String

    override fun onReduceState(viewAction: Action) = when (viewAction) {
        Action.SaveSuccess -> state.copy(
            isSuccess = true
        )
        Action.SaveLoading -> state.copy(
            isSuccess = false,
            isError = false,
            isLoading = true
        )
    }

    override fun onLoadData() {
        super.onLoadData()
        viewModelScope.launch {
            repo.createChatUser(id)
        }
    }

    fun saveProfile(profile: NewProfileModel, path: String) {

        sendAction(Action.SaveLoading)

        var profilePicUrl: String = ""

        runBlocking {
            viewModelScope.launch(Dispatchers.IO + NonCancellable) {
                Timber.d("launch ${Date()}")

                val profileAsync = async {
                    repo.saveProfile(id, profile).let { response ->
                        Timber.d(" Response code is ${response.status}")
                        if (response.status == 200) {
                            //  navigateToMainScreen(id)
                        }
                    }
                }

                val pictureAsync = async {
                    repo.uploadProfilePic(id, path).let {
                        profilePicUrl = it.image_link
                        Timber.d(it.image_link)
                    }
                }

                val deferds = listOf(pictureAsync, profileAsync)

                //Try catch


                //With contenxt MAIN THREAD
                try {
                    deferds.awaitAll()
                } catch (e: Exception) {
                    Timber.e(e)
                }

                Timber.d("SAVE PROFILE TO DB $profilePicUrl ${Date()}")

                withContext(Dispatchers.IO) {
                    appRepository.saveProfile(
                        profile = ProfileDataModel(
                            id,
                            profile.name,
                            profilePicUrl,
                            profile.bio,
                            profile.gender,
                            null
                        )
                    )
                }

                withContext(Dispatchers.Main){
                    Timber.d("Navigate to main screen ${Date()}")
                    sendAction(Action.SaveSuccess)
                }
            }
        }


    }

    fun setID(id: String) {
        this.id = id
    }

    fun navigateToMainScreen() {
        val uri = Uri.parse("$MainScreenDeepLinkUri/?id=$id")
        navManager.navigate(uri)
    }

    internal data class ViewState(
        val isSuccess: Boolean = false,
        val isLoading: Boolean = false,
        val isError: Boolean = false
    ) : BaseViewState

    internal sealed class Action : BaseAction {
        object SaveSuccess : Action()
        object SaveLoading : Action()
    }
}