package com.nikolam.data.db

import androidx.room.*
import com.nikolam.data.db.models.MessageDataModel
import com.nikolam.data.db.models.ProfileDataModel
import com.nikolam.data.db.models.UserDataModel
import kotlinx.coroutines.flow.Flow


@Dao
interface ProfileDao {
    @Query("SELECT * FROM profile")
    suspend fun getProfile() : List<ProfileDataModel>

    @Update
    suspend fun updateProfile(profileDataModel: ProfileDataModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProfile(profileDataModel: ProfileDataModel)
}