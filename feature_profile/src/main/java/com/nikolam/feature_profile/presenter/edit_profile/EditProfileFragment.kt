package com.nikolam.feature_profile.presenter.edit_profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.esafirm.imagepicker.features.ImagePicker
import com.esafirm.imagepicker.features.ReturnMode
import com.nikolam.feature_profile.data.models.SaveProfileModel
import com.nikolam.feature_profile.di.profileModule
import com.nikolam.feature_profile.databinding.EditProfileFragmentBinding
import com.nikolam.feature_profile.presenter.profile.ProfileViewModel
import org.koin.android.ext.android.inject
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import timber.log.Timber


class EditProfileFragment : Fragment() {

    private val viewModel: EditProfileViewModel by inject()

    private var _binding: EditProfileFragmentBinding? = null

    private val binding get() = _binding!!

    private val stateObserver = Observer<EditProfileViewModel.ViewState> {
        Timber.d(it.profile.toString())
        if (it.isSuccess) {
            binding.nameEditText.hint = it.profile?.name
            binding.nameEditText.hint = it.profile?.bio
            Glide.with(binding.profileImage).load(it.profile?.profilePictureUrl).into(binding.profileImage)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = EditProfileFragmentBinding.inflate(inflater, container, false)
        val view = binding.root

        arguments?.let {
            val id = it.getString("id")
            viewModel.setID(id ?: "")
            Timber.d("The id is $id")
        }

        setupProfileImagePicker()

        binding.cancelButton.setOnClickListener {
           viewModel.goBack( )
        }

        binding.saveChangesButton.setOnClickListener {
            viewModel.saveProfile(
                SaveProfileModel(name = binding.nameEditText.text.toString(),
bio = binding.bioEditText.text.toString(), gender = "", profilePictureUrl = ""
                )
            )
        }

        viewModel.stateLiveData.observe(viewLifecycleOwner, stateObserver)

        viewModel.getProfile()

        return view
    }

    private fun setupProfileImagePicker() {
        binding.profileImage.setOnClickListener {
            ImagePicker.create(this)
                    .returnMode(ReturnMode.ALL) // set whether pick and / or camera action should return immediate result or not.
                    .toolbarFolderTitle("Gallery") // folder selection title
                    .toolbarImageTitle("Tap an image to select") // image selection title
                    .includeVideo(false) // Show video on image picker
                    .single() // single mode
                    .imageDirectory("Camera") // directory name for captured image  ("Camera" folder by default)
                    .enableLog(true) // disabling log
                    .start(); // start image picker activity with request code
        }
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