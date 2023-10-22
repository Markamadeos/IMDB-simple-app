package com.example.imdbsearch.presentation.cast

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.imdbsearch.databinding.ActivityCastBinding

class CastActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCastBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCastBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    companion object {

        private const val ARGS_MOVIE_ID = "movie_id"

        fun newInstance(context: Context, movieId: String): Intent {
            return Intent(context, CastActivity::class.java).apply {
                putExtra(ARGS_MOVIE_ID, movieId)
            }
        }
    }
}