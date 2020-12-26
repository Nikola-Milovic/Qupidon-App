package com.nikolam.feature_login.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.nikolam.feature_login.databinding.LoginFragmentBinding
import org.koin.android.ext.android.inject

class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by inject()

    private var _binding: LoginFragmentBinding? = null

    private val binding get() = _binding!!


    private val stateObserver = Observer<LoginViewModel.ViewState> {
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LoginFragmentBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel.stateLiveData.observe(viewLifecycleOwner, stateObserver)

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        //  loadKoinModules(feedModule)
    }

    override fun onDetach() {
        super.onDetach()
        activity?.actionBar?.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        //  unloadKoinModules(feedModule)
    }
}