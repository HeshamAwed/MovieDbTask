package com.hesham.moviedbtask.domain.entities

import com.google.gson.annotations.SerializedName

data class MovieList(
    @SerializedName("page")
    var page: Int?,
    @SerializedName("results")
    var results: List<Movie>?,
    @SerializedName("total_pages")
    var totalPages: Int?,
    @SerializedName("total_results")
    var totalResults: Int?
)
