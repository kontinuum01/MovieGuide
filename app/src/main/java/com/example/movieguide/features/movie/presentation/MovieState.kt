package com.example.movieguide.features.movie.presentation

import android.content.Context

typealias ErrorProvider = (Context) -> String

data class MovieScreenState(
    val isLoading: Boolean = false,
    val movieState: List<MovieState> = emptyList(),
    val hasError: Boolean = false,
    val errorProvider: ErrorProvider = { "" },
    val errorRes: Int = 0,
    )

data class MovieState(
    val title: String = "",
    val year: String = "",
    val runtime: String = "",
    val genre: String = "",
    val director: String = "",
    val actors: String = "",
    val plot: String = "",
    val poster: String = "",
    val metascore: String = "",
    val imdbRating: String = "",
    val isFavorite: Boolean = false
)
