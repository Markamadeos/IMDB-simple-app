package com.example.imdbsearch

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
import com.example.imdbsearch.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private val imdbBaseUrl = "https://imdb-api.com/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(imdbBaseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val imdbService = retrofit.create(IMDbApiService::class.java)

    private val movies = ArrayList<Movie>()

    private val adapter = MovieAdapter() { onMovieClick(it) }

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

            imdbService.searchMovies(binding.queryInput.text.toString())
                .enqueue(object : Callback<MoviesSearchResponse> {
                    override fun onResponse(
                        call: Call<MoviesSearchResponse>,
                        response: Response<MoviesSearchResponse>
                    ) {
                        binding.progressBar.visibility = View.GONE
                        if (response.code() == 200) {
                            movies.clear()
                            if (response.body()?.results?.isNotEmpty() == true) {
                                binding.rvMovieList.visibility = View.VISIBLE
                                movies.addAll(response.body()?.results!!)
                                adapter.notifyDataSetChanged()
                            }
                            if (movies.isEmpty()) {
                                showMessage(getString(R.string.nothing_found), "")
                            } else {
                                hideMessage()
                            }
                        } else {
                            showMessage(
                                getString(R.string.something_went_wrong),
                                response.code().toString()
                            )
                        }
                    }

                    override fun onFailure(call: Call<MoviesSearchResponse>, t: Throwable) {
                        binding.progressBar.visibility = View.GONE
                        showMessage(getString(R.string.something_went_wrong), t.message.toString())
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