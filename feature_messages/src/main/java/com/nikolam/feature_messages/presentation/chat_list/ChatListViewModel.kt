package com.nikolam.feature_messages.presentation.chat_list

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikolam.common.navigation.MainScreenDeepLinkUri
import com.nikolam.common.viewmodel.BaseAction
import com.nikolam.common.viewmodel.BaseViewModel
import com.nikolam.common.viewmodel.BaseViewState
import com.nikolam.feature_messages.domain.MessageRepository
import kotlinx.coroutines.launch

internal class ChatListViewModel (
        private val repository : MessageRepository
        ) : BaseViewModel<ChatListViewModel.ViewState, ChatListViewModel.Action>(ViewState()) {

    private lateinit var id : String

    private val _profileLiveData: MutableLiveData<String> = MutableLiveData()
    val profileLiveData: LiveData<String>
    get() = _profileLiveData

    private val profiles = arrayListOf<String>()
    private var currentProfileIndex = 0

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

    fun getChats() {
        viewModelScope.launch {
        }
    }

//    fun navigate(id: String) {
//        val uri = Uri.parse("$MainScreenDeepLinkUri/?id=$id")
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