package com.example.common.datafavorites

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import kotlinx.serialization.InternalSerializationApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@OptIn(InternalSerializationApi::class)
class FavoritesRepository @Inject constructor(
    private val favoritesDataSource: FavoritesDataSource,
    private val dispatcher: CoroutineDispatcher,
) {
    fun consumeFavorites(): Flow<List<FavoriteEntity>> {
        return favoritesDataSource.consumeFavorites()
            .flowOn(dispatcher)
    }

    @OptIn(InternalSerializationApi::class)
    suspend fun addToFavorites(favorite: FavoriteEntity) = withContext(dispatcher) {
        favoritesDataSource.saveFavorite(favorite)
    }

    @OptIn(InternalSerializationApi::class)
    suspend fun removeFromFavorites(favorite: FavoriteEntity) = withContext(dispatcher) {
        favoritesDataSource.removeFavorite(favorite)
    }
}