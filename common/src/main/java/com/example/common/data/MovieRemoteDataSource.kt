package com.example.common.data

import javax.inject.Inject

class MovieRemoteDataSource @Inject constructor (
    private val movieApiService: MovieApiService,
){
    suspend fun getMovies(): List<MovieDto> {

        val singleMovie = movieApiService.getMovies()

        return  listOf(singleMovie)
    }
}