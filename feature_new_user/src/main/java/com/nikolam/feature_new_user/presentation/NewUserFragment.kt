package com.nikolam.feature_new_user.presentation

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.esafirm.imagepicker.features.ImagePicker
import com.esafirm.imagepicker.features.ReturnMode
import com.esafirm.imagepicker.model.Image
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.jaygoo.widget.OnRangeChangedListener
import com.jaygoo.widget.RangeSeekBar
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.nikolam.feature_new_user.R
import com.nikolam.feature_new_user.data.model.LocationModel
import com.nikolam.feature_new_user.data.model.NewProfileModel
import com.nikolam.feature_new_user.data.model.PreferenceModel
import com.nikolam.feature_new_user.databinding.NewUserFragmentBinding
import com.nikolam.feature_new_user.di.newUserModule
import org.koin.android.ext.android.inject
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import timber.log.Timber


class NewUserFragment : Fragment() {

    private val genders = arrayListOf("male", "female", "other")
    private val genderPreference = arrayListOf("male", "female", "both", "other")

    private val viewModel: NewUserViewModel by inject()

    private var _binding: NewUserFragmentBinding? = null

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var location: Location

    private val binding get() = _binding!!

    private var leftAgeLimit = 0
    private var rightAgeLimit = 0

    private val stateObserver = Observer<NewUserViewModel.ViewState> {
        if (it.isSuccess) {
            viewModel.navigateToMainScreen()
        }
    }

    lateinit var profilePicture: Image


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(requireActivity()) //TODO check if this is okay
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

        val genderSpinner: Spinner = binding.genderSpinner
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.gender_list,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            genderSpinner.adapter = adapter
        }

        val genderPrefSpinner: Spinner = binding.genderPreferenceSpinner
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.gender_preferences_list,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            genderPrefSpinner.adapter = adapter
        }

        binding.continueButton.setOnClickListener {
            viewModel.saveProfile(
                NewProfileModel(
                    name = binding.nameEditText.text.toString(),
                    bio = binding.bioEditText.text.toString(),
                    gender = genders[genderSpinner.selectedItemPosition],
                    location = LocationModel(location.longitude, location.latitude),
                    age = binding.ageEditText.text.toString().toInt(),
                    preferences = PreferenceModel(
                        genderPreference[binding.genderPreferenceSpinner.selectedItemPosition],
                        binding.distancePreference.text.toString().toInt(),
                        age_min = leftAgeLimit,
                        age_max = rightAgeLimit
                    )
                ),
                profilePicture.path
            )
        }

        binding.agePreferenceSeeker.let {
            it.setProgress(18f, 90f)
            it.setIndicatorTextDecimalFormat("0")
            it.setOnRangeChangedListener(object : OnRangeChangedListener {
                override fun onRangeChanged(
                    view: RangeSeekBar?,
                    leftValue: Float,
                    rightValue: Float,
                    isFromUser: Boolean
                ) {
                    leftAgeLimit = leftValue.toInt()
                    rightAgeLimit = rightValue.toInt()
                }

                override fun onStartTrackingTouch(view: RangeSeekBar?, isLeft: Boolean) {
                }

                override fun onStopTrackingTouch(view: RangeSeekBar?, isLeft: Boolean) {
                }

            })
        }


        setupProfileImagePicker()

        setupLocationButtonAndPermission()

        viewModel.stateLiveData.observe(viewLifecycleOwner, stateObserver)

        viewModel.loadData()

        return view
    }

    private fun setupProfileImagePicker() {
        binding.profilePic.setOnClickListener {
            requestStoragePermission()
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            val image = ImagePicker.getFirstImageOrNull(data)
            image?.let {
                Glide.with(binding.profilePic).load(image.uri).into(binding.profilePic)
                profilePicture = it
            }
        }


        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun setupLocationButtonAndPermission() {
        binding.getLocationButton.setOnClickListener {
            Dexter.withContext(context)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                        if (ActivityCompat.checkSelfPermission(
                                requireContext(),
                                Manifest.permission.ACCESS_FINE_LOCATION
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {
                            return
                        }
                        fusedLocationClient.lastLocation
                            .addOnSuccessListener { loc: Location? ->
                                Timber.d("Location is ${loc?.longitude} and ${loc?.latitude}")
                                loc?.let {
                                    location = loc
                                    binding.locationTextView.text = "Successfully located"
                                    binding.locationTextView.setTextColor(Color.GREEN)
                                }
                            }
                    }

                    override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                        Toast.makeText(
                            context,
                            "You won't be able to find any matches without your location",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        p0: PermissionRequest?,
                        p1: PermissionToken?
                    ) {
                        p1?.continuePermissionRequest();
                    }

                }).check();
        }
    }

    private fun requestStoragePermission() {
        Dexter.withContext(context)
            .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                    if (ActivityCompat.checkSelfPermission(
                            requireContext(),
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        return
                    }
                }

                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                    Toast.makeText(
                        context,
                        "You won't be able to find any matches without your profile picture",
                        Toast.LENGTH_LONG
                    ).show()
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: PermissionRequest?,
                    p1: PermissionToken?
                ) {
                    p1?.continuePermissionRequest();
                }

            }).check();
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