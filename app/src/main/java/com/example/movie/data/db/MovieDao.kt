package com.example.moviecleenapp.data.db

import androidx.room.*
import com.example.moviecleenapp.data.model.Result
import kotlinx.coroutines.flow.Flow
import retrofit2.http.DELETE

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(result: Result)

    @Query("SELECT * FROM MOVIE_POPULAR")
    fun getMovies():Flow<List<Result>>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(result:Result)

    @Query("SELECT * FROM MOVIE_POPULAR WHERE favorite_movie = 1")
    fun getFavorite():Flow<List<Result>>

    @Delete
    suspend fun deleteFav(result: Result)
}