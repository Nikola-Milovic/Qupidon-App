package com.nikolam.feature_login.presentation

import androidx.lifecycle.viewModelScope
import com.nikolam.common.navigation.NavManager
import com.nikolam.common.viewmodel.BaseAction
import com.nikolam.common.viewmodel.BaseViewModel
import com.nikolam.common.viewmodel.BaseViewState
import com.nikolam.feature_login.domain.FacebookLoginUseCase
import kotlinx.coroutines.launch
import timber.log.Timber

internal class LoginViewModel(private val navManager: NavManager,
                              private val facebookLoginUseCase: FacebookLoginUseCase
) :
        BaseViewModel<LoginViewModel.ViewState, LoginViewModel.Action>(ViewState()) {

    override fun onReduceState(viewAction: Action) = when (viewAction) {
        Action.LoginSucess -> state
    }

    fun loginFacebooKToken(token : String) {
        viewModelScope.launch {
            facebookLoginUseCase.execute(token).let {
                Timber.d(it.toString())
            }
        }
    }

    fun navigateTo() {
//        val uri = Uri.parse()
//        navManager.navigate(uri)
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