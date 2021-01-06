package com.nikolam.feature_main_screen.presentation

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikolam.common.navigation.MainScreenDeepLinkUri
import com.nikolam.common.navigation.NavManager
import com.nikolam.common.viewmodel.BaseAction
import com.nikolam.common.viewmodel.BaseViewModel
import com.nikolam.common.viewmodel.BaseViewState
import com.nikolam.feature_main_screen.data.model.ProfileModel
import com.nikolam.feature_main_screen.domain.GetMatchesUseCase
import kotlinx.coroutines.launch

internal class MainViewModel  ( private val navManager: NavManager,
                                private val getMatchesUseCase: GetMatchesUseCase
) : BaseViewModel<MainViewModel.ViewState, MainViewModel.Action>(ViewState()) {

    private lateinit var id : String

    override fun onReduceState(viewAction: Action) = when (viewAction) {
        is Action.LoadingMatchesSuccess ->state.copy(
                isError = false,
                isSuccess = true,
                isLoading = false,
                profiles = viewAction.matches
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
                    is GetMatchesUseCase.Result.Success -> sendAction(Action.LoadingMatchesSuccess(it.users))
                    is GetMatchesUseCase.Result.Error -> sendAction(Action.LoadingMatchesFailure)
                }
            }
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
            val isError: Boolean = false,
            val profiles : ArrayList<ProfileModel> = arrayListOf()
    ) : BaseViewState

    internal sealed class Action : BaseAction {
        class LoadingMatchesSuccess(val matches : ArrayList<ProfileModel>) : Action()
        object LoadingMatchesFailure : Action()
    }
}