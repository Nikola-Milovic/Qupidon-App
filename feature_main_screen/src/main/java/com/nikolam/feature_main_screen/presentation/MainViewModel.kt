package com.nikolam.feature_main_screen.presentation

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikolam.common.messaging.MessagingManager
import com.nikolam.common.navigation.MainScreenDeepLinkUri
import com.nikolam.common.navigation.NavManager
import com.nikolam.common.viewmodel.BaseAction
import com.nikolam.common.viewmodel.BaseViewModel
import com.nikolam.common.viewmodel.BaseViewState
import com.nikolam.feature_main_screen.data.model.ProfileModel
import com.nikolam.feature_main_screen.domain.GetMatchesUseCase
import com.nikolam.feature_main_screen.domain.InteractionUseCase
import kotlinx.coroutines.launch

internal class MainViewModel  ( private val navManager: NavManager,
                                private val getMatchesUseCase: GetMatchesUseCase,
                                private val interactionUseCase: InteractionUseCase,
                                private val messagingManager: MessagingManager
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

    fun getMatches() {
        viewModelScope.launch {
            getMatchesUseCase.execute(id).let {
                when(it) {
                    is GetMatchesUseCase.Result.Success -> {
                        sendAction(Action.LoadingMatchesSuccess)
                        profiles.addAll(it.users)
                        val profile = profiles[0]
                        _profileLiveData.postValue(profile)
                    }
                    is GetMatchesUseCase.Result.Error -> sendAction(Action.LoadingMatchesFailure)
                }
            }
        }
    }

    fun likeUser() {
        viewModelScope.launch {
            interactionUseCase.likeUser(id, profileLiveData.value!!.id)
            moveToNextUser()
        }
    }

    fun rejectUser() {
        viewModelScope.launch {
            interactionUseCase.rejectUser(id, profileLiveData.value!!.id)
            moveToNextUser()
        }
    }

    fun setID(id : String) {
        this.id = id
        messagingManager.connect()
    }

    fun navigate(id: String) {
        val uri = Uri.parse("$MainScreenDeepLinkUri/?id=$id")
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