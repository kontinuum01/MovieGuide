package com.example.movieguide.features.movie.domain

import com.example.common.data.MovieEntity
import kotlinx.serialization.InternalSerializationApi
import javax.inject.Inject

class MovieDomainMapper @Inject constructor(){
    @OptIn(InternalSerializationApi::class)
    fun fromEntity(movieEntity: MovieEntity): Movie {
        return Movie(
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