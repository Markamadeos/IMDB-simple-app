package com.example.imdbsearch.ui.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.imdbsearch.R
import com.example.imdbsearch.models.Movie

class MovieViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater
    .from(parent.context)
    .inflate(R.layout.item_movie_layout, parent, false)) {

    val cover: ImageView = itemView.findViewById(R.id.cover)
    val title: TextView = itemView.findViewById(R.id.title)
    val description: TextView = itemView.findViewById(R.id.description)

    fun bind (movie: Movie) {
        Glide.with(itemView)
            .load(movie.image)
            .into(cover)

        title.text = movie.title
        description.text = movie.description
    }
}