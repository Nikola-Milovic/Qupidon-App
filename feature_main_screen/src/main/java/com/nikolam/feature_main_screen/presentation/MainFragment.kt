package com.nikolam.feature_main_screen.presentation

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.graphics.Picture
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

    private lateinit var picturesAdapter : PicturePagerAdapter

    private val stateObserver = Observer<MainViewModel.ViewState> {
        if (it.isSuccess){
           viewModel.profileLiveData.observe(this, Observer { profile ->
               Timber.d(it.toString())

               picturesAdapter.addImages(arrayListOf("https://picsum.photos/300/300",
                       "https://picsum.photos/300/300",
                       "https://picsum.photos/300/300"))

               binding.interestsTag.tags = listOf("Horses", "Running", "Music", "Dancing")

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

        activity?.getSharedPreferences("FirebaseToken", MODE_PRIVATE)?.apply {
            val token = this.getString("token", "")
            Timber.d("Token from preference is $token")
            if (!token.isNullOrEmpty()){
                viewModel.saveFCMToken(token)
            }
        }

        picturesAdapter = PicturePagerAdapter(requireContext())

        binding.imagesViewPager.adapter = picturesAdapter

        binding.matchAcceptButton.setOnClickListener {
            viewModel.likeUser()
        }

        binding.matchRejectButton.setOnClickListener {
            viewModel.rejectUser()
        }

        binding.preferences.setOnClickListener{
            viewModel.navigateToChat()
        }

        binding.profileButton.setOnClickListener{
            viewModel.navigateToProfile()
        }

        viewModel.stateLiveData.observe(viewLifecycleOwner, stateObserver)

        viewModel.getProfiles()

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