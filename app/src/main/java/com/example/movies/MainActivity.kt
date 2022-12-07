package com.example.movies

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.movies.data.Movie
import com.example.movies.repository.MainRepository
import com.example.movies.ui.details.*
import com.example.movies.ui.details.MOVIE_OVERVIEW
import com.example.movies.ui.movies.MoviesAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var popularMovies: RecyclerView
    private lateinit var popularMoviesAdapter: MoviesAdapter
    private lateinit var popularMoviesLayoutMgr: StaggeredGridLayoutManager

    private var popularMoviesPage = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        popularMovies = findViewById(R.id.movies)
        popularMoviesLayoutMgr = StaggeredGridLayoutManager(
            3,
            StaggeredGridLayoutManager.VERTICAL
        )
        popularMovies.layoutManager = popularMoviesLayoutMgr
        popularMoviesAdapter = MoviesAdapter(mutableListOf()) { movie -> showMovieDetails(movie) }
        popularMovies.adapter = popularMoviesAdapter

        setupButtons()

        upcoming_movies_button.performClick()
    }

    private fun onPopularMoviesFetched(movies: List<Movie>) {
        popularMoviesAdapter.updateMovies(movies)
    }

    private fun onNewUpcomingMoviesFetched(movies: List<Movie>) {
        popularMoviesAdapter.addMovies(movies)
        attachMoviesOnScrollListener(GetMoviesTypeEnum.UPCOMING)
    }

    private fun onNewPopularMoviesFetched(movies: List<Movie>) {
        popularMoviesAdapter.addMovies(movies)
        attachMoviesOnScrollListener(GetMoviesTypeEnum.POPULAR)
    }

    private fun onError() {
        Toast.makeText(this, getString(R.string.error_fetch_movies), Toast.LENGTH_SHORT).show()
    }

    private fun setupButtons() {
        upcoming_movies_button.setOnClickListener {
            MainRepository.getUpcomingMovies(
                1,
                onSuccess = ::onPopularMoviesFetched,
                onError = ::onError
            )
            movies_title.text = "Upcoming Movies"
            attachMoviesOnScrollListener(GetMoviesTypeEnum.UPCOMING)
        }

        popular_movies_button.setOnClickListener {
            MainRepository.getPopularMovies(
                1,
                onSuccess = ::onPopularMoviesFetched,
                onError = ::onError
            )
            movies_title.text = "Popular Movies"
            attachMoviesOnScrollListener(GetMoviesTypeEnum.POPULAR)
        }
    }

    private fun showMovieDetails(movie: Movie) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(MOVIE_BACKDROP, movie.backdropPath)
        intent.putExtra(MOVIE_TITLE, movie.title)
        intent.putExtra(MOVIE_ID, movie.id)
        intent.putExtra(MOVIE_DATE, movie.releaseDate)
        intent.putExtra(MOVIE_OVERVIEW, movie.overview)
        startActivity(intent)
    }

    private fun attachMoviesOnScrollListener(type: GetMoviesTypeEnum) {
        popularMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = popularMoviesLayoutMgr.itemCount
                val visibleItemCount = popularMoviesLayoutMgr.childCount
                val firstVisibleItem = popularMoviesLayoutMgr.findFirstVisibleItemPositions(null)

                if (firstVisibleItem[0] + visibleItemCount >= totalItemCount / 2) {
                    popularMovies.removeOnScrollListener(this)
                    popularMoviesPage++
                    if (type == GetMoviesTypeEnum.POPULAR) {
                        MainRepository.getPopularMovies(
                            popularMoviesPage,
                            onSuccess = ::onNewPopularMoviesFetched,
                            onError = ::onError
                        )
                    }
                    if (type == GetMoviesTypeEnum.UPCOMING) {
                        MainRepository.getUpcomingMovies(
                            popularMoviesPage,
                            onSuccess = ::onNewUpcomingMoviesFetched,
                            onError = ::onError
                        )
                    }
                }
            }
        })
    }
}