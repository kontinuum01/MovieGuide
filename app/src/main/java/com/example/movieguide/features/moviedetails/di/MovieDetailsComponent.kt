package com.example.movieguide.features.moviedetails.di

import com.example.common.data.MovieRepository
import com.example.common.di.FragmentScope
import com.example.movieguide.features.moviedetails.presentation.MovieDetailsFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Named

@FragmentScope
@Component(dependencies = [MovieDetailsComponentDependencies::class])
interface MovieDetailsComponent {

    @Component.Factory
    interface Factory {
        fun create(
            dependencies: MovieDetailsComponentDependencies,
            @BindsInstance @Named("movieTitle") movieTitle: String,
            ): MovieDetailsComponent
    }

    fun inject(movieDetailsFragment: MovieDetailsFragment)
}
interface MovieDetailsComponentDependencies {
    fun getMovieRepository(): MovieRepository
}