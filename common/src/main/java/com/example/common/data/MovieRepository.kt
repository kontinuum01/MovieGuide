package com.example.common.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.serialization.InternalSerializationApi
import javax.inject.Inject

@OptIn(InternalSerializationApi::class)
class MovieRepository @Inject constructor(
    private val movieLocalDataSource: MovieLocalDataSource,
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val movieDataMapper: MovieDataMapper,
    dispatcher: CoroutineDispatcher,
) {
    private val scope = CoroutineScope(SupervisorJob() + dispatcher)

    fun consumeMovies(): Flow<List<MovieEntity>> {
        scope.launch(Dispatchers.IO) {
            val movies = movieRemoteDataSource.getMovies()
            movieLocalDataSource.saveMovies(
                movies.map(movieDataMapper::toEntity)
            )
        }
        return movieLocalDataSource.consumeMovies()
    }

}