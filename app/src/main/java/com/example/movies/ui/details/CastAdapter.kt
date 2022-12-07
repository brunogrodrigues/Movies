package com.example.movies.ui.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.movies.R
import com.example.movies.data.Cast
import com.example.movies.data.Movie

class CastAdapter(
    private var cast: List<Cast>
) : RecyclerView.Adapter<CastAdapter.CastViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.cast_item, parent, false)
        return CastViewHolder(view)
    }

    override fun getItemCount(): Int = cast.size

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        holder.bind(cast[position])
    }

    fun updateCast(cast: List<Cast>) {
        this.cast = cast
        notifyDataSetChanged()
    }

    inner class CastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val poster: ImageView = itemView.findViewById(R.id.item_cast_poster)
        private val member: TextView = itemView.findViewById(R.id.cast_member)
        private val character: TextView = itemView.findViewById(R.id.character)

        fun bind(cast: Cast) {
            Glide.with(itemView)
                .load("https://image.tmdb.org/t/p/w342${cast.profilePath}")
                .transform(CenterCrop())
                .into(poster)

            member.text = cast.name
            character.text = cast.character
        }
    }
}