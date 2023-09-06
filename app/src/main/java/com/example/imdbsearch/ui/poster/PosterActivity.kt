package com.example.imdbsearch.ui.poster

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.imdbsearch.util.Creator
import com.example.imdbsearch.R

class PosterActivity : AppCompatActivity() {

    private val posterController = Creator.providePosterController(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_poster)
        posterController.onCreate()
    }
}