package com.example.movieguide.features.moviedetails.domain

import com.example.common.data.MovieEntity
import kotlinx.serialization.InternalSerializationApi
import javax.inject.Inject

@OptIn(InternalSerializationApi::class)
class MovieDetailsDataMapper @Inject constructor(){

    fun fromEntity(movieEntity: MovieEntity): MovieDetails {
        return MovieDetails(
            title = movieEntity.title,
            year = movieEntity.year,
            runtime = movieEntity.runtime,
            genre = movieEntity.genre,
            director = movieEntity.director,
            actors = movieEntity.actors,
            plot = movieEntity.plot,
            poster = movieEntity.poster,
            metascore = movieEntity.metascore,
            imdbRating = movieEntity.imdbRating,
            isFavorite = movieEntity.isFavorite
        )
    }
}