package com.example.imdbsearch.core.navigation.api

import androidx.fragment.app.Fragment
import com.example.imdbsearch.core.navigation.api.Navigator

interface NavigatorHolder {

    fun attachNavigator(navigator: Navigator)

    fun detachNavigator()

    fun openFragment(fragment: Fragment)
}