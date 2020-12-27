package com.nikolam.feature_new_user.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.facebook.CallbackManager
import com.nikolam.feature_new_user.data.model.NewProfileModel
import com.nikolam.feature_new_user.databinding.NewUserFragmentBinding
import com.nikolam.feature_new_user.di.newUserModule
import org.koin.android.ext.android.inject
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import timber.log.Timber

class NewUserFragment : Fragment() {

    private val viewModel: NewUserViewModel by inject()

    private var _binding: NewUserFragmentBinding? = null

    private val binding get() = _binding!!

    private val stateObserver = Observer<NewUserViewModel.ViewState> {
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = NewUserFragmentBinding.inflate(inflater, container, false)
        val view = binding.root

        // TODO load the basic data from the db first
        arguments?.let {
            val id = it.getString("id")
            viewModel.setID(id ?: "")
            Timber.d("The id is $id")
        }

        // TODO add gender spinner
        binding.continueButton.setOnClickListener {
            viewModel.saveProfile(NewProfileModel(
                name = binding.nameEditText.text.toString(),
                bio = binding.bioEditText.text.toString(),
                gender = ""
            ))
        }

        viewModel.stateLiveData.observe(viewLifecycleOwner, stateObserver)

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        loadKoinModules(newUserModule)
    }

    override fun onDetach() {
        super.onDetach()
        activity?.actionBar?.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        unloadKoinModules(newUserModule)
    }
}