package com.example.movieguide.features.movie.domain

data class Movie (
    val title: String,
    val year: String,
    val runtime: String,
    val genre: String,
    val director: String,
    val actors: String,
    val plot: String,
    val poster: String,
    val metascore: String,
    val imdbRating: String,
    val isFavorite: Boolean = false
)