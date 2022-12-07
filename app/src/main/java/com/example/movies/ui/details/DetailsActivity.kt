package com.example.movies.ui.details

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.movies.R
import com.example.movies.data.Cast
import com.example.movies.data.MovieGenres
import com.example.movies.repository.MainRepository
import kotlinx.android.synthetic.main.activity_details.*
import java.time.LocalDate

const val MOVIE_BACKDROP = "extra_movie_backdrop"
const val MOVIE_TITLE = "extra_movie_title"
const val MOVIE_ID = "extra_movie_id"
const val MOVIE_DATE = "extra_movie_date"
const val MOVIE_OVERVIEW = "extra_movie_overview"

class DetailsActivity : AppCompatActivity() {

    private lateinit var cast: RecyclerView
    private lateinit var castAdapter: CastAdapter
    private lateinit var castLayoutMgr: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        cast = findViewById(R.id.cast)
        castLayoutMgr = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        cast.layoutManager = castLayoutMgr
        castAdapter = CastAdapter(mutableListOf())
        cast.adapter = castAdapter

        val extras = intent.extras

        if (extras != null) {
            populateDetails(extras)
        } else {
            finish()
        }
    }

    private fun populateDetails(extras: Bundle) {
        extras.getString(MOVIE_BACKDROP)?.let { backdropPath ->
            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w1280$backdropPath")
                .transform(CenterCrop())
                .into(poster)
        }


        movie_name.text = extras.getString(MOVIE_TITLE, "")
        movie_description.text = extras.getString(MOVIE_OVERVIEW, "")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            movie_year.text = LocalDate.parse(
                extras.getString(MOVIE_DATE,
                    ""
                )
            ).year.toString()
        }

        MainRepository.getCast(
            intent.getLongExtra(MOVIE_ID, 0),
            onSuccess = ::onCastFetched,
            onError = ::onError
        )
        MainRepository.getMovieRuntime(
            intent.getLongExtra(MOVIE_ID, 0),
            onSuccess = ::onMovieRuntimeFetched,
            onError = ::onError
        )
        MainRepository.getMovieGenres(
            intent.getLongExtra(MOVIE_ID, 0),
            onSuccess = ::onMovieGenresFetched,
            onError = ::onError
        )
    }

    private fun onError() {
        Toast.makeText(this, getString(R.string.error_fetch_movies), Toast.LENGTH_SHORT).show()
    }

    private fun onCastFetched(cast: List<Cast>) {
        castAdapter.updateCast(cast)
    }

    private fun onMovieRuntimeFetched(runtime : Int) {
        movie_runtime.text = runtime.toString() + "m"
    }

    private fun onMovieGenresFetched(genres : List<MovieGenres>) {
        val genreNames = ArrayList<String>()
        for (i: Int in genres.indices){ genreNames.add(genres[i].name) }
        movie_genre.text = genreNames.joinToString()
    }
}