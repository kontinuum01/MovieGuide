package com.example.movieguide.features.moviedetails.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.movieguide.features.moviedetails.domain.ConsumeMovieDetailsUseCase
import javax.inject.Inject
import javax.inject.Named

class MovieDetailsViewModelFactory@Inject constructor(
    private val consumeMovieDetailsUseCase: ConsumeMovieDetailsUseCase,
    private val movieDetailsStateFactory: MovieDetailsStateFactory,
    @Named("movieTitle")
    private val movieTitle: String,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(
        modelClass: Class<T>,
        extras: CreationExtras,
    ): T {
        when {
            modelClass.isAssignableFrom(MovieDetailsViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST") return MovieDetailsViewModel(
                    consumeMovieDetailsUseCase = consumeMovieDetailsUseCase,
                    movieDetailsStateFactory = movieDetailsStateFactory,
                    movieTitle = movieTitle,
                ) as T
            }
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}