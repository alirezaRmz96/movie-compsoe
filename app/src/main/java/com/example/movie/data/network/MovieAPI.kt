package com.example.moviecleenapp.data.network

import com.example.movie.BuildConfig
import com.example.moviecleenapp.data.model.MovieList
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface MovieAPI {

    @GET("movie/popular")
    suspend fun getMoviesPopular(
        @Query("api_key")
        api_key:String = BuildConfig.API_KEY,
        @Query("page")
        page:Int=1
    ):MovieList
}