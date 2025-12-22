package com.example.common.datafavorites

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable

@InternalSerializationApi
@Serializable
data class FavoriteEntity(
    val title: String,
    val year: String,
    val poster: String,
    val genre: String,
    val metascore: String,
    val imdbRating: String,

)