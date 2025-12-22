package com.example.movieguide.features.moviedetails.presentation

import android.content.Context

typealias ErrorProvider = (Context) -> String

data class MovieDetailsScreenState(
    val isLoading: Boolean = false,
    val detailsState: MovieDetailsState = MovieDetailsState(),
    val hasError: Boolean = false,
    val errorProvider: ErrorProvider = { "" },
)

data class MovieDetailsState (
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
)
