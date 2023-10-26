package com.example.imdbsearch.core.navigation.impl

import androidx.fragment.app.Fragment
import com.example.imdbsearch.core.navigation.api.NavigatorHolder
import com.example.imdbsearch.core.navigation.api.Router

class RouterImpl : Router {

    override val navigatorHolder: NavigatorHolder = NavigatorHolderImpl()

    override fun openFragment(fragment: Fragment) {
        navigatorHolder.openFragment(fragment)
    }
}