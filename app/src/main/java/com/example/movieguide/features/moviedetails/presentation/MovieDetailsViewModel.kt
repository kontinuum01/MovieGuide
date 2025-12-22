package com.example.movieguide.features.moviedetails.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieguide.features.moviedetails.domain.ConsumeMovieDetailsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update

class MovieDetailsViewModel(
    private val consumeMovieDetailsUseCase: ConsumeMovieDetailsUseCase,
    private val movieDetailsStateFactory: MovieDetailsStateFactory,
    private val movieTitle: String,
) : ViewModel(){

    private val _state = MutableStateFlow(MovieDetailsScreenState())
    val state: StateFlow<MovieDetailsScreenState> = _state.asStateFlow()

    init {
        requestMovies()
    }

     fun requestMovies() {
        consumeMovieDetailsUseCase(movieTitle)
            .map { movieDetails -> movieDetailsStateFactory.create(movieDetails) }
            .flowOn(Dispatchers.IO)
            .onStart {
                _state.update { screenState -> screenState.copy(isLoading = true) }
            }
            .onEach { detailsState ->
                _state.update { screenState ->
                    screenState.copy(
                        isLoading = false,
                        detailsState = detailsState,
                    )
                }
            }
            .catch {
                _state.update { screenState ->
                    screenState.copy(
                        hasError = true,
                        errorProvider = {context -> context.getString(com.example.movieguide.R.string.error_wile_loading_data)}
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    fun errorHasShown() {
        _state.update { screenState -> screenState.copy(hasError = false) }
    }
}