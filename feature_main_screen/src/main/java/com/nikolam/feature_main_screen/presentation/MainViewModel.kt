package com.nikolam.feature_main_screen.presentation

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nikolam.common.navigation.ChatDeepLinkUri
import com.nikolam.common.navigation.NavManager
import com.nikolam.common.navigation.ProfileDeepLinkUri
import com.nikolam.common.viewmodel.BaseAction
import com.nikolam.common.viewmodel.BaseViewModel
import com.nikolam.common.viewmodel.BaseViewState
import com.nikolam.data.messaging.MessagingManager
import com.nikolam.feature_main_screen.data.model.ProfileModel
import com.nikolam.feature_main_screen.domain.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

internal class MainViewModel(private val navManager: NavManager,
                             private val messagingManager: MessagingManager,
                             private val repository: MainRepository
) : BaseViewModel<MainViewModel.ViewState, MainViewModel.Action>(ViewState()) {

    private lateinit var id: String

    private val _profileLiveData: MutableLiveData<ProfileModel> = MutableLiveData()
    val profileLiveData: LiveData<ProfileModel>
        get() = _profileLiveData

    private val profiles = arrayListOf<ProfileModel>()
    private var currentProfileIndex = 0

    override fun onReduceState(viewAction: Action) = when (viewAction) {
        is Action.LoadingMatchesSuccess -> state.copy(
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
            //TODO add errors
            repository.getProfiles(id).let {
                if (it.isEmpty()) return@let
                Timber.d(it.toString())
                sendAction(Action.LoadingMatchesSuccess)
                profiles.addAll(it)
                it.forEach {
                    it.profilePicUrl = "https://qupidon-images.s3.eu-central-1.amazonaws.com/${it.profilePicUrl}"
                    profiles.add(it)
                }
                val profile = profiles[0]
                _profileLiveData.postValue(profile)
            }
            // sendAction(Action.LoadingMatchesFailure)

        }
}

fun saveFCMToken(token: String) {
    viewModelScope.launch {
        repository.saveFCMToken(id, token)
    }
}

fun likeUser() {
    val likedID = profiles[currentProfileIndex].id
    viewModelScope.launch(Dispatchers.IO) {
        repository.likeUser(id, likedID)
    }
    moveToNextUser()
}

fun rejectUser() {
    viewModelScope.launch {
        repository.rejectUser(id, profiles[currentProfileIndex].id)
        moveToNextUser()
    }
}

fun setID(id: String) {
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

private fun moveToNextUser() {
    currentProfileIndex++
    if (currentProfileIndex >= profiles.size) {
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