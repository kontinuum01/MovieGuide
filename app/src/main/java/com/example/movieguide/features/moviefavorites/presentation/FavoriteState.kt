package com.example.movieguide.features.moviefavorites.presentation

import android.content.Context

typealias ErrorProvider = (Context) -> String

data class FavoriteScreenState(
    val isLoading: Boolean = false,
    val movieState: List<FavoriteState> = emptyList(),
    val hasError: Boolean = false,
    val errorProvider: ErrorProvider = { "" },
    val errorRes: Int = 0,
)

data class FavoriteState(
    val title: String = "",
    val year: String = "",
    val genre: String = "",
    val poster: String = "",
    val metascore: String = "",
    val imdbRating: String = "",

)
