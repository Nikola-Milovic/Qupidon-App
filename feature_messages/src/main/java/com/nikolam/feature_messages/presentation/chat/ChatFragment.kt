package com.nikolam.feature_messages.presentation.chat

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.nikolam.common.extensions.hideKeyboard
import com.nikolam.feature_messages.databinding.ChatFragmentBinding
import com.nikolam.feature_messages.di.chatModule
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
        if (it.isSuccess){
         binding.chatNameTextView.text = it.profile?.name
         Glide.with(binding.chatProfilePicImageView.context).load(it.profile?.profilePicture)
                 .into(binding.chatProfilePicImageView)
        }
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
            binding.messageEditText.text.clear()
            hideKeyboardFrom(requireContext(), binding.messageEditText)
        }

        binding.goBackImageView.setOnClickListener {
            viewModel.goBack()
        }

        adapter = ChatAdapter()

        binding.messagesRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        binding.messagesRecyclerView.adapter = adapter

        viewModel.stateLiveData.observe(viewLifecycleOwner, stateObserver)

        viewModel.messageLiveData.observe(viewLifecycleOwner, {
            adapter.newData(it)
        })

        viewModel.getMessages()
        viewModel.getProfile()

        return view
    }

    private fun hideKeyboardFrom(context: Context, view: View) {
        val imm: InputMethodManager = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
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