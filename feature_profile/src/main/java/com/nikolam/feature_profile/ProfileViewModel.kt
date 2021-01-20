package com.nikolam.feature_profile

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikolam.common.navigation.ChatDeepLinkUri
import com.nikolam.common.viewmodel.BaseAction
import com.nikolam.common.viewmodel.BaseViewModel
import com.nikolam.common.viewmodel.BaseViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

internal class ProfileViewModel () : BaseViewModel<ProfileViewModel.ViewState, ProfileViewModel.Action>(ViewState()) {

    private lateinit var id : String

//    private val _profileLiveData: MutableLiveData<ProfileModel> = MutableLiveData()
//    val profileLiveData: LiveData<ProfileModel>
//    get() = _profileLiveData


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

    fun getProfile() {
        viewModelScope.launch {

        }
    }


    fun setID(id : String) {
        this.id = id
    }

//    fun navigateToChat() {
//        val uri = Uri.parse("$ChatDeepLinkUri/?id=$id")
//        navManager.navigate(uri)
//    }


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