package com.example.movieguide.features.moviefavorites.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.common.di.FragmentScope
import com.example.movieguide.features.moviefavorites.domain.ConsumeFavoriteUseCase
import javax.inject.Inject

@FragmentScope
class FavoriteViewModelFactory @Inject constructor(
    private val consumeFavoriteUseCase: ConsumeFavoriteUseCase,
    private val favoriteStateFactory: FavoriteStateFactory,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(
        modelClass: Class<T>,
        extras: CreationExtras,
    ): T { when {
        modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
            @Suppress("UNCHECKED_CAST")
            return FavoriteViewModel(
                consumeFavoriteUseCase = consumeFavoriteUseCase,
                favoriteStateFactory = favoriteStateFactory,
                movieTitle = String()
            ) as T
        }
    }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}