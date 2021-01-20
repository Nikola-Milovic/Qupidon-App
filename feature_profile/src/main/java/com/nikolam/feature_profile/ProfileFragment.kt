package com.nikolam.feature_profile

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.nikolam.di.profileModule
import com.nikolam.feature_profile.databinding.ProfileFragmentBinding
import org.koin.android.ext.android.inject
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import timber.log.Timber

class ProfileFragment : Fragment() {

    private val viewModel: ProfileViewModel by inject()

    private var _binding: ProfileFragmentBinding? = null

    private val binding get() = _binding!!

    private val stateObserver = Observer<ProfileViewModel.ViewState> {
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ProfileFragmentBinding.inflate(inflater, container, false)
        val view = binding.root

        // TODO load the basic data from the db first
        arguments?.let {
            val id = it.getString("id")
          //  viewModel.setID(id ?: "")
            Timber.d("The id is $id")
        }

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        loadKoinModules(profileModule)
    }

    override fun onDetach() {
        super.onDetach()
        activity?.actionBar?.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        unloadKoinModules(profileModule)
    }
}