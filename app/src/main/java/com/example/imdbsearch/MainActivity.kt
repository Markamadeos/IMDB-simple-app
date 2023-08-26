package com.example.imdbsearch

import android.content.Intent
import android.os.Bundle
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

    private val imdbService = retrofit.create(IMDbApi::class.java)

    private val movies = ArrayList<Movie>()

    private val adapter = MovieAdapter() { onMovieClick(it) }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

        adapter.movies = movies

        binding.rvMovieList.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        binding.rvMovieList.adapter = adapter

        binding.searchButton.setOnClickListener {
            if (binding.queryInput.text.isNotEmpty()) {
                imdbService.getMovie(binding.queryInput.text.toString()).enqueue(object :
                    Callback<MoviesResponse> {
                    override fun onResponse(
                        call: Call<MoviesResponse>,
                        response: Response<MoviesResponse>
                    ) {
                        if (response.code() == 200) {
                            movies.clear()
                            if (response.body()?.results?.isNotEmpty() == true) {
                                movies.addAll(response.body()?.results!!)
                                adapter.notifyDataSetChanged()
                            }
                            if (movies.isEmpty()) {
                                showMessage(getString(R.string.nothing_found), "")
                            } else {
                                showMessage("", "")
                            }
                        } else {
                            showMessage(
                                getString(R.string.something_went_wrong),
                                response.code().toString()
                            )
                        }
                    }

                    override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                        showMessage(getString(R.string.something_went_wrong), t.message.toString())
                    }

                })
            }
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
        val intent = Intent(this, PosterActivity::class.java)
            .putExtra("MOVIE_POSTER", movie.image)
        startActivity(intent)
    }

}