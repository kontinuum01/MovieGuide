package com.example.movieguide.features.moviefavorites.presentation

import com.example.common.di.FragmentScope
import com.example.movieguide.features.moviefavorites.domain.FavoriteMovie
import javax.inject.Inject

@FragmentScope
class FavoriteStateFactory @Inject constructor() {

    fun create(favoriteMovie: FavoriteMovie): FavoriteState {

        return FavoriteState(
            title = favoriteMovie.title,
            year = favoriteMovie.year,
            genre = favoriteMovie.genre,
            poster = favoriteMovie.poster,
            metascore = favoriteMovie.metascore,
            imdbRating = favoriteMovie.imdbRating,
        )
    }
}
