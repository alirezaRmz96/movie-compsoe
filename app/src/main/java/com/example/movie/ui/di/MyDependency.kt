package com.example.moviecleenapp.ui.di

import android.content.Context
import androidx.room.Dao
import com.example.moviecleenapp.data.db.MovieDao
import com.example.moviecleenapp.data.network.MovieAPI
import com.example.moviecleenapp.data.repository.datasource.MovieRemoteDataSource
import com.example.moviecleenapp.data.repository.datasourceImpl.MovieRemoteDataSourceImpl
import com.example.moviecleenapp.data.repository.MovieRepositoryImpl
import com.example.moviecleenapp.data.repository.datasource.MovieLocalDataSource
import com.example.moviecleenapp.data.repository.datasourceImpl.MovieLocalDataSourceImpl
import com.example.moviecleenapp.domain.repository.IsNetworking
import com.example.moviecleenapp.domain.repository.MovieRepository
import com.example.moviecleenapp.domain.repository.NetWorkCheck
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MyDependency {

    @Singleton
    @Provides
    fun provideIsNetWorking(
        @ApplicationContext context: Context
    ): IsNetworking = NetWorkCheck(context)

    @Singleton
    @Provides
    fun provideRepository(
        movieRemoteDataSource: MovieRemoteDataSource,
        movieLocalDataSource: MovieLocalDataSource
    ): MovieRepository {
        return MovieRepositoryImpl(
            movieRemoteDataSource,movieLocalDataSource
        )
    }
    @Singleton
    @Provides
    fun provideRemoteData(
        movieAPI: MovieAPI,
    ): MovieRemoteDataSource {
        return MovieRemoteDataSourceImpl(
            movieAPI
        )
    }

    @Singleton
    @Provides
    fun provideLocalDataSource(movieDao: MovieDao):MovieLocalDataSource{
        return MovieLocalDataSourceImpl(movieDao = movieDao)
    }
}