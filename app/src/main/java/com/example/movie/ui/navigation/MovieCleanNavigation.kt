package com.example.moviecleenapp.ui.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.moviecleenapp.data.model.Result
import com.example.moviecleenapp.ui.screens.favorite.FavoriteScreen
import com.example.moviecleenapp.ui.screens.detials.DetailsScreen
import com.example.moviecleenapp.ui.screens.home.HomeScreen

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun MovieCleanNavigation(navController:NavHostController) {

    NavHost(
        navController = navController,
        startDestination = MovieScreens.HomeScreen.name
    ){
        composable(MovieScreens.HomeScreen.name){
            HomeScreen(
               navController,
            )
        }
        composable(MovieScreens.DetailsScreen.name){
            val movieModel = navController
                .previousBackStackEntry?.
                savedStateHandle?.get<Result>("movie")
            movieModel?.let {
                DetailsScreen(navController = navController, movie = it)
            }
        }
        composable(MovieScreens.FavoriteScreen.name){
            FavoriteScreen()
        }
    }
}