package com.nikolam.feature_new_user.data.model

import android.location.Location

data class NewProfileModel(
    val name: String,
    val gender: String,
    val bio: String,
    val location : LocationModel,
    val preferences : PreferenceModel,
    val age : Int
    )