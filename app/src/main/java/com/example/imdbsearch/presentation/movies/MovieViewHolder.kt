package com.example.imdbsearch.presentation.movies

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.imdbsearch.R
import com.example.imdbsearch.domain.models.Movie
import com.example.imdbsearch.presentation.movies.MovieAdapter

class MovieViewHolder(
    parent: ViewGroup,
    private val clickListener: MovieAdapter.MovieClickListener
) : RecyclerView.ViewHolder(
    LayoutInflater
        .from(parent.context)
        .inflate(R.layout.item_movie_layout, parent, false)
) {

    val cover: ImageView = itemView.findViewById(R.id.cover)
    val title: TextView = itemView.findViewById(R.id.title)
    val description: TextView = itemView.findViewById(R.id.description)
    var inFavoriteToggle: ImageButton = itemView.findViewById(R.id.favorite)

    fun bind(movie: Movie) {
        Glide.with(itemView)
            .load(movie.image)
            .into(cover)

        title.text = movie.title
        description.text = movie.description
        inFavoriteToggle.setImageDrawable(getFavoriteToggleDrawable(movie.inFavorite))

        itemView.setOnClickListener { clickListener.onMovieClick(movie) }
        // 3
        inFavoriteToggle.setOnClickListener { clickListener.onFavoriteToggleClick(movie) }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun getFavoriteToggleDrawable(inFavorite: Boolean): Drawable? {
        return itemView.context.getDrawable(
            if (inFavorite) R.drawable.active_favorite else R.drawable.inactive_favorite
        )
    }
}