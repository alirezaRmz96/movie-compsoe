package com.example.moviecleenapp.ui.screens.favorite

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.example.moviecleenapp.data.model.Result
import com.example.moviecleenapp.ui.navigation.BottomMovieNavigation
import com.example.moviecleenapp.ui.navigation.MovieScreens
import com.example.moviecleenapp.ui.theme.Background
import com.example.moviecleenapp.ui.viewModel.MainViewModel

@Composable
fun FavoriteScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    val movieList = viewModel.movieList.collectAsState().value
    val movieListFavor = viewModel.favoriteMovie.collectAsState().value
    Scaffold(
        topBar = {},
        backgroundColor = Background
    ) {
        //Log.d("TAG", "FavoriteScreen: " + movieList.size)
        Log.d("TAG", "Favorite Screen Favor: " + movieListFavor.size)
        Column(Modifier.padding(12.dp)) {
            LazyColumn{
                items(items = movieList){movie ->
                    MovieRow(movie = movie)
                }
            }
        }
    }
}


@Composable
fun MovieRow(movie: Result) {
    Card(modifier = Modifier
        .padding(4.dp)
        .fillMaxWidth(),
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        elevation = 6.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Surface(modifier = Modifier
                .padding(12.dp)
                .size(100.dp),
                shape = RectangleShape,
                elevation = 4.dp) {
                Image(
                    painter = rememberImagePainter(
                        data = "https://image.tmdb.org/t/p/w500" + movie.poster_path,
                        builder = {
                            crossfade(true)
                            transformations(CircleCropTransformation())
                        }),
                    contentDescription = "Movie Poster")
            }
            Column(modifier = Modifier.padding(4.dp)) {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.h6
                )
                Text(
                    text = "Director: ${movie.title}",
                    style = MaterialTheme.typography.caption
                )
                Text(
                    text = "Released: ${movie.release_date}",
                    style = MaterialTheme.typography.caption
                )
            }
        }
    }
}
