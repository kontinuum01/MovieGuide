package com.example.movieguide.features.movie.di

import com.example.common.data.MovieRepository
import com.example.common.datafavorites.FavoritesRepository
import com.example.common.di.FragmentScope
import com.example.movieguide.features.movie.presentation.MovieListFragment
import dagger.Component

@FragmentScope
@Component(dependencies = [MovieListComponentDependencies::class])
interface MovieListComponent {

    @Component.Factory
    interface Factory {
        fun create(
            dependencies: MovieListComponentDependencies,
        ): MovieListComponent
    }

    fun inject(movieListFragment: MovieListFragment)
}

interface MovieListComponentDependencies {

    fun getMovieRepository(): MovieRepository
    fun getFavoritesRepository(): FavoritesRepository
}