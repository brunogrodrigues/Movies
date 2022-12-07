package com.example.movies.data

import com.google.gson.annotations.SerializedName

data class GetMovieDetailsResponse(
    @SerializedName("genres") val genres: List<MovieGenres>,
    @SerializedName("runtime") val runtime: Int,
    @SerializedName("release_date") val release_date: String
)
