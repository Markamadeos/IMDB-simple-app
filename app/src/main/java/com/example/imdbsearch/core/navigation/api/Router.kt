package com.example.imdbsearch.core.navigation.api

import androidx.fragment.app.Fragment
import com.example.imdbsearch.core.navigation.api.NavigatorHolder

/**
 * Router — это входная точка для фрагментов,
 * которые хотят открыть следующий экран.
 */
interface Router {

    val navigatorHolder: NavigatorHolder

    fun openFragment(fragment: Fragment)

}