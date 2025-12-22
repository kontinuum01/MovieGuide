package com.example.movieguide.features.moviefavorites.domain

import com.example.common.datafavorites.FavoriteEntity
import kotlinx.serialization.InternalSerializationApi
import javax.inject.Inject

@OptIn(InternalSerializationApi::class)
class FavoriteMovieDomainMapper @Inject constructor() {

    fun fromEntity(favoriteEntity: FavoriteEntity): FavoriteMovie {
        return FavoriteMovie(
            title = favoriteEntity.title,
            year = favoriteEntity.year,
            genre = favoriteEntity.genre,
            poster = favoriteEntity.poster,
            metascore = favoriteEntity.metascore,
            imdbRating = favoriteEntity.imdbRating,

        )
    }
}