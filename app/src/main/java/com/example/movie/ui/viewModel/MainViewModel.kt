/*
 * comments show the another way to solve
 */

package com.example.moviecleenapp.ui.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.moviecleenapp.data.model.Result
import com.example.moviecleenapp.data.units.Resource
import com.example.moviecleenapp.domain.repository.IsNetworking
import com.example.moviecleenapp.domain.repository.MovieRepository
import com.example.moviecleenapp.domain.useCase.GetMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val isNetworking: IsNetworking,
    private val getMovieUseCase: GetMovieUseCase
):ViewModel() {

    val list = mutableStateOf<List<Result> >(listOf())

    private val _movieList = MutableStateFlow<List<Result>>(emptyList())
    val movieList = _movieList.asStateFlow()

    private var _favoriteMovie = MutableStateFlow<List<Result>>(listOf())
    val favoriteMovie = _favoriteMovie.asStateFlow()


    var isLoading : Boolean by mutableStateOf(true)

//    val data:MutableState<DataOrException<List<Result>,
//            Boolean,Exception>> = mutableStateOf(
//        DataOrException(null,true,Exception("")))
    init {
        //loadMovies()
        getMovies()
        viewModelScope.launch()
        {
            movieRepository.getSavedMovies().distinctUntilChanged()
                .collect{ listOfMovies->
                    if (listOfMovies.isNullOrEmpty()){
                        Log.d("Empty", ": Empty List")
                    }
                    else{
                        listOfMovies.map {
                            if (it.favoriteMovies){
                                it.favoriteMovies = true
                            }
                            it
                        }
                        _movieList.value = listOfMovies
                    }
                }
        }
        viewModelScope.launch {
            getMovieUseCase.getFavorite().let { listof->
                _favoriteMovie.value = listof
            }

        }

    }

//    private fun loadMovies(){
//        viewModelScope.launch {
//            data.value.loading = true
//            data.value = getMovieUseCase.getMoviesPopular()
//            if (data.value.data.toString().isNotEmpty()){
//                data.value.loading = false
//            }
//        }
//    }
//    fun getTotalMovieCount() : Int{
//        return data.value.data?.toMutableList()?.size!!
//    }


    // get movie from API
    fun getMovies(){
        viewModelScope.launch(Dispatchers.Default) {
            try {
                if (isNetworking.getNetWork()){
                    try {
                        when(val response = getMovieUseCase.execute()){
                            is Resource.Success -> {
                                list.value = response.data!!
                                if (list.value.isNotEmpty()) isLoading = false
                            }
                            is Resource.Error ->{
                                isLoading = false
                                Log.e("Network", ": Failed getting movies" )
                            }
                            else -> {isLoading =false}
                        }
                    }catch (exception: Exception){
                        isLoading = false
                        Log.d("Network", "Movies: ${exception.message.toString()}")
                    }
                }
            }catch (e: Exception){
                Log.d("TAG", "No network: ")
            }

        }
    }


    fun addMovie(movie:Result) {
        viewModelScope.launch() {
            movieRepository.addMovie(result = movie )
        }
    }
    fun update(movie: Result){
        viewModelScope.launch {
            movieRepository.update(result = movie)
        }
    }
    fun deleteFav(movie:Result){
        viewModelScope.launch {
            movieRepository.deleteFav(result = movie)
        }
    }
}



