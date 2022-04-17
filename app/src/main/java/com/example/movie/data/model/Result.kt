package com.example.moviecleenapp.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
@Entity(tableName = "movie_popular")
data class Result(
    val adult: Boolean,
    val backdrop_path: String,
    @PrimaryKey
    val id: Int,
    val original_language: String,
    @ColumnInfo(name = "movie_title")
    val original_title: String,
    @ColumnInfo(name = "movie_description")
    val overview: String,
    val popularity: Double,
    @ColumnInfo(name = "poster_path")
    val poster_path: String,
    @ColumnInfo(name = "release_date")
    val release_date: String,
    val title: String,
    val video: Boolean,
    @ColumnInfo(name = "vote_average")
    val vote_average: Double,
    val vote_count: Int,
    @ColumnInfo(name = "favorite_movie") var favoriteMovies : Boolean = false
) : Parcelable