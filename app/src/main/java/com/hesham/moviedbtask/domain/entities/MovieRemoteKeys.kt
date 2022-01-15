package com.hesham.moviedbtask.domain.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "movieremotekeys")
data class MovieRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("id")
    val movieId: Long,
    @SerializedName("prevKey")
    val prevKey: Int?,
    @SerializedName("nextKey")
    val nextKey: Int?
)