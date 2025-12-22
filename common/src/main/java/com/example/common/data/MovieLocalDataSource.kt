package com.example.common.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import javax.inject.Inject

@OptIn(InternalSerializationApi::class)
interface MovieLocalDataSource {
    fun consumeMovies(): Flow<List<MovieEntity>>
    suspend fun saveMovies(movies: List<MovieEntity>)
}

@OptIn(InternalSerializationApi::class)
class MovieLocalDataSourceImpl @Inject constructor (
    private val dataStore: DataStore<Preferences>,
    ) : MovieLocalDataSource
{
    override fun consumeMovies(): Flow<List<MovieEntity>> = dataStore.data
        .map(::mapMovieFromPrefs)

     override suspend fun saveMovies(movies: List<MovieEntity>) {
        dataStore.edit { prefs -> prefs[moviePreferencesKey] = encodeToString(movies) }
    }

    private fun decodeFromString(string: String): List<MovieEntity> =
        try {
            Json.decodeFromString(ListSerializer(MovieEntity::class.serializer()), string)
        } catch (e: Exception) {
            listOf()
        }
    private fun mapMovieFromPrefs(prefs: Preferences): List<MovieEntity> =
        prefs[moviePreferencesKey]
            ?.takeIf(String::isNotEmpty)
            ?.let(this::decodeFromString) ?: listOf()

    private val moviePreferencesKey = stringPreferencesKey(MOVIE_KEY)

    private fun encodeToString(movies: List<MovieEntity>): String =
        Json.encodeToString(
            ListSerializer(MovieEntity::class.serializer()),
            movies,
        )

    private companion object {
        const val MOVIE_KEY = "movie_key"
    }
}