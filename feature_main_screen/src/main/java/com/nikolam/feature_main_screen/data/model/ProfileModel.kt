package com.nikolam.feature_main_screen.data.model

import com.google.gson.annotations.SerializedName

data class ProfileModel(
        @SerializedName("user_id") val id: String,
        @SerializedName("profile_pic") val profilePicUrl: String,
        @SerializedName("bio") val bio: String,
        @SerializedName("name") val name: String
//        @SerializedName("__v") val __v: Int
)
