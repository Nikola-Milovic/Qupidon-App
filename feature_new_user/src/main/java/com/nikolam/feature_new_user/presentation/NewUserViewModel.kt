package com.nikolam.feature_new_user.presentation

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.nikolam.common.navigation.MainScreenDeepLinkUri
import com.nikolam.common.navigation.NavManager
import com.nikolam.common.navigation.NewUserDeepLinkUri
import com.nikolam.common.viewmodel.BaseAction
import com.nikolam.common.viewmodel.BaseViewModel
import com.nikolam.common.viewmodel.BaseViewState
import com.nikolam.feature_new_user.data.model.NewProfileModel
import com.nikolam.feature_new_user.domain.SaveProfileUseCase
import kotlinx.coroutines.launch

internal class NewUserViewModel(
    private val navManager: NavManager,
    private val saveProfileUseCase: SaveProfileUseCase
) : BaseViewModel<NewUserViewModel.ViewState, NewUserViewModel.Action>(ViewState()) {

    private lateinit var id : String

    override fun onReduceState(viewAction: Action) = when (viewAction) {
        Action.SaveSucess -> state
    }

    fun saveProfile(profile : NewProfileModel) {
        viewModelScope.launch {
            saveProfileUseCase.execute(id, profile).let {
                when (it) {
                    is SaveProfileUseCase.Result.Success -> {
                        if (it.response.status == 200) {
                            navigateToMainScreen(id)
                        }
                    }
                }
            }
        }
    }

    fun setID(id : String) {
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
        object SaveSucess : Action()
    }
}