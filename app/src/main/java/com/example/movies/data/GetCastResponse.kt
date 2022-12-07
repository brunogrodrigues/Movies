package com.example.movies.data

import com.google.gson.annotations.SerializedName

data class GetCastResponse(
    @SerializedName("cast") val cast: List<Cast>
)
