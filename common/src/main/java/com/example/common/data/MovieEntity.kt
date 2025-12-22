package com.example.common.data

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable

@InternalSerializationApi
@Serializable
data class MovieEntity (
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
    val isFavorite: Boolean,
//    val response: Boolean

)