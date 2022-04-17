/***
 * comments show the another way to solve
 */

package com.example.moviecleenapp.data.repository
import com.example.moviecleenapp.data.model.Result
import com.example.moviecleenapp.data.repository.datasource.MovieLocalDataSource
import com.example.moviecleenapp.data.repository.datasource.MovieRemoteDataSource
import com.example.moviecleenapp.data.units.Resource
import com.example.moviecleenapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class MovieRepositoryImpl(
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val movieLocalDataSource: MovieLocalDataSource
): MovieRepository {


//    private val dataOrException =
//        DataOrException<List<Result>,
//                Boolean,
//                Exception>()

    override suspend fun getMoviesList(): Resource<List<Result>> {
        return try {
            Resource.Loading(data = true)

            val movieList = movieRemoteDataSource.getMovie()
            if (movieList.isNotEmpty()) Resource.Loading(data = false)
            Resource.Success(data = movieList)
        }catch (exception: Exception){
            Resource.Error(message = exception.message.toString())
        }
    }

//    override suspend fun getMoviesPopular(): DataOrException<List<Result>, Boolean, Exception> {
//        try {
//            dataOrException.loading = true
//            dataOrException.data = movieRemoteDataSource.getMovie()
//            if (dataOrException.data.toString().isNotEmpty()) dataOrException.loading = false
//        }catch (exception: Exception){
//            dataOrException.e = exception
//            Log.d("Exc", "getAllQuestions: ${dataOrException.e!!.localizedMessage}")
//        }
//        return dataOrException
//    }

    // Room data
    override suspend fun addMovie(result: Result) {
        movieLocalDataSource.addMovie(result = result)
    }

    override suspend fun update(result: Result) {
        movieLocalDataSource.update(result = result)
    }

    override suspend fun deleteFav(result: Result) {
        movieLocalDataSource.deleteFav(result)
    }


    override fun getSavedMovies(): Flow<List<Result>> {
        return movieLocalDataSource.getMovies()
    }

    override fun getFavorite(): Flow<List<Result>> {
        return movieLocalDataSource.getFavorite()
    }

}