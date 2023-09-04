package com.example.imdbsearch.ui.poster

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.imdbsearch.databinding.ActivityPosterBinding

class PosterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPosterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPosterBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
        val poster = intent.getStringExtra("poster")
        Glide.with(this)
            .load(poster)
            .into(binding.poster)    }
}