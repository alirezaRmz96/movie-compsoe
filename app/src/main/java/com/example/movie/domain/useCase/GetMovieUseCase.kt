/***
 * comments show the another way to solve
 */

package com.example.moviecleenapp.domain.useCase

import com.example.moviecleenapp.data.model.Result
import com.example.moviecleenapp.data.units.Resource
import com.example.moviecleenapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMovieUseCase @Inject constructor(
   private val movieRepository: MovieRepository
){

//    suspend fun getMoviesPopular():
//            DataOrException<List<Result>, Boolean, java.lang.Exception> {
//        return movieRepository.getMoviesPopular()
//    }

    suspend fun execute():Resource<List<Result>> {
        var result = movieRepository.getMoviesList()
        if (result is Resource.Success){
            val getFavorite = movieRepository.getSavedMovies().first()
            return result.data?.map {
                if (getFavorite.contains(it)){
                    it.favoriteMovies = true
                }
                it
            }?.let { Resource.Success(it) }!!
        }
        return result
    }
    suspend fun getFavorite(): List<Result> {
        var result = movieRepository.getSavedMovies().first()
        return result.map {
            if (result.contains(it)) {
                it.favoriteMovies = true
            }
            it
        }

    }
}