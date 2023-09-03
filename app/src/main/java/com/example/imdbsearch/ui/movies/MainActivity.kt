package com.example.imdbsearch.ui.movies

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.imdbsearch.Creator
import com.example.imdbsearch.R
import com.example.imdbsearch.databinding.ActivityMainBinding
import com.example.imdbsearch.domain.api.MoviesInteractor
import com.example.imdbsearch.domain.models.Movie
import com.example.imdbsearch.ui.poster.PosterActivity

class MainActivity : AppCompatActivity() {

    private val moviesInteractor = Creator.provideMoviesInteractor()

    private val movies = ArrayList<Movie>()

    private val adapter = MovieAdapter { onMovieClick(it) }

    private lateinit var binding: ActivityMainBinding

    private var isClickAllowed = true

    private val handler = Handler(Looper.getMainLooper())

    private val searchRunnable = Runnable { searchRequest() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

        adapter.movies = movies

        binding.queryInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                searchDebounce()
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })

        binding.rvMovieList.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        binding.rvMovieList.adapter = adapter
    }

    private fun searchDebounce() {
        handler.removeCallbacks(searchRunnable)
        handler.postDelayed(searchRunnable, SEARCH_DEBOUNCE_DELAY_MS)
    }

    private fun searchRequest() {
        if (binding.queryInput.text.isNotEmpty()) {

            binding.placeholderMessage.visibility = View.GONE
            binding.rvMovieList.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE

            moviesInteractor.searchMovies(binding.queryInput.text.toString(), object : MoviesInteractor.MoviesConsumer {
                override fun consume(foundMovies: List<Movie>) {
                    handler.post {
                        binding.progressBar.visibility = View.GONE
                        movies.clear()
                        movies.addAll(foundMovies)
                        binding.rvMovieList.visibility = View.VISIBLE
                        adapter.notifyDataSetChanged()
                        if (movies.isEmpty()) {
                            showMessage(getString(R.string.nothing_found), "")
                        } else {
                            hideMessage()
                        }
                    }
                }
            })
        }
    }

    private fun showMessage(text: String, additionalMessage: String) {
        if (text.isNotEmpty()) {
            binding.placeholderMessage.visibility = View.VISIBLE
            movies.clear()
            adapter.notifyDataSetChanged()
            binding.placeholderMessage.text = text
            if (additionalMessage.isNotEmpty()) {
                Toast.makeText(applicationContext, additionalMessage, Toast.LENGTH_LONG)
                    .show()
            }
        } else {
            binding.placeholderMessage.visibility = View.GONE
        }
    }

    private fun onMovieClick(movie: Movie) {
        if (clickDebounce()) {
            val intent = Intent(this, PosterActivity::class.java)
            intent.putExtra("poster", movie.image)
            startActivity(intent)
        }
    }

    private fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            handler.postDelayed({ isClickAllowed = true }, CLICK_DEBOUNCE_DELAY_MS)
        }
        return current
    }

    private fun hideMessage() {
        binding.placeholderMessage.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(searchRunnable)
    }

    companion object {
        private const val CLICK_DEBOUNCE_DELAY_MS = 1000L
        private const val SEARCH_DEBOUNCE_DELAY_MS = 2000L
    }

}