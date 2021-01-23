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
import kotlinx.coroutines.launch
import timber.log.Timber

internal class NewUserViewModel(
    private val navManager: NavManager,
    private val appRepository: AppRepository,
    private val repo: NewUserRepository
) : BaseViewModel<NewUserViewModel.ViewState, NewUserViewModel.Action>(ViewState()) {

    private lateinit var id: String

    override fun onReduceState(viewAction: Action) = when (viewAction) {
        Action.SaveSuccess -> state
    }

    override fun onLoadData() {
        super.onLoadData()
        viewModelScope.launch {
            repo.createChatUser(id)
        }
    }

    fun saveProfile(profile: NewProfileModel, path: String) {
        viewModelScope.launch {
            repo.saveProfile(id, profile).let { response ->
                if (response.status == 200) {
                    navigateToMainScreen(id)
                }
            }
        }

    viewModelScope.launch {
        repo.uploadProfilePic(id, path).let {
            Timber.d(it.toString())
        }
    }

    appRepository.saveProfile(profile = ProfileDataModel(id, profile.name, null, profile.bio, profile.gender, null))
}

fun setID(id: String) {
    this.id = id
}

fun navigateToMainScreen(id: String) {
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
}
}