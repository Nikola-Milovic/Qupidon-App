package com.nikolam.feature_messages.presentation.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nikolam.common.messaging.MessagingManager
import com.nikolam.common.viewmodel.BaseAction
import com.nikolam.common.viewmodel.BaseViewModel
import com.nikolam.common.viewmodel.BaseViewState
import com.nikolam.feature_messages.domain.models.MessageDomainModel
import kotlinx.coroutines.launch

internal class ChatViewModel (
    private val messageManager: MessagingManager
        ) : BaseViewModel<ChatViewModel.ViewState, ChatViewModel.Action>(ViewState()) {

    private lateinit var id : String

    private val _messageLiveData: MutableLiveData<ArrayList<MessageDomainModel>> = MutableLiveData()
    val messageLiveData: LiveData<ArrayList<MessageDomainModel>>
        get() = _messageLiveData


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

    fun setID(id : String) {
        this.id = id
    }

    fun getMessages() {
        viewModelScope.launch {
        }
    }

    fun sendMessage(text : String){
        messageManager.sendMessage(id , text)
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