package com.example.movies.repository

import com.example.movies.data.GetCastResponse
import com.example.movies.data.GetMovieDetailsResponse
import com.example.movies.data.GetMoviesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MainApi {

    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String = "27907acc2c5e9276061517d21944ea3a",
        @Query("page") page: Int
    ): Call<GetMoviesResponse>

    @GET("movie/upcoming")
    fun getUpcomingMovies(
        @Query("api_key") apiKey: String = "27907acc2c5e9276061517d21944ea3a",
        @Query("page") page: Int
    ): Call<GetMoviesResponse>

    @GET("movie/{id}")
    fun getMovieDetails(
        @Path("id") id: Long,
        @Query("api_key") apiKey: String = "27907acc2c5e9276061517d21944ea3a"
    ): Call<GetMovieDetailsResponse>

    @GET("movie/{id}/credits")
    fun getCast(
        @Path("id") id: Long,
        @Query("api_key") apiKey: String = "27907acc2c5e9276061517d21944ea3a"
    ): Call<GetCastResponse>
}