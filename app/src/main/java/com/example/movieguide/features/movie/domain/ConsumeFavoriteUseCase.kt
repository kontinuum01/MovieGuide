package com.example.movieguide.features.movie.domain

import com.example.common.datafavorites.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.serialization.InternalSerializationApi
import javax.inject.Inject

@OptIn(InternalSerializationApi::class)
class ConsumeFavoriteUseCase @Inject constructor(
    private val favoritesRepository: FavoritesRepository,
    private val consumeMovieUseCase: ConsumeMovieUseCase,
){

    operator fun invoke(movieTitle: String): Flow<List<Movie>> {
        return combine(
            consumeMovieUseCase(movieTitle = ""),
            favoritesRepository.consumeFavorites()
        ) { movies, favoriteEntities ->
            val favoriteIds = favoriteEntities.map { it.title }.toSet()
            movies.map { it.copy(isFavorite = it.title in favoriteIds) }
        }
    }
}