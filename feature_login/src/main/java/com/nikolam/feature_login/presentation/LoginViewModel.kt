package com.nikolam.feature_login.presentation

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.nikolam.common.navigation.MainScreenDeepLinkUri
import com.nikolam.common.navigation.NavManager
import com.nikolam.common.navigation.NewUserDeepLinkUri
import com.nikolam.common.viewmodel.BaseAction
import com.nikolam.common.viewmodel.BaseViewModel
import com.nikolam.common.viewmodel.BaseViewState
import com.nikolam.feature_login.domain.FacebookLoginUseCase
import kotlinx.coroutines.launch
import timber.log.Timber

internal class LoginViewModel(
    private val navManager: NavManager,
    private val facebookLoginUseCase: FacebookLoginUseCase
) : BaseViewModel<LoginViewModel.ViewState, LoginViewModel.Action>(ViewState()) {

    override fun onReduceState(viewAction: Action) = when (viewAction) {
        Action.LoginSucess -> state
    }

    fun loginFacebooKToken(token: String) {
        viewModelScope.launch {
            facebookLoginUseCase.execute(token).let {
                when (it) {
                    is FacebookLoginUseCase.Result.Success -> {
                        Timber.d(it.response.toString())
                        if (it.response.new) {
                            navigateToNewUser(it.response.id)
                        } else {
                            navigateToMainScreen(it.response.id)
                        }
                    }
                }
            }
        }
    }

    private fun navigateToMainScreen(id : String) {
        val uri = Uri.parse("$MainScreenDeepLinkUri/?id=$id")
        navManager.navigate(uri)
    }

    private fun navigateToNewUser(id : String) {
        val uri = Uri.parse("$NewUserDeepLinkUri/?id=$id")
        navManager.navigate(uri)
    }

    internal data class ViewState(
        val isSuccess: Boolean = false,
        val isLoading: Boolean = false,
        val isError: Boolean = false
    ) : BaseViewState

    internal sealed class Action : BaseAction {
        object LoginSucess : Action()
    }
}