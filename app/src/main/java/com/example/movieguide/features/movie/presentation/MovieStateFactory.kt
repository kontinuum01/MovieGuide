package com.example.movieguide.features.movie.presentation


import com.example.common.di.FragmentScope
import com.example.movieguide.features.movie.domain.Movie
import javax.inject.Inject

@FragmentScope
class MovieStateFactory @Inject constructor()  {

     fun create(movie: Movie): MovieState {
        return MovieState(
            title = movie.title,
            year = movie.year,
            runtime = movie.runtime,
            genre = movie.genre,
            director = movie.director,
            actors = movie.actors,
            plot = movie.plot,
            poster = movie.poster,
            metascore = movie.metascore,
            imdbRating = movie.imdbRating,
            isFavorite = movie.isFavorite
        )
    }
}