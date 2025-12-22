package com.example.common.data

import retrofit2.http.GET

interface MovieApiService {
    @GET("?t=Iron Man&apikey=dc2992ff")
    suspend fun getMovies(): MovieDto
}