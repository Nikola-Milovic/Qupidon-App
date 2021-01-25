package com.nikolam.feature_profile.presenter.profile

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.nikolam.common.navigation.NavManager
import com.nikolam.common.viewmodel.BaseAction
import com.nikolam.common.viewmodel.BaseViewModel
import com.nikolam.common.viewmodel.BaseViewState
import com.nikolam.feature_profile.data.models.SaveProfileModel
import com.nikolam.feature_profile.domain.ProfileRepository
import com.nikolam.feature_profile.domain.models.ProfileDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal class ProfileViewModel(
    private val navManager: NavManager,
    private val repository: ProfileRepository

) : BaseViewModel<ProfileViewModel.ViewState, ProfileViewModel.Action>(ViewState()) {

    private lateinit var id: String

//    private val _profileLiveData: MutableLiveData<ProfileModel> = MutableLiveData()
//    val profileLiveData: LiveData<ProfileModel>
//    get() = _profileLiveData

    private lateinit var currentProfile : ProfileDomainModel


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
            withContext(Dispatchers.IO) {
                repository.getProfile(
                    id
                )
            }.let { profile ->
                //TODO add error handling
                currentProfile = profile
                sendAction(Action.LoadingProfileSuccess(profile))
            }
        }
    }

    fun goBack(){
        navManager.popBackStack()
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