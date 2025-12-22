package com.example.movieguide

import com.example.movieguide.features.movie.domain.AddFavoriteUseCase
import com.example.movieguide.features.movie.domain.ConsumeFavoriteUseCase
import com.example.movieguide.features.movie.domain.ConsumeMovieUseCase
import com.example.movieguide.features.movie.domain.Movie
import com.example.movieguide.features.movie.domain.RemoveFavoriteUseCase
import com.example.movieguide.features.movie.presentation.MovieListViewModel
import com.example.movieguide.features.movie.presentation.MovieState
import com.example.movieguide.features.movie.presentation.MovieStateFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.argThat
import org.mockito.kotlin.whenever
import timber.log.Timber
import java.io.PrintWriter
import java.io.StringWriter


//@RunWith(MockitoJUnitRunner::class)
class MovieListViewModelTest {

    private lateinit var sut: MovieListViewModel

    @Mock
    private lateinit var addFavoriteUseCase: AddFavoriteUseCase

    @Mock
    private lateinit var removeFavoriteUseCase: RemoveFavoriteUseCase

    @Mock
    private lateinit var consumeFavoriteUseCase: ConsumeFavoriteUseCase

    @Mock
    private lateinit var movieStateFactory: MovieStateFactory

    @Mock
    private lateinit var consumeMovieUseCase: ConsumeMovieUseCase

    private lateinit var movieTitle: String

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)

        movieTitle = "Title1"

    }

    @Test
    fun `requestMovies WHEN starting loading EXPECT isLoading flag in state `() = runTest {
        // arrange
        whenever(consumeFavoriteUseCase.invoke(movieTitle)).thenReturn(flowOf())

        // act
        sut = MovieListViewModel(
            movieStateFactory = movieStateFactory,
            removeFavoriteUseCase = removeFavoriteUseCase,
            addFavoriteUseCase = addFavoriteUseCase,
            consumeFavoriteUseCase = consumeFavoriteUseCase,
            consumeMovieUseCase = consumeMovieUseCase,
            movieTitle = movieTitle
        )
        sut.requestMovies()
        advanceUntilIdle()

        // assert
        Assert.assertTrue(sut.state.value.isLoading)

    }


    @Test
    fun `requestMovies WHEN product loading has error EXPECT state has en error`() = runTest {
        // arrange
        whenever(consumeFavoriteUseCase.invoke(movieTitle)).thenReturn(flow { throw IllegalStateException() })

        // act
        sut = MovieListViewModel(
            movieStateFactory = movieStateFactory,
            removeFavoriteUseCase = removeFavoriteUseCase,
            addFavoriteUseCase = addFavoriteUseCase,
            consumeFavoriteUseCase = consumeFavoriteUseCase,
            consumeMovieUseCase = consumeMovieUseCase,
            movieTitle = movieTitle
        )
        sut.requestMovies()
        advanceUntilIdle()

        // assert
        Assert.assertTrue(!sut.state.value.hasError)
        assertEquals(R.string.error_wile_loading_data, sut.state.value.errorRes)
        Assert.assertFalse(sut.state.value.isLoading)
    }

    @Test
    fun `requestMovies WHEN product is loaded EXPECT reset isLoading flag and set product state`() = runTest {
        // arrange
        val expectedMovies = loadMovies()

        // act
        sut = MovieListViewModel(
            movieStateFactory = movieStateFactory,
            removeFavoriteUseCase = removeFavoriteUseCase,
            addFavoriteUseCase = addFavoriteUseCase,
            consumeFavoriteUseCase = consumeFavoriteUseCase,
            consumeMovieUseCase = consumeMovieUseCase,
            movieTitle = movieTitle
        )
        sut.requestMovies()
        advanceUntilIdle()

        // assert
        Assert.assertTrue(!sut.state.value.isLoading)
        assertEquals(expectedMovies, sut.state.value.movieState)
        Assert.assertFalse(sut.state.value.hasError)
    }

    private fun loadMovies(): List<MovieState> {
        whenever(consumeFavoriteUseCase.invoke(movieTitle))
            .thenReturn(
                flowOf(
                    listOf(
                        Movie(
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
                        ),
                        Movie(
                            title = "Title2",
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
                    )
                )
            )

        val state1 = MovieState(title = "Title1")
        val state2 = MovieState(title = "Title2")

        whenever(movieStateFactory.create(argThat { title == "Title1" })).thenReturn(state1)
        whenever(movieStateFactory.create(argThat { title == "Title2" })).thenReturn(state2)

        return listOf(state1, state2)
    }
}