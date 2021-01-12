package com.nikolam.feature_messages.presentation.chat_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nikolam.common.viewmodel.BaseAction
import com.nikolam.common.viewmodel.BaseViewModel
import com.nikolam.common.viewmodel.BaseViewState
import com.nikolam.feature_messages.domain.GetUsersUseCase
import com.nikolam.feature_messages.domain.models.UserDomainModel
import kotlinx.coroutines.launch

internal class ChatListViewModel(
    private val getUsersUseCase: GetUsersUseCase
) : BaseViewModel<ChatListViewModel.ViewState, ChatListViewModel.Action>(ViewState()) {

    private lateinit var id: String

    private val _profileLiveData: MutableLiveData<String> = MutableLiveData()
    val profileLiveData: LiveData<String>
        get() = _profileLiveData

    private val profiles = arrayListOf<String>()
    private var currentProfileIndex = 0

    override fun onReduceState(viewAction: Action) = when (viewAction) {
        is Action.LoadingChatsSuccess -> state.copy(
            isError = false,
            isSuccess = true,
            isLoading = false,
            chats = viewAction.chats
        )
    }

    fun getChats() {
        viewModelScope.launch {
            getUsersUseCase.execute().let {
                when (it) {
                    is GetUsersUseCase.Result.Success -> sendAction(Action.LoadingChatsSuccess(it.response))
                }

            }
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
        val chats: ArrayList<UserDomainModel> = arrayListOf()
    ) : BaseViewState

    internal sealed class Action : BaseAction {
        class LoadingChatsSuccess(val chats: ArrayList<UserDomainModel>) : Action()
    }
}