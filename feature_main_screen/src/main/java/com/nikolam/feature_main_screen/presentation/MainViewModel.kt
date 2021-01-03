package com.nikolam.feature_main_screen.presentation

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikolam.common.navigation.MainScreenDeepLinkUri
import com.nikolam.common.navigation.NavManager
import com.nikolam.common.viewmodel.BaseAction
import com.nikolam.common.viewmodel.BaseViewModel
import com.nikolam.common.viewmodel.BaseViewState
import kotlinx.coroutines.launch

internal class MainViewModel  ( private val navManager: NavManager
) : BaseViewModel<MainViewModel.ViewState, MainViewModel.Action>(ViewState()) {

    private lateinit var id : String

    override fun onReduceState(viewAction: Action) = when (viewAction) {
        Action.SaveSucess -> state
    }

    fun save() {
        viewModelScope.launch {

        }
    }

    fun setID(id : String) {
        this.id = id
    }

    fun navigate(id: String) {
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