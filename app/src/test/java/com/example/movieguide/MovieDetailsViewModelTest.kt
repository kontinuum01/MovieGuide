package com.example.movieguide

import com.example.movieguide.features.moviedetails.domain.ConsumeMovieDetailsUseCase
import com.example.movieguide.features.moviedetails.domain.MovieDetails
import com.example.movieguide.features.moviedetails.presentation.MovieDetailsState
import com.example.movieguide.features.moviedetails.presentation.MovieDetailsStateFactory
import com.example.movieguide.features.moviedetails.presentation.MovieDetailsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class MovieDetailsViewModelTest {

    private lateinit var sut: MovieDetailsViewModel

    @Mock
    private lateinit var consumeMovieDetailsUseCase: ConsumeMovieDetailsUseCase

    @Mock
    private lateinit var movieDetailsStateFactory: MovieDetailsStateFactory

    private lateinit var movieTitle: String

    @Mock
    private lateinit var mockMovieDetails : MovieDetails

    @Mock
    private lateinit var mockDetailsState : MovieDetailsState

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)

        movieTitle = "Title1"

        //настройка мока фабрики состояния
        whenever(movieDetailsStateFactory.create(mockMovieDetails)).thenReturn(
            mockDetailsState
        )
    }

    @Test
    fun `requestMovies WHEN starting loading EXPECT isLoading flag in state `() = runTest {
        // arrange
        val initialFlow = MutableSharedFlow<MovieDetails>()
        whenever(consumeMovieDetailsUseCase(movieTitle)).thenReturn(initialFlow)

        // act
        sut = MovieDetailsViewModel(
            movieDetailsStateFactory = movieDetailsStateFactory,
            consumeMovieDetailsUseCase = consumeMovieDetailsUseCase,
            movieTitle = movieTitle
        )
        val initialState = sut.state.value
        assert(initialState.isLoading) { "Должен быть isLoading = true" }
        initialFlow.emit(mockMovieDetails)
        advanceUntilIdle()

        // assert
        Assert.assertTrue(!sut.state.value.isLoading)
        assert(sut.state.value.detailsState == mockDetailsState)
        Assert.assertFalse(sut.state.value.hasError)

    }

    @Test
    fun `requestMovies updates state correctly on successful data load`() = runTest {
        // arrange
        val expectedFlow = flowOf(mockMovieDetails)
        whenever(consumeMovieDetailsUseCase(movieTitle)).thenReturn(expectedFlow)

        // act
        sut = MovieDetailsViewModel(
            movieDetailsStateFactory = movieDetailsStateFactory,
            consumeMovieDetailsUseCase = consumeMovieDetailsUseCase,
            movieTitle = movieTitle
        )

        sut.requestMovies()
        advanceUntilIdle()

        // assert
        Assert.assertTrue(sut.state.value.isLoading)
        assert(sut.state.value.detailsState == mockDetailsState)
        Assert.assertFalse(sut.state.value.hasError)
    }

    @Test
    fun `requestMovies updates state to error when use case throws exception`() = runTest{
        //arrange
        val error = RuntimeException("Network Error")
        whenever(consumeMovieDetailsUseCase(movieTitle)).thenReturn(flow { throw error })

        //act
        sut = MovieDetailsViewModel(
            movieDetailsStateFactory = movieDetailsStateFactory,
            consumeMovieDetailsUseCase = consumeMovieDetailsUseCase,
            movieTitle = movieTitle
        )

        sut.requestMovies()
        advanceUntilIdle()

        //assert
        assert(sut.state.value.isLoading)
        assert(sut.state.value.hasError)
//        val state = sut.state.value
//        assert(state.isLoading)
//        assert(state.hasError)
    }

    @Test
    fun `errorHasShown sets hasError to false`() = runTest {
        //arrange
        whenever(consumeMovieDetailsUseCase(movieTitle)).thenReturn(flow { throw RuntimeException() }) //вызываем ошибку
        sut.requestMovies()
        advanceUntilIdle()
        assert(sut.state.value.hasError) //устанавливаем флаг true

        //act
        sut = MovieDetailsViewModel(
            movieDetailsStateFactory = movieDetailsStateFactory,
            consumeMovieDetailsUseCase = consumeMovieDetailsUseCase,
            movieTitle = movieTitle
        )

        sut.errorHasShown()

        //assert
        assert(!sut.state.value.hasError)
    }

}