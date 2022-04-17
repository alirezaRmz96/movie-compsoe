package com.example.moviecleenapp.data.repository.datasource

import com.example.moviecleenapp.data.model.Result
import kotlinx.coroutines.flow.Flow

interface MovieLocalDataSource {

    suspend fun addMovie(result: Result)
    fun getMovies(): Flow<List<Result>>
    suspend fun update(result: Result)
    fun getFavorite() : Flow<List<Result>>
    suspend fun deleteFav(result: Result)
}