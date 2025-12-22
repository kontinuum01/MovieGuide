package com.example.movieguide.features.movie.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.common.di.FragmentScope
import com.example.movieguide.features.movie.domain.AddFavoriteUseCase
import com.example.movieguide.features.movie.domain.ConsumeMovieUseCase
import com.example.movieguide.features.movie.domain.RemoveFavoriteUseCase
import com.example.movieguide.features.movie.domain.ConsumeFavoriteUseCase
import javax.inject.Inject

@FragmentScope
class MovieListViewModelFactory @Inject constructor(
    private val consumeMovieUseCase: ConsumeMovieUseCase,
    private val movieStateFactory: MovieStateFactory,
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val removeFavoriteUseCase: RemoveFavoriteUseCase,
    private val consumeFavoriteUseCase: ConsumeFavoriteUseCase,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(
        modelClass: Class<T>,
        extras: CreationExtras,
    ): T {
        when {
            modelClass.isAssignableFrom(MovieListViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                return MovieListViewModel(
                    consumeFavoriteUseCase = consumeFavoriteUseCase,
                    addFavoriteUseCase = addFavoriteUseCase,
                    removeFavoriteUseCase = removeFavoriteUseCase,
                    consumeMovieUseCase = consumeMovieUseCase,
                    movieStateFactory = movieStateFactory,
                    movieTitle = String()
                ) as T
            }
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}