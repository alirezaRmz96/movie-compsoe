package com.example.moviecleenapp.data.repository.datasourceImpl

import com.example.moviecleenapp.data.db.MovieDao
import com.example.moviecleenapp.data.model.Result
import com.example.moviecleenapp.data.repository.datasource.MovieLocalDataSource
import kotlinx.coroutines.flow.Flow

class MovieLocalDataSourceImpl(
    private val movieDao: MovieDao
) :MovieLocalDataSource{
    override suspend fun addMovie(result: Result) {
        movieDao.insert(result = result)
    }


    override fun getMovies(): Flow<List<Result>> {
        return movieDao.getMovies()
    }

    override suspend fun update(result: Result) {
        movieDao.update(result)
    }

    override fun getFavorite(): Flow<List<Result>> {
        return movieDao.getFavorite()
    }

    override suspend fun deleteFav(result: Result) {
        return movieDao.deleteFav(result)
    }

}