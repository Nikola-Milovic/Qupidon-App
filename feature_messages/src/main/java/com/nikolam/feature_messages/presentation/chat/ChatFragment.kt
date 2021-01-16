package com.nikolam.feature_messages.presentation.chat

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.nikolam.feature_messages.R
import com.nikolam.feature_messages.databinding.ChatFragmentBinding
import com.nikolam.feature_messages.databinding.ChatListFragmentBinding
import com.nikolam.feature_messages.di.chatModule
import com.nikolam.feature_messages.domain.models.MessageDomainModel
import com.nikolam.feature_messages.presentation.chat_list.ChatListAdapter
import com.nikolam.feature_messages.presentation.chat_list.ChatListViewModel
import org.koin.android.ext.android.inject
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import timber.log.Timber

class ChatFragment : Fragment() {

    private val viewModel: ChatViewModel by inject()

    private var _binding: ChatFragmentBinding? = null

    private val binding get() = _binding!!

    private lateinit var adapter : ChatAdapter

    private val stateObserver = Observer<ChatViewModel.ViewState> {
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ChatFragmentBinding.inflate(inflater, container, false)
        val view = binding.root

        arguments?.let {
            val id = it.getString("id")
            viewModel.setID(id ?: "")
            Timber.d("The chat id is $id")
        }

        binding.sendMessageButton.setOnClickListener {
            viewModel.sendMessage(binding.messageEditText.text.toString())
        }

        adapter = ChatAdapter()

        binding.messagesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.messagesRecyclerView.addItemDecoration(
                DividerItemDecoration(
                        requireContext(),
                        DividerItemDecoration.VERTICAL
                )
        )

        binding.messagesRecyclerView.adapter = adapter


        viewModel.stateLiveData.observe(viewLifecycleOwner, stateObserver)

        viewModel.messageLiveData.observe(viewLifecycleOwner, {
            adapter.newData(it)
        })

        viewModel.getMessages()

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        loadKoinModules(chatModule)
    }

    override fun onDetach() {
        super.onDetach()
        activity?.actionBar?.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        unloadKoinModules(chatModule)
    }
}