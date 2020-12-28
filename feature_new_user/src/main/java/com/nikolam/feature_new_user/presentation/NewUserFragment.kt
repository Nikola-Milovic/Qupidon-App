package com.nikolam.feature_new_user.presentation

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.nikolam.feature_new_user.data.model.LocationModel
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

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var location: Location

    private val binding get() = _binding!!

    private val stateObserver = Observer<NewUserViewModel.ViewState> {
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity()) //TODO check if this is okay
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
                    gender = "",
                    location = LocationModel(location.longitude, location.latitude)
            ))
        }

        binding.getLocationButton.setOnClickListener {
            Dexter.withContext(context)
                    .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    .withListener(object : PermissionListener {
                        override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                            if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                return
                            }
                            fusedLocationClient.lastLocation
                                    .addOnSuccessListener { loc: Location? ->
                                        Timber.d("Location is ${loc?.longitude} and ${loc?.latitude}")
                                        loc?.let {
                                            location = loc
                                        }
                                    }
                        }

                        override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                            Toast.makeText(context, "You won't be able to find any matches without your location", Toast.LENGTH_LONG).show()
                        }

                        override fun onPermissionRationaleShouldBeShown(
                                p0: PermissionRequest?,
                                p1: PermissionToken?
                        ) {
                            p1?.continuePermissionRequest();
                        }

                    }).check();
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