package com.example.imdbsearch

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.imdbsearch.model.Movie

class MovieAdapter() : RecyclerView.Adapter<MovieViewHolder>() {

    var movies = ArrayList<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder =
        MovieViewHolder(parent)

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies.get(position))
    }

    override fun getItemCount() = movies.size
}

