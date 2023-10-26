package com.example.imdbsearch.presentation.root

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import com.example.imdbsearch.R
import com.example.imdbsearch.core.navigation.api.NavigatorHolder
import com.example.imdbsearch.core.navigation.impl.NavigatorImpl
import com.example.imdbsearch.databinding.ActivityRootBinding
import com.example.imdbsearch.presentation.movies.ui.MoviesFragment
import org.koin.android.ext.android.inject

class RootActivity : AppCompatActivity() {

    // Заинжектили NavigatorHolder,
    // чтобы прикрепить к нему Navigator
    private val navigatorHolder: NavigatorHolder by inject()

    // Создали Navigator
    private val navigator = NavigatorImpl(
        fragmentContainerViewId = R.id.rootFragmentContainerView,
        fragmentManager = supportFragmentManager
    )

    private lateinit var binding: ActivityRootBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRootBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            navigator.openFragment(
                MoviesFragment()
            )
        }
    }

    // Прикрепляем Navigator к NavigatorHolder
    override fun onResume() {
        super.onResume()
        navigatorHolder.attachNavigator(navigator)
    }

    // Открепляем Navigator от NavigatorHolder
    override fun onPause() {
        super.onPause()
        navigatorHolder.detachNavigator()
    }
}

