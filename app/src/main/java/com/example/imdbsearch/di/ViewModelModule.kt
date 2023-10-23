import com.example.imdbsearch.presentation.cast.view_model.MoviesCastViewModel
import com.example.imdbsearch.presentation.details.about.view_model.AboutViewModel
import com.example.imdbsearch.presentation.movies.view_model.MoviesSearchViewModel
import com.example.imdbsearch.presentation.details.poster.PosterViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        MoviesSearchViewModel(moviesInteractor = get(), application = androidApplication())
    }

    viewModel { (movieId: String) ->
        AboutViewModel(movieId = movieId, moviesInteractor = get())
    }

    viewModel { (posterUrl: String) ->
        PosterViewModel(posterUrl = posterUrl)
    }

    viewModel { (movieId: String) ->
        MoviesCastViewModel(movieId = movieId, moviesInteractor = get())
    }

} 