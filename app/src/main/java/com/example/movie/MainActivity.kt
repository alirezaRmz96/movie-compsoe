package com.example.moviecleenapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.moviecleenapp.ui.navigation.BottomMovieNavigation
import com.example.moviecleenapp.ui.navigation.MovieCleanNavigation
import com.example.moviecleenapp.ui.navigation.MovieScreens
import com.example.moviecleenapp.ui.theme.MovieCleanAppTheme
import dagger.hilt.android.AndroidEntryPoint
@ExperimentalFoundationApi
@AndroidEntryPoint
@ExperimentalMaterialApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieCleanAppTheme() {
                MyApp()
            }
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun MyApp(){
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route?.substringBeforeLast("/")
    Scaffold(

        backgroundColor = com.example.moviecleenapp.ui.theme.HomeScreen,
        bottomBar = {
            if (currentRoute?.substringBeforeLast("/") != MovieScreens.DetailsScreen.name) {
                BottomMovieNavigation(navController)
            }
        }
    ){
        MovieCleanNavigation(navController = navController)
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview(){
    MovieCleanAppTheme {

    }
}