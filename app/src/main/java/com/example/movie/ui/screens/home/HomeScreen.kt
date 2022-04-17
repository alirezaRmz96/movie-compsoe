package com.example.moviecleenapp.ui.screens.home

import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.graphics.Color
import androidx.core.net.toUri
import androidx.core.os.bundleOf
import androidx.navigation.*
import coil.compose.rememberImagePainter
import com.example.moviecleenapp.data.components.*
import com.example.moviecleenapp.data.units.getMovieCategory
import com.example.moviecleenapp.data.model.Result
import com.example.moviecleenapp.ui.navigation.MovieScreens
import com.example.moviecleenapp.ui.theme.Background
import com.example.moviecleenapp.ui.viewModel.MainViewModel


@ExperimentalFoundationApi
@Composable
fun HomeScreen(
    myNavController: NavController,
    viewModel: MainViewModel = hiltViewModel()
   ){

    Scaffold(
        backgroundColor = Background,
        topBar = {
            MovieTopBar(title = "Movie", navController = myNavController, size = 40.sp)
    },
   ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Background
        ) {
            HomeContent(myNavController,viewModel)
        }

    }
}

@ExperimentalFoundationApi
@Composable
private fun HomeContent(
    myNavController: NavController,
    viewModel: MainViewModel) {

//    var listOfMovies = emptyList<Result>()
//    if(viewModel.list.value.isEmpty()){
//        Log.d("TAG", "Network: is Empty")
//    }
//    else{
//       listOfMovies = viewModel.list.value
//    }
    Column {
        val textState = remember { mutableStateOf(TextFieldValue("")) }
        SearchView(textState)
        Spacer(modifier = Modifier.height(19.dp))

        TitleSection(label = "Movie Category")
        Spacer(modifier = Modifier.height(7.dp))

        HorizontalMovieCategory(getMovieCategory())
        Spacer(modifier = Modifier.height(7.dp))

        TitleSection(label = "Popular")
        Spacer(modifier = Modifier.height(7.dp))

        HorizontalPopularPoster(
            listOfMovies = viewModel.list.value,
            viewModel = viewModel,
            navController = myNavController
        )
    }
}

@ExperimentalFoundationApi
@Composable
fun HorizontalPopularPoster(
    listOfMovies : List<Result>,
    viewModel: MainViewModel,
    navController: NavController
) {

    if (listOfMovies.isNullOrEmpty()){
        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            cells = GridCells.Adaptive(minSize = 180.dp)
        ) {
            items(10) {
                ShimmerAnimation()
            }
        }
    }else{
        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            cells = GridCells.Adaptive(minSize = 180.dp)
        ){
            items(listOfMovies){ item ->
                if (viewModel.isLoading){
                    ShimmerAnimation()
                }
                else {
                    if (listOfMovies.isNullOrEmpty()){
                        ShimmerAnimation()
                    }else{
                        Box(contentAlignment = Alignment.TopEnd ){
                            Card(
                                modifier = Modifier
                                    .padding(6.dp)
                                    .height(242.dp)
                                    .width(202.dp)
                                    .clickable {
                                        gotoDetailsScreen(item, navController)
                                    },
                                shape = RoundedCornerShape(10.dp),
                            ) {
                                Image(
                                    painter = rememberImagePainter(
                                        data = "https://image.tmdb.org/t/p/w500" + item.poster_path,

                                        ),
                                    contentScale = ContentScale.Crop,
                                    contentDescription = "Image Poster",
                                )
                            }
                            FavoriteButton(
                                modifier = Modifier.padding(12.dp),
                                navController = navController,
                                item = item
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FavoriteButton(
    navController: NavController,
    modifier: Modifier ,
    color: Color = androidx.compose.ui.graphics.Color.White,
    item:Result,
    viewModel: MainViewModel = hiltViewModel()
) {
    var isExpanded by remember { mutableStateOf(false) }
    val showPopMenu = remember { mutableStateOf(false)}
    IconToggleButton(
        checked = isExpanded,
        onCheckedChange = {
            isExpanded = !isExpanded
        }
    ) {
        Box(
            modifier = Modifier
                .wrapContentSize(Alignment.TopCenter)
        ) {
            Icon(
                modifier = Modifier.clickable {
                    Log.d("TAG", "HorizontalPopularPoster: ")
                    showPopMenu.value = true
                },
                tint = color,
                imageVector = Icons.Filled.MoreVert,
                contentDescription = null
            )
            DropdownMenu(
                expanded = showPopMenu.value,
                onDismissRequest = { showPopMenu.value = false },
            ) {
                DropdownMenuItem(
                    onClick = {
                        showPopMenu.value = false
                        viewModel.addMovie(movie = item)
                }) {
                    Text("Favorite")
                }
                Divider()
                DropdownMenuItem(onClick = {
                    showPopMenu.value = false
                }) {
                    Text("Share")
                }
                Divider()
                DropdownMenuItem(onClick = {
                    showPopMenu.value = false
                    gotoDetailsScreen(item, navController)
                }) {
                    Text("More")
                }
            }
        }
    }

}

fun gotoDetailsScreen(result: Result,navController: NavController){
    navController
        .currentBackStackEntry
        ?.savedStateHandle?.set("movie",result)
    navController.navigate(MovieScreens.DetailsScreen.name)
}


