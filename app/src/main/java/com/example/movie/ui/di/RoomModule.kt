package com.example.moviecleenapp.ui.di

import android.content.Context
import androidx.room.Room
import com.example.moviecleenapp.data.db.MovieDao
import com.example.moviecleenapp.data.db.MovieDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Singleton
    @Provides
    fun provideMovieDao(movieDataBase: MovieDataBase):MovieDao
    = movieDataBase.movieDao()

    @Singleton
    @Provides
    fun provideAppDataBase(@ApplicationContext context:Context):MovieDataBase
            = Room.databaseBuilder(
        context,
        MovieDataBase::class.java,
        "movies_db")
        .fallbackToDestructiveMigration()
        .build()
}