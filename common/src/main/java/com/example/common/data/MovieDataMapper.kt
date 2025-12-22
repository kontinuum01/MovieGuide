package com.example.common.data

import kotlinx.serialization.InternalSerializationApi
import javax.inject.Inject

@OptIn(InternalSerializationApi::class)
class MovieDataMapper @Inject constructor() {
    fun toEntity(movieDto: MovieDto): MovieEntity {
        return MovieEntity(
            title = movieDto.title,
            year = movieDto.year,
            runtime = movieDto.runtime,
            genre = movieDto.genre,
            director = movieDto.director,
            actors = movieDto.actors,
            plot = movieDto.plot,
            poster = movieDto.poster,
            metascore = movieDto.metascore,
            imdbRating = movieDto.imdbRating,
            isFavorite = movieDto.isFavorite,
//            response = movieDto.response
        )
    }
}