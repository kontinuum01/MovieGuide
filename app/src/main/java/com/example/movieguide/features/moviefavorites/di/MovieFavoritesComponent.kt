package com.example.movieguide.features.moviefavorites.di

import com.example.common.datafavorites.FavoritesRepository
import com.example.common.di.FragmentScope
import com.example.movieguide.features.moviefavorites.presentation.MovieFavoritesFragment
import dagger.Component

@FragmentScope
@Component(dependencies = [MovieFavoritesComponentDependencies::class])
interface MovieFavoritesComponent {

    @Component.Factory
    interface Factory {
        fun create(
            dependencies: MovieFavoritesComponentDependencies,
        ): MovieFavoritesComponent
    }

    fun inject(movieFavoritesFragment: MovieFavoritesFragment)
}
interface MovieFavoritesComponentDependencies {

    fun getFavoritesRepository() : FavoritesRepository
}
