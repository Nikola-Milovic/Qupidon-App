package com.nikolam.feature_main_screen.presentation

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nikolam.data.messaging.MessagingManager
import com.nikolam.common.navigation.ChatDeepLinkUri
import com.nikolam.common.navigation.NavManager
import com.nikolam.common.navigation.ProfileDeepLinkUri
import com.nikolam.common.viewmodel.BaseAction
import com.nikolam.common.viewmodel.BaseViewModel
import com.nikolam.common.viewmodel.BaseViewState
import com.nikolam.feature_main_screen.data.model.ProfileModel
import com.nikolam.feature_main_screen.domain.GetMatchesUseCase
import com.nikolam.feature_main_screen.domain.GetProfilesUseCase
import com.nikolam.feature_main_screen.domain.InteractionUseCase
import com.nikolam.feature_main_screen.domain.SaveTokenUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

internal class MainViewModel  (private val navManager: NavManager,
                               private val getProfilesUseCase: GetProfilesUseCase,
                               private val interactionUseCase: InteractionUseCase,
                               private val getMatchesUseCase: GetMatchesUseCase,
                               private val saveTokenUseCase: SaveTokenUseCase,
                               private val messagingManager: com.nikolam.data.messaging.MessagingManager
) : BaseViewModel<MainViewModel.ViewState, MainViewModel.Action>(ViewState()) {

    private lateinit var id : String

    private val _profileLiveData: MutableLiveData<ProfileModel> = MutableLiveData()
    val profileLiveData: LiveData<ProfileModel>
        get() = _profileLiveData

    private val profiles = arrayListOf<ProfileModel>()
    private var currentProfileIndex = 0

    override fun onReduceState(viewAction: Action) = when (viewAction) {
        is Action.LoadingMatchesSuccess ->state.copy(
                isError = false,
                isSuccess = true,
                isLoading = false,
        )
        is Action.LoadingMatchesFailure -> state.copy(
                isError = true,
                isSuccess = false,
                isLoading = false
        )
    }

    fun getProfiles() {
        viewModelScope.launch {
            getProfilesUseCase.execute(id).let {
                when(it) {
                    is GetProfilesUseCase.Result.Success -> {
                        if (it.users.isEmpty()) return@let
                        Timber.d(it.users.toString())
                        sendAction(Action.LoadingMatchesSuccess)
                        profiles.addAll(it.users)
                        it.users.forEach {
                            it.profilePicUrl = "https://qupidon-images.s3.eu-central-1.amazonaws.com/${it.profilePicUrl}"
                            profiles.add(it)
                        }
                        val profile = profiles[0]
                        _profileLiveData.postValue(profile)
                    }
                    is GetProfilesUseCase.Result.Error -> sendAction(Action.LoadingMatchesFailure)
                }
            }
        }
    }

    fun getMatches() {
        viewModelScope.launch {
            getMatchesUseCase.execute(id)
        }
    }

    fun saveFCMToken(token : String) {
        viewModelScope.launch {
            saveTokenUseCase.execute(id, token)
        }
    }

    fun likeUser() {
        val likedID = profiles[currentProfileIndex].id
        viewModelScope.launch(Dispatchers.IO) {
            interactionUseCase.likeUser(id, likedID)
        }
        moveToNextUser()
    }

    fun rejectUser() {
        viewModelScope.launch {
            interactionUseCase.rejectUser(id, profileLiveData.value!!.id)
            moveToNextUser()
        }
    }

    fun setID(id : String) {
        this.id = id
        messagingManager.connect(id)
    }

    fun navigateToChat() {
        val uri = Uri.parse("$ChatDeepLinkUri/?id=$id")
        navManager.navigate(uri)
    }

    fun navigateToProfile() {
        val uri = Uri.parse("$ProfileDeepLinkUri/?id=$id")
        navManager.navigate(uri)
    }

    private fun moveToNextUser(){
        currentProfileIndex++
        if (currentProfileIndex >= profiles.size){
            return
        }
        _profileLiveData.value = profiles[currentProfileIndex]
    }

    internal data class ViewState(
            val isSuccess: Boolean = false,
            val isLoading: Boolean = false,
            val isError: Boolean = false,
    ) : BaseViewState

    internal sealed class Action : BaseAction {
        object LoadingMatchesSuccess : Action()
        object LoadingMatchesFailure : Action()
    }
}