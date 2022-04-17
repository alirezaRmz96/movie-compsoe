/***
 * comments show the another way to solve
 */

package com.example.moviecleenapp.domain.repository

import com.example.moviecleenapp.data.model.Result
import com.example.moviecleenapp.data.units.Resource
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getMoviesList():Resource<List<Result>>
    //suspend fun getMoviesPopular(): DataOrException<List<Result>, Boolean, Exception>
    suspend fun addMovie(result: Result)
    suspend fun update(result: Result)
    suspend fun deleteFav(result: Result)
    fun getSavedMovies():Flow<List<Result>>
    fun getFavorite() : Flow<List<Result>>
}