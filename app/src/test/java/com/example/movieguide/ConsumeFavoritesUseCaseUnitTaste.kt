package com.example.movieguide

import com.example.common.datafavorites.FavoriteEntity
import com.example.common.datafavorites.FavoritesRepository
import com.example.movieguide.features.movie.domain.ConsumeFavoriteUseCase
import com.example.movieguide.features.movie.domain.ConsumeMovieUseCase
import com.example.movieguide.features.movie.domain.Movie
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.InternalSerializationApi
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

//@RunWith(MockitoJUnitRunner::class)
class ConsumeFavoritesUseCaseUnitTaste {

    private lateinit var sut: ConsumeFavoriteUseCase

    @Mock
    private lateinit var favoritesRepository: FavoritesRepository

    @Mock
    private lateinit var consumeMovieUseCase: ConsumeMovieUseCase

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)

        sut = ConsumeFavoriteUseCase(
            favoritesRepository = favoritesRepository,
            consumeMovieUseCase = consumeMovieUseCase
        )
    }

    @OptIn(InternalSerializationApi::class)
    @Test
    fun `invoke sets isFavorite true WHEN movieTitle in favorites`() = runTest {
        //arrange(подготовка данных)
        val movies = listOf(
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
            Movie(title = "Title2",
                year = "2000",
                runtime = "120",
                poster = "Title2",
                genre = "action",
                director = "disney",
                actors = "V/A",
                plot = "plot",
                metascore = "100",
                imdbRating = "10",
                isFavorite = false,)
        )
        val favorites = listOf(
            FavoriteEntity(title = "Title1",
                year = "2000",
                poster = "Title1",
                genre = "action",
                metascore = "100",
                imdbRating = "10",
               )
        )

        whenever(favoritesRepository.consumeFavorites()).thenReturn(flowOf(favorites))  //мокаем зависимости
        whenever(consumeMovieUseCase(movieTitle = "")).thenReturn(flowOf(movies))  //мокаем зависимости

        //act (выполняем)
        val result = sut(movieTitle = "").first()

        //assert (проверяем)
        assertEquals(2, result.size)
        val p1 = result.first { it.title == "Title1" }
        val p2 = result.first { it.title == "Title2" }
        assertTrue(p1.isFavorite)   // title "Title1" в favorites
        assertFalse(p2.isFavorite)  // title "Title2" не в favorites
    }

    @OptIn(InternalSerializationApi::class)
    @Test
    fun `invoke sets all isFavorite false WHEN favorites empty`() = runTest {
        //arrange(подготовка данных)
        val movies = listOf(
            Movie(title = "Title1",
                year = "2000",
                runtime = "120",
                poster = "Title1",
                genre = "action",
                director = "disney",
                actors = "V/A",
                plot = "plot",
                metascore = "100",
                imdbRating = "10",
                isFavorite = false,),
            Movie(title = "Title2",
                year = "2000",
                runtime = "120",
                poster = "Title2",
                genre = "action",
                director = "disney",
                actors = "V/A",
                plot = "plot",
                metascore = "100",
                imdbRating = "10",
                isFavorite = false,)
        )
        val favorites = emptyList<FavoriteEntity>()

        whenever(favoritesRepository.consumeFavorites()).thenReturn(flowOf(favorites))  //мокаем зависимости
        whenever(consumeMovieUseCase(movieTitle = "")).thenReturn(flowOf(movies))  //мокаем зависимости

        //act (выполняем)
        val result = sut(movieTitle = "").first()

        //assert (проверяем)
        assertEquals(2, result.size)
        assertFalse(result[0].isFavorite)
        assertFalse(result[1].isFavorite)
    }
}