package com.example.movies.repository

import com.example.movies.data.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MainRepository {

    private val api: MainApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(MainApi::class.java)
    }

    fun getPopularMovies(
        page: Int,
        onSuccess: (movies: List<Movie>) -> Unit,
        onError: () -> Unit
    ) {
        api.getPopularMovies(page = page)
            .enqueue(object : Callback<GetMoviesResponse> {
                override fun onResponse(
                    call: Call<GetMoviesResponse>,
                    response: Response<GetMoviesResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            onSuccess.invoke(responseBody.movies)
                        } else {
                            onError.invoke()
                        }
                    } else {
                        onError.invoke()
                    }
                }

                override fun onFailure(call: Call<GetMoviesResponse>, t: Throwable) {
                    onError.invoke()
                }
            })
    }

    fun getUpcomingMovies(
        page: Int,
        onSuccess: (movies: List<Movie>) -> Unit,
        onError: () -> Unit
    ) {
        api.getUpcomingMovies(page = page)
            .enqueue(object : Callback<GetMoviesResponse> {
                override fun onResponse(
                    call: Call<GetMoviesResponse>,
                    response: Response<GetMoviesResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            onSuccess.invoke(responseBody.movies)
                        } else {
                            onError.invoke()
                        }
                    } else {
                        onError.invoke()
                    }
                }

                override fun onFailure(call: Call<GetMoviesResponse>, t: Throwable) {
                    onError.invoke()
                }
            })
    }

    fun getMovieGenres(
        id: Long,
        onSuccess: (genres: List<MovieGenres>) -> Unit,
        onError: () -> Unit
    ) {
        api.getMovieDetails(id)
            .enqueue(object : Callback<GetMovieDetailsResponse> {
                override fun onResponse(
                    call: Call<GetMovieDetailsResponse>,
                    response: Response<GetMovieDetailsResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            onSuccess.invoke(responseBody.genres)
                        } else {
                            onError.invoke()
                        }
                    } else {
                        onError.invoke()
                    }
                }

                override fun onFailure(call: Call<GetMovieDetailsResponse>, t: Throwable) {
                    onError.invoke()
                }
            })
    }

    fun getMovieRuntime(
        id: Long,
        onSuccess: (runtime: Int) -> Unit,
        onError: () -> Unit
    ) {
        api.getMovieDetails(id)
            .enqueue(object : Callback<GetMovieDetailsResponse> {
                override fun onResponse(
                    call: Call<GetMovieDetailsResponse>,
                    response: Response<GetMovieDetailsResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            onSuccess.invoke(responseBody.runtime)
                        } else {
                            onError.invoke()
                        }
                    } else {
                        onError.invoke()
                    }
                }

                override fun onFailure(call: Call<GetMovieDetailsResponse>, t: Throwable) {
                    onError.invoke()
                }
            })
    }

    fun getCast(
        id: Long,
        onSuccess: (cast: List<Cast>) -> Unit,
        onError: () -> Unit
    ) {
        api.getCast(id)
            .enqueue(object : Callback<GetCastResponse> {
                override fun onResponse(
                    call: Call<GetCastResponse>,
                    response: Response<GetCastResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            onSuccess.invoke(responseBody.cast)
                        } else {
                            onError.invoke()
                        }
                    } else {
                        onError.invoke()
                    }
                }

                override fun onFailure(call: Call<GetCastResponse>, t: Throwable) {
                    onError.invoke()
                }
            })
    }
}