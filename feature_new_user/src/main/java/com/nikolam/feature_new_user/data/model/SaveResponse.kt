package com.nikolam.feature_new_user.data.model

import android.content.Context
import com.google.gson.annotations.SerializedName
import com.nikolam.common.utils.GetTimePassed
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

data class SaveResponse(
        val status : Int
)