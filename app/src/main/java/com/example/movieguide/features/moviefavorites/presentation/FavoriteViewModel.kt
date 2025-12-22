package com.example.movieguide.features.moviefavorites.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieguide.R
import com.example.movieguide.features.moviefavorites.domain.ConsumeFavoriteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.serialization.InternalSerializationApi
import javax.inject.Inject

@OptIn(InternalSerializationApi::class)
class FavoriteViewModel @Inject constructor (
    private val consumeFavoriteUseCase: ConsumeFavoriteUseCase,
    private val favoriteStateFactory: FavoriteStateFactory,
    private val movieTitle: String,
) : ViewModel() {

    private val _state = MutableStateFlow(FavoriteScreenState())
    val state: StateFlow<FavoriteScreenState> = _state.asStateFlow()

    init {
        requestMovies()
    }

    private fun requestMovies() {
        consumeFavoriteUseCase(movieTitle).map { movies ->
            movies.map { movie -> favoriteStateFactory.create(movie) }
        }
            .onStart {
                _state.update { screenState -> screenState.copy(isLoading = true) }
            }
            .onEach { favoriteState ->
                _state.update { screenState ->
                    screenState.copy(
                        isLoading = false,
                        movieState = favoriteState,
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
}