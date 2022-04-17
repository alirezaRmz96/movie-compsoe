package com.example.moviecleenapp.data.repository.datasourceImpl

import com.example.moviecleenapp.data.model.Result
import com.example.moviecleenapp.data.network.MovieAPI
import com.example.moviecleenapp.data.repository.datasource.MovieRemoteDataSource

class MovieRemoteDataSourceImpl(
    private val movieAPI: MovieAPI,
): MovieRemoteDataSource {
    override suspend fun getMovie(): List<Result> =
        movieAPI.getMoviesPopular().results

}