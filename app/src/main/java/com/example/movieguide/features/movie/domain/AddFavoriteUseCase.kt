package com.example.movieguide.features.movie.domain

import com.example.common.datafavorites.FavoriteEntity
import com.example.common.datafavorites.FavoritesRepository
import kotlinx.serialization.InternalSerializationApi
import javax.inject.Inject

@OptIn(InternalSerializationApi::class)
class AddFavoriteUseCase @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) {
    suspend operator fun invoke(favorite: FavoriteEntity) {
        favoritesRepository.addToFavorites(favorite)
    }
}