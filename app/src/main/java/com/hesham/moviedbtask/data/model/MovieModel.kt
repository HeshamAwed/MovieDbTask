package com.hesham.moviedbtask.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.hesham.moviedbtask.data.Constants
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "movie")
data class MovieModel(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("id")
    var id: Int,
    @SerializedName("adult")
    var adult: Boolean?,
    @SerializedName("backdrop_path")
    var backdropPath: String?,
    @SerializedName("budget")
    var budget: Int?,
    @SerializedName("homepage")
    var homepage: String?,
    @SerializedName("imdb_id")
    var imdbId: String?,
    @SerializedName("original_language")
    var originalLanguage: String?,
    @SerializedName("original_title")
    var originalTitle: String?,
    @SerializedName("overview")
    var overview: String?,
    @SerializedName("popularity")
    var popularity: Double?,
    @SerializedName("poster_path")
    var posterPath: String?,
    @SerializedName("release_date")
    var releaseDate: String?,
    @SerializedName("revenue")
    var revenue: Int?,
    @SerializedName("runtime")
    var runtime: Int?,
    @SerializedName("status")
    var status: String?,
    @SerializedName("tagline")
    var tagline: String?,
    @SerializedName("title")
    var title: String?,
    @SerializedName("video")
    var video: Boolean?,
    @SerializedName("vote_average")
    var voteAverage: Double?,
    @SerializedName("vote_count")
    var voteCount: Int?,
    @SerializedName("filter_type")
    var filter_type: String?
) : Parcelable {

    fun getFullBackdropPath() =
        if (backdropPath.isNullOrBlank()) null else Constants.LARGE_IMAGE_URL + backdropPath

    fun getFullPosterPath() =
        if (posterPath.isNullOrBlank()) null else Constants.SMALL_IMAGE_URL + posterPath
}
