package com.example.moviecleenapp.ui.navigation

import java.lang.IllegalArgumentException

enum class MovieScreens {
    HomeScreen,
    DetailsScreen,
    FavoriteScreen;
    companion object{
        fun fromRoute(route:String?) : MovieScreens
        = when (route?.substringBefore("/")){
                HomeScreen.name -> HomeScreen
                DetailsScreen.name -> DetailsScreen
                FavoriteScreen.name -> FavoriteScreen
            null -> HomeScreen
            else -> throw  IllegalArgumentException("Route $route is not recognized")
        }
    }
}