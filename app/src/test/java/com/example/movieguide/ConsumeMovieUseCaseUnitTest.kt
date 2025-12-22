package com.example.movieguide

import com.example.common.data.MovieEntity
import com.example.common.data.MovieRepository
import com.example.movieguide.features.movie.domain.ConsumeMovieUseCase
import com.example.movieguide.features.movie.domain.Movie
import com.example.movieguide.features.movie.domain.MovieDomainMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.InternalSerializationApi
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever

//@RunWith(MockitoJUnitRunner::class)
class ConsumeMovieUseCaseUnitTest {

    private lateinit var sut: ConsumeMovieUseCase

    @Mock
    private lateinit var movieRepository: MovieRepository

    @Mock
    private lateinit var movieDomainMapper: MovieDomainMapper

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)

        sut = ConsumeMovieUseCase(
            movieRepository = movieRepository,
            movieDomainMapper = movieDomainMapper
        )
    }

    @OptIn(InternalSerializationApi::class)
    @Test
    fun `invoke should return mapped list of movies from repository`() = runTest {
        //arrange(подготовка данных)
      val movieEntity1 = MovieEntity(
            title = "Title1",
            year = "2000",
            runtime = "120",
            poster = "Title1",
            genre = "action",
            director = "disney",
            actors = "V/A",
            plot = "plot",
            metascore = "100",
            imdbRating = "10",
            isFavorite = false,
        )
        val movieEntity2 = MovieEntity(
            title = "Title2",
            year = "2000",
            runtime = "120",
            poster = "Title2",
            genre = "action",
            director = "disney",
            actors = "V/A",
            plot = "plot",
            metascore = "100",
            imdbRating = "10",
            isFavorite = false,
        )

        val movieEntity = listOf(movieEntity1, movieEntity2)

        val movie1 = Movie(
            title = "Title1",
            year = "2000",
            runtime = "120",
            poster = "Title1",
            genre = "action",
            director = "disney",
            actors = "V/A",
            plot = "plot",
            metascore = "100",
            imdbRating = "10",
            isFavorite = false,
        )
        val movie2 = Movie(
            title = "Title2",
            year = "2000",
            runtime = "120",
            poster = "Title2",
            genre = "action",
            director = "disney",
            actors = "V/A",
            plot = "plot",
            metascore = "100",
            imdbRating = "10",
            isFavorite = false,
        )
        val movieList = listOf(movie1, movie2)

        whenever(movieRepository.consumeMovies()).thenReturn(flowOf(movieEntity))  //мокаем репозиторий

        whenever(movieDomainMapper.fromEntity(movieEntity1)).thenReturn(movie1) //мокаем маппер
        whenever(movieDomainMapper.fromEntity(movieEntity2)).thenReturn(movie2)

        //act (выполняем)
        val resultFlow: Flow<List<Movie>> = sut.invoke(movieTitle = "") //выполняем UseCase
        val actualResult = resultFlow.toList() //результат из Flow

        //assert (проверяем)
        assertEquals(1, actualResult.size)
        assertEquals(movieList, actualResult.first())

    }

    @OptIn(InternalSerializationApi::class)
    @Test
    fun `invoke should return empty list when repository returns empty list`() = runTest {
        //arrange
        whenever(movieRepository.consumeMovies()).thenReturn(flowOf(emptyList()))// Настраиваем мок для возврата пустого списка

        whenever(movieDomainMapper.fromEntity(any())).thenThrow(IllegalStateException("Mapper should not be called"))// Настраиваем маппер

        //act
        val resultFlow: Flow<List<Movie>> = sut.invoke(movieTitle = "")// Выполняем UseCase
        val actualResult = resultFlow.toList()

        //assert
        assertEquals(1, actualResult.size)
        assertEquals(emptyList<Movie>(), actualResult.first())
    }
}