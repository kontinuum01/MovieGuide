package com.example.movieguide.di

import android.content.Context
import com.example.common.data.MovieRepository
import com.example.common.datafavorites.FavoritesRepository
import com.example.common.di.Dependencies
import com.example.movieguide.features.movie.di.MovieListComponentDependencies
import com.example.movieguide.features.moviedetails.di.MovieDetailsComponentDependencies
import com.example.movieguide.features.moviefavorites.di.MovieFavoritesComponentDependencies
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NetworkModule::class,
        DataModule::class,
    ]
)
interface AppComponent:
    Dependencies,
    MovieListComponentDependencies,
    MovieDetailsComponentDependencies,
    MovieFavoritesComponentDependencies
{
    override fun getMovieRepository(): MovieRepository
    override fun getFavoritesRepository(): FavoritesRepository

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): AppComponent
    }
}