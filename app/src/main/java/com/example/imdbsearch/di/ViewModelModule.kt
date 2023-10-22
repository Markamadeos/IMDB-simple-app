import com.example.imdbsearch.presentation.movies.view_model.MoviesSearchViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        MoviesSearchViewModel(moviesInteractor = get(), androidApplication())
    }
} 