package com.nikolam.feature_messages.presentation.chat_list

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.nikolam.feature_messages.R
import com.nikolam.feature_messages.databinding.ChatListFragmentBinding
import com.nikolam.feature_messages.di.chatListModule
import com.nikolam.feature_messages.di.repoModule
import org.koin.android.ext.android.inject
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import timber.log.Timber

class ChatListFragment : Fragment() {

    private val viewModel: ChatListViewModel by inject()

    private var _binding: ChatListFragmentBinding? = null

    private val binding get() = _binding!!

    private val stateObserver = Observer<ChatListViewModel.ViewState> {
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ChatListFragmentBinding.inflate(inflater, container, false)
        val view = binding.root

//        arguments?.let {
//            val id = it.getString("id")
//            viewModel.setID(id ?: "")
//            Timber.d("The id is $id")
//        }

        viewModel.stateLiveData.observe(viewLifecycleOwner, stateObserver)

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        loadKoinModules(arrayListOf(chatListModule, repoModule))
    }

    override fun onDetach() {
        super.onDetach()
        activity?.actionBar?.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        unloadKoinModules(chatListModule)
    }
}