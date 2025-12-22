package com.example.common.datafavorites

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.json.Json

@OptIn(InternalSerializationApi::class)
interface FavoritesDataSource {

    fun consumeFavorites(): Flow<List<FavoriteEntity>>

    suspend fun saveFavorite(favoriteEntity: FavoriteEntity)

    suspend fun removeFavorite(favoriteEntity: FavoriteEntity)
}

@OptIn(ExperimentalSerializationApi::class, InternalSerializationApi::class)
class FavoritesDataSourceImpl(
    private val dataStore: DataStore<Preferences>,
) : FavoritesDataSource {

    override fun consumeFavorites(): Flow<List<FavoriteEntity>> = dataStore.data
        .map (::mapFromPrefs)


    override suspend fun saveFavorite(favoriteEntity: FavoriteEntity) {
        dataStore.edit { prefs ->
            val currentFavorites = mapFromPrefs(prefs).toMutableSet()
            currentFavorites.add(favoriteEntity)
            prefs[preferencesKey] = Json.encodeToString(currentFavorites.toList())
        }
    }

    override suspend fun removeFavorite(favoriteEntity: FavoriteEntity) {
        dataStore.edit { prefs ->
            val currentFavorites = mapFromPrefs(prefs).toMutableSet()
            currentFavorites.removeIf { it.title == favoriteEntity.title }
            prefs[preferencesKey] = Json.encodeToString(currentFavorites.toList())
        }
    }

    private fun mapFromPrefs(prefs: Preferences): List<FavoriteEntity> =
        prefs[preferencesKey]
            ?.takeIf(String::isNotEmpty)
            ?.let { Json.decodeFromString(it) }
            ?: listOf()

    private val preferencesKey = stringPreferencesKey(KEY)

    private companion object {
        const val KEY = "favorite_key"
    }

}