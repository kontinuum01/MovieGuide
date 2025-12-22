package com.example.movieguide.features.movie.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.datafavorites.FavoriteEntity
import com.example.movieguide.R
import com.example.movieguide.features.movie.domain.AddFavoriteUseCase
import com.example.movieguide.features.movie.domain.ConsumeMovieUseCase
import com.example.movieguide.features.movie.domain.RemoveFavoriteUseCase
import com.example.movieguide.features.movie.domain.ConsumeFavoriteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.InternalSerializationApi

@OptIn(InternalSerializationApi::class)
class MovieListViewModel (
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val removeFavoriteUseCase: RemoveFavoriteUseCase,
    private val consumeFavoriteUseCase: ConsumeFavoriteUseCase,
    private val consumeMovieUseCase: ConsumeMovieUseCase,
    private val movieStateFactory: MovieStateFactory,
    private val movieTitle: String,
) : ViewModel() {
    private val _state = MutableStateFlow(MovieScreenState())
    val state: StateFlow<MovieScreenState> = _state.asStateFlow()

    init {
        requestMovies()
    }

     fun requestMovies() {
        consumeFavoriteUseCase(movieTitle).map { movies ->
            movies.map { movie -> movieStateFactory.create(movie) }
        }
            .onStart {
                _state.update { screenState -> screenState.copy(isLoading = true) }
            }
            .onEach { movieListState ->
                _state.update { screenState ->
                    screenState.copy(
                        isLoading = false,
                        movieState = movieListState,
                    )
                }
            }
            .catch {
                _state.update { screenState ->
                    screenState.copy(
                        isLoading = false,
                        hasError = true,
                        errorProvider = { context -> context.getString(R.string.error_wile_loading_data) }
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    fun refresh() {
        requestMovies()
    }

    fun errorHasShown() {
        _state.update { screenState -> screenState.copy(hasError = false) }
    }

    fun addToFavorites(titleFavorite : String, year: String, poster : String,  genre: String, metascore: String, imdbRating: String) {
        viewModelScope.launch {
            try {
                Log.i("TAG","Attempting to add favorite: $titleFavorite")
                addFavoriteUseCase(
                    FavoriteEntity(
                        titleFavorite,
                        year,
                        poster,
                        genre,
                        metascore,
                        imdbRating
                    )
                )
                Log.i("TAG","add to favorite")
            } catch (e: Exception) {
                Log.e("TAG", "Error adding favorite for $titleFavorite", e)
                _state.update {
                    it.copy(hasError = true, errorRes = R.string.error_favorites_operation)
                }
            }
        }
    }

    fun removeFromFavorites(titleFavorite: String, year: String, poster : String, genre: String, metascore: String, imdbRating: String) {
        viewModelScope.launch {
            try {
                removeFavoriteUseCase(FavoriteEntity(titleFavorite, year, poster, genre, metascore, imdbRating))
                Log.i("TAG","remove to favorite")
            } catch (e: Exception) {
                _state.update {
                    it.copy(hasError = true, errorRes = R.string.error_favorites_operation)
                }
            }
        }
    }
}