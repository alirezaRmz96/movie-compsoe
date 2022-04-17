package com.example.moviecleenapp.data.repository.datasource

import com.example.moviecleenapp.data.model.MovieList
import com.example.moviecleenapp.data.model.Result
import retrofit2.Response

interface MovieRemoteDataSource {
    suspend fun getMovie(
    ):List<Result>
}