package com.nikolam.feature_main_screen.presentation

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.nikolam.feature_main_screen.databinding.MainFragmentBinding
import com.nikolam.feature_main_screen.di.mainScreenModule
import org.koin.android.ext.android.inject
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import timber.log.Timber

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by inject()

    private var _binding: MainFragmentBinding? = null

    private val binding get() = _binding!!

    private val stateObserver = Observer<MainViewModel.ViewState> {
        if (it.isSuccess){
           viewModel.profileLiveData.observe(this, Observer { profile ->
               Timber.d(it.toString())
               Glide.with(binding.profileImage)
                       .load(profile.profilePicUrl)
                       .apply(RequestOptions.centerCropTransform())
                       .into(binding.profileImage)

               binding.nameTextView.text = profile.name
               binding.bioTextView.text = profile.bio
           })
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        val view = binding.root

        // TODO load the basic data from the db first
        arguments?.let {
            val id = it.getString("id")
            viewModel.setID(id ?: "")
            Timber.d("The id is $id")
        }

        binding.matchAcceptButton.setOnClickListener {
            viewModel.likeUser()
        }

        binding.matchRejectButton.setOnClickListener {
            viewModel.rejectUser()
        }

        binding.preferences.setOnClickListener{
            viewModel.navigateToChat()
        }

        viewModel.stateLiveData.observe(viewLifecycleOwner, stateObserver)

        viewModel.getProfiles()

        viewModel.getMatches()

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        loadKoinModules(mainScreenModule)
    }

    override fun onDetach() {
        super.onDetach()
        activity?.actionBar?.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        unloadKoinModules(mainScreenModule)
    }
}