package com.example.moviecleenapp.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.moviecleenapp.ui.theme.Background
import com.example.moviecleenapp.ui.theme.buttonSelected

@Composable
fun BottomMovieNavigation(navController: NavController) {
    data class Screen(val title: String, val icon: ImageVector)
    val showColor = remember { mutableStateOf(false)}
    val items = listOf(
        Screen(title = MovieScreens.HomeScreen.name, icon = Icons.Filled.Home),
        Screen(title = MovieScreens.FavoriteScreen.name, icon = Icons.Filled.Favorite)

    )
    BottomNavigation(
        backgroundColor =  Background,
        contentColor = buttonSelected,
        modifier = Modifier
            .padding(start = 30.dp, end = 30.dp, bottom = 10.dp)
            .height(75.dp)
            .graphicsLayer {
                shape = RoundedCornerShape(20.dp)
                clip = true
            }
            .background(
                color = if (showColor.value) {
                    buttonSelected}else{
                    Color.Black
                },
            )

    ){
        RoundedCornerShape(10.dp)
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item->
            BottomNavigationItem(
                selected = currentRoute == item.title,
                onClick = {
                          navController.navigate(item.title){
                              navController.graph.startDestinationRoute?.let { screen_route ->
                                  popUpTo(screen_route){
                                      saveState = true
                                  }
                              }
                              launchSingleTop = true
                              restoreState = true
                          }
                    showColor.value = !showColor.value
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        "",
                    )
                },
//                selectedContentColor =if (showColor.value) {
//                    buttonSelected}else{
//                    Color.Black
//                },
                unselectedContentColor = Color.White
            )
        }
    }
}