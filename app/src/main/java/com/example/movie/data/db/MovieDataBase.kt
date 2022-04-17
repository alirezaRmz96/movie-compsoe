package com.example.moviecleenapp.data.db

import androidx.room.*
import com.example.moviecleenapp.data.model.Result
import com.example.moviecleenapp.data.units.DateConverter
import com.example.moviecleenapp.data.units.UUIDConverter

@Database(
    entities = [Result::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateConverter::class, UUIDConverter::class)

abstract class MovieDataBase : RoomDatabase (){
    abstract fun movieDao() : MovieDao


}