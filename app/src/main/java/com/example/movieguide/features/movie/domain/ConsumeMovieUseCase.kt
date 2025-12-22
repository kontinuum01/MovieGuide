package com.example.movieguide.features.movie.domain

import com.example.common.data.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.InternalSerializationApi
import javax.inject.Inject

@OptIn(InternalSerializationApi::class)
class ConsumeMovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val movieDomainMapper: MovieDomainMapper,
) {
    operator fun invoke(movieTitle: String): Flow<List<Movie>> {

        return movieRepository.consumeMovies()
            .map { movie -> movie.map(movieDomainMapper::fromEntity) }
    }
}


//.map { movies ->
//    movies.map { movie -> movieDomainMapper.fromEntity(movie) }
//}