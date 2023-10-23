package com.example.imdbsearch.presentation.details.about.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.imdbsearch.databinding.FragmentAboutBinding
import com.example.imdbsearch.domain.models.MovieDetails
import com.example.imdbsearch.presentation.cast.ui.MoviesCastActivity
import com.example.imdbsearch.presentation.details.about.model.AboutState
import com.example.imdbsearch.presentation.details.about.view_model.AboutViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class AboutFragment : Fragment() {

    private val aboutViewModel: AboutViewModel by viewModel {
        parametersOf(requireArguments().getString(MOVIE_ID))
    }

    private lateinit var binding: FragmentAboutBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAboutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        aboutViewModel.observeState().observe(viewLifecycleOwner) {
            when (it) {
                is AboutState.Content -> showDetails(it.movie)
                is AboutState.Error -> showErrorMessage(it.message)
            }
        }

        setupButton()
    }

    private fun setupButton() {
        binding.btnShowCast.setOnClickListener {
            val intent = Intent(
                MoviesCastActivity.newInstance(
                    context = requireContext(),
                    movieId = requireArguments().getString(MOVIE_ID).orEmpty()
                )
            )
            startActivity(intent)
        }
    }

    private fun showErrorMessage(message: String) {
        binding.apply {
            details.visibility = View.GONE
            errorMessage.visibility = View.VISIBLE
            errorMessage.text = message
        }
    }

    private fun showDetails(movieDetails: MovieDetails) {
        binding.apply {
            details.visibility = View.VISIBLE
            errorMessage.visibility = View.GONE
            title.text = movieDetails.title
            ratingValue.text = movieDetails.imDbRating
            yearValue.text = movieDetails.year
            countryValue.text = movieDetails.countries
            genreValue.text = movieDetails.genres
            directorValue.text = movieDetails.directors
            writerValue.text = movieDetails.writers
            castValue.text = movieDetails.stars
            plot.text = movieDetails.plot
        }
    }

    companion object {
        private const val MOVIE_ID = "movie_id"

        fun newInstance(movieId: String) = AboutFragment().apply {
            arguments = Bundle().apply {
                putString(MOVIE_ID, movieId)
            }
        }
    }
}