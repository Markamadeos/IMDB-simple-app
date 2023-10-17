package com.example.imdbsearch.presentation.ui.poster

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.imdbsearch.R
import com.example.imdbsearch.presentation.poster.PosterPresenter
import com.example.imdbsearch.presentation.poster.PosterView
import com.example.imdbsearch.util.Creator

class PosterActivity : AppCompatActivity(), PosterView {

    private lateinit var poster: ImageView

    private lateinit var posterPresenter: PosterPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_poster)

        poster = findViewById(R.id.poster)
        val url = intent.extras?.getString("poster", "")

        posterPresenter = Creator.providePosterPresenter(this, url!!)

        posterPresenter.onCreate()

    }

    override fun setupPosterImage(url: String) {
        Glide.with(applicationContext)
            .load(url)
            .into(poster)
    }
}
