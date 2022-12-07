package com.example.movies.ui.movies

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.movies.R
import com.example.movies.data.Movie
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MoviesAdapter(
    private var movies: List<Movie>,
    private val onMovieClick: (movie: Movie) -> Unit
) : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.movie_item, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    fun updateMovies(movies: List<Movie>) {
        this.movies = movies
        notifyDataSetChanged()
    }

    fun addMovies(movies: List<Movie>) {
        this.movies += movies
        notifyDataSetChanged()
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val poster: ImageView = itemView.findViewById(R.id.item_movie_poster)
        private val date: TextView = itemView.findViewById(R.id.item_movie_date)
        private val title: TextView = itemView.findViewById(R.id.item_movie_title)

        fun bind(movie: Movie) {
            Glide.with(itemView)
                .load("https://image.tmdb.org/t/p/w342${movie.posterPath}")
                .transform(CenterCrop())
                .into(poster)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                date.text = LocalDate.parse(movie.releaseDate).format(DateTimeFormatter.ofPattern("dd/MM/yy")).toString()
            }
            title.text = movie.title
            itemView.setOnClickListener { onMovieClick.invoke(movie) }
        }
    }
}