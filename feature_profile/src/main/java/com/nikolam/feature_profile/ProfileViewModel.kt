package com.nikolam.feature_profile

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.nikolam.common.navigation.NavManager
import com.nikolam.common.viewmodel.BaseAction
import com.nikolam.common.viewmodel.BaseViewModel
import com.nikolam.common.viewmodel.BaseViewState
import com.nikolam.domain.GetProfileUseCase
import com.nikolam.domain.models.ProfileDomainModel
import kotlinx.coroutines.launch

internal class ProfileViewModel(
    private val navManager: NavManager,
    private val getProfileUseCase: GetProfileUseCase

) : BaseViewModel<ProfileViewModel.ViewState, ProfileViewModel.Action>(ViewState()) {

    private lateinit var id: String

//    private val _profileLiveData: MutableLiveData<ProfileModel> = MutableLiveData()
//    val profileLiveData: LiveData<ProfileModel>
//    get() = _profileLiveData


    override fun onReduceState(viewAction: Action) = when (viewAction) {
        is Action.LoadingProfileSuccess -> state.copy(
            isError = false,
            isSuccess = true,
            isLoading = false,
            profile = viewAction.profile
        )
        is Action.LoadingProfileFailure -> state.copy(
            isError = true,
            isSuccess = false,
            isLoading = false
        )
    }

    fun getProfile() {
        viewModelScope.launch {
            getProfileUseCase.execute(id).let {
                when (it) {
                    is GetProfileUseCase.Result.Success -> {
                        sendAction(Action.LoadingProfileSuccess(it.profile))
                    }
                    is GetProfileUseCase.Result.Error -> {
                    }
                }
            }
        }
    }

    fun setID(id: String) {
        this.id = id
    }

    fun navigateToEditProfile() {
        val uri = Uri.parse("qupidon://editProfile")
        navManager.navigate(uri)
    }


    internal data class ViewState(
        val isSuccess: Boolean = false,
        val isLoading: Boolean = false,
        val isError: Boolean = false,
        val profile: ProfileDomainModel? = null
    ) : BaseViewState

    internal sealed class Action : BaseAction {
        class LoadingProfileSuccess(val profile: ProfileDomainModel) : Action()
        object LoadingProfileFailure : Action()
    }
}