package com.example.movies.data

import com.google.gson.annotations.SerializedName

data class Cast(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("profile_path") val profilePath: String,
    @SerializedName("character") val character: String,
)
