package com.example.movieguide.features.moviedetails.presentation

import com.example.movieguide.features.moviedetails.domain.MovieDetails
import javax.inject.Inject

class MovieDetailsStateFactory @Inject constructor() {

    fun create(movieDetails: MovieDetails): MovieDetailsState {
        return MovieDetailsState(
            title = movieDetails.title,
            year = movieDetails.year,
            runtime = movieDetails.runtime,
            genre = movieDetails.genre,
            director = movieDetails.director,
            actors = movieDetails.actors,
            plot = movieDetails.plot,
            poster = movieDetails.poster,
            metascore = movieDetails.metascore,
            imdbRating = movieDetails.imdbRating,
        )
    }
}