package com.nikolam.feature_login.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.nikolam.feature_login.databinding.LoginFragmentBinding
import com.nikolam.feature_login.di.loginModule
import org.koin.android.ext.android.inject
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import timber.log.Timber
import java.util.*


class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by inject()

    private var _binding: LoginFragmentBinding? = null

    private val binding get() = _binding!!

    private val callbackManager: CallbackManager by inject()
    private val stateObserver = Observer<LoginViewModel.ViewState> {
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LoginFragmentBinding.inflate(inflater, container, false)
        val view = binding.root


        binding.loginButton.setPermissions(listOf("email"))
        binding.loginButton.fragment = this;

        // Callback registration
        binding.loginButton.registerCallback(
            callbackManager,
            object : FacebookCallback<LoginResult?> {
                override fun onSuccess(loginResult: LoginResult?) {
                    Timber.d("Success facebook login ${loginResult?.accessToken?.token}")
                    loginResult?.accessToken?.token?.let { viewModel.loginFacebooKToken(it) }
                }

                override fun onCancel() {
                    Timber.d("Canceled facebook login")
                }

                override fun onError(exception: FacebookException) {
                    Timber.d("Failed to login with facebook ${exception.localizedMessage}")
                }
            })


        viewModel.stateLiveData.observe(viewLifecycleOwner, stateObserver)

        return view
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        loadKoinModules(loginModule)
    }

    override fun onDetach() {
        super.onDetach()
        activity?.actionBar?.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        unloadKoinModules(loginModule)
    }
}