package com.nikolam.feature_login.presentation

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikolam.common.navigation.NavManager
import com.nikolam.common.viewmodel.BaseAction
import com.nikolam.common.viewmodel.BaseViewModel
import com.nikolam.common.viewmodel.BaseViewState
import kotlinx.coroutines.launch

internal class LoginViewModel(private val navManager: NavManager) :
    BaseViewModel<LoginViewModel.ViewState, LoginViewModel.Action>(ViewState()) {

    override fun onReduceState(viewAction: Action) = when (viewAction) {
        Action.LoginSucess -> state
    }

    fun get() {
        viewModelScope.launch {

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