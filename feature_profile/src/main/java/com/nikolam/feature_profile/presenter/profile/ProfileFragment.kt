package com.nikolam.feature_profile.presenter.profile

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.nikolam.feature_profile.di.profileModule
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
        Timber.d(it.profile.toString())
        if (it.isSuccess){
            binding.nameTextView.text = it.profile?.name
            binding.bioTextView.text = it.profile?.bio
            Glide.with(binding.profileImage).load(it.profile?.profilePictureUrl).into(binding.profileImage)
            Timber.d(it.profile?.profilePictureUrl)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ProfileFragmentBinding.inflate(inflater, container, false)
        val view = binding.root

        arguments?.let {
            val id = it.getString("id")
            viewModel.setID(id ?: "")
            Timber.d("The id is $id")
        }

        binding.editProfileButton.setOnClickListener {
            viewModel.navigateToEditProfile()
        }

        viewModel.stateLiveData.observe(viewLifecycleOwner, stateObserver)

        viewModel.getProfile()

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        loadKoinModules(profileModule)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        unloadKoinModules(profileModule)
    }
}