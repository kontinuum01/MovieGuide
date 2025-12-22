package com.example.movieguide.features.moviefavorites.domain

import com.example.common.datafavorites.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.InternalSerializationApi
import javax.inject.Inject

@OptIn(InternalSerializationApi::class)
class ConsumeFavoriteUseCase @Inject constructor(
    private val favoritesRepository: FavoritesRepository,
    private val favoriteMovieDomainMapper: FavoriteMovieDomainMapper,
) {

    operator fun invoke(movieTitle: String): Flow<List<FavoriteMovie>> {
        return favoritesRepository.consumeFavorites()
            .map { movie -> movie.map(favoriteMovieDomainMapper::fromEntity) }
    }
}
