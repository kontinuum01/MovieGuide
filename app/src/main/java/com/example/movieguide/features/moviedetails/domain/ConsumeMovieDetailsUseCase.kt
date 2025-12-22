package com.example.movieguide.features.moviedetails.domain

import com.example.common.data.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.InternalSerializationApi
import javax.inject.Inject

@OptIn(InternalSerializationApi::class)
class ConsumeMovieDetailsUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val movieDetailsDomainMapper: MovieDetailsDataMapper,
) {

    operator fun invoke(movieTitle: String): Flow<MovieDetails> {
        return movieRepository.consumeMovies()
            .map { movies ->
                movies
                    .first { it.title == movieTitle }
                    .run(movieDetailsDomainMapper::fromEntity)

            }
    }
}