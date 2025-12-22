package com.example.common.data

import com.google.gson.annotations.SerializedName

data class MovieDto(
    @SerializedName("Title") val title: String,
    @SerializedName("Year")val year: String,
    @SerializedName("Runtime")val runtime: String,
    @SerializedName("Genre")val genre: String,
    @SerializedName("Director")val director: String,
    @SerializedName("Actors")val actors: String,
    @SerializedName("Plot")val plot: String,
    @SerializedName("Poster")val poster: String,
    @SerializedName("Metascore")val metascore: String,
    @SerializedName("imdbRating")val imdbRating: String,
    @SerializedName("isFavorite")val isFavorite: Boolean,
    @SerializedName("Response")val response: Boolean
)